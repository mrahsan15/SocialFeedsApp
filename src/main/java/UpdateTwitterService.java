
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import twitter4j.PagableResponseList;
import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class UpdateTwitterService extends Thread{
    Twitter twitter = null;
    Connection con = null;
    
    public UpdateTwitterService(){
        twitter  = new TwitterFactory().getInstance();
        con= new DBConnectivity().ConnectDB();
        UpdateFollowers(twitter, con);
//        UpdateFollowings(twitter,con);
         
    }
    
    
    
    
    public void UpdateFollowers(Twitter twitter, Connection con){
        try{
//            File file = new File("/home/"+System.getProperty("user.name")+"/.socialfeedapp/"+twitter.getScreenName()+"/FollowersProfile/");
//            File file = new File("/.socialfeedapp/"+twitter.getScreenName()+"/FollowersProfile/");
//            file.mkdirs();
//        
            
            
            ArrayList listFriends = new ArrayList();
            long cursor = -1;
            PagableResponseList<twitter4j.User> pagableFollowers;
            Statement checkstatement = con.createStatement();
            Statement updatestatement = con.createStatement();
            
            do {
                pagableFollowers = twitter.getFollowersList(twitter.getId(), cursor, 200);
                
                for (User user : pagableFollowers) {
                    listFriends.add(user);
//                    System.out.println(user.getScreenName());
                    int found = 0;
                    String UserId = user.getId()+"";
                    String Name = user.getName();
                    Name = Name.replace("'", "\'");
                    Name = Name.replace("\'", "\\'");
                    
                    String ScreenName = user.getScreenName();
                    int StatusCount = user.getStatusesCount();
                    int FollowersCount = user.getFollowersCount();
                    int FollowingCount = user.getFriendsCount();
                    
                    String ProfilePicture = user.getOriginalProfileImageURL();
                    String ProfileBanner = user.getProfileBannerURL();
//                    Relationship relation = twitter.showFriendship(twitter.getScreenName(), user.getScreenName());
//                    Boolean FollowBack = relation.isTargetFollowedBySource();
                    Boolean FollowBack = false;
                    String Description = user.getDescription();
                    Description = Description.replace("'", "\'");
                    Description = Description.replace("\'", "\\'");
                    
                    String Check = "SELECT * from Ahsan_Data.Twitter_Followers WHERE UserID = "+UserId;
                    ResultSet rs = checkstatement.executeQuery(Check) ;
                    if(rs.next()){
                        found++;
                        //break;
                        }
                    
                    if(found == 1){
                        
                    }else{
                        String LocalProfilePicture = "";
                        String LocalProfileBanner = "";
//                        try{
//                            if(!ProfilePicture.equals("null")){
//                                
//                            URL picurl = new URL(ProfilePicture);
//                            InputStream is = new BufferedInputStream(picurl.openStream());
//                            OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+UserId+"-Image"));
//                            LocalProfilePicture =file.getPath()+"/"+UserId+"-Image";
//                            for ( int z; (z = is.read()) != -1; ) {
//                                os.write(z);
//                            }
//                            is.close();
//                            os.close();
//                            }
//                            
//                        }catch(NullPointerException exx){
//                            System.out.println("Profile Picture Not Found!");
//                        }
//                        
//                        try{
//                            if(!ProfileBanner.equals("null")){
//                                ProfileBanner = ProfileBanner.replace("web", "1500x500");
//                                URL banurl = new URL(ProfileBanner);
//                                InputStream is = new BufferedInputStream(banurl.openStream());
//                                OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+UserId+"-Banner"));
//                                LocalProfileBanner = file.getPath()+"/"+UserId+"-Banner";
//                                for ( int z; (z = is.read()) != -1; ) {
//                                    os.write(z);
//                                }
//                                is.close();
//                                os.close();
//
//                            }
//                        }catch(NullPointerException exx){
//                                System.out.println("Cover Picture Not Found!");
//                        
//                        }
                        String DB="CREATE TABLE Ahsan_Data.Twitter_Followers("
                                + "ID int(9) NOT NULL AUTO_INCREMENT,"
                                + "Name varchar(500),"
                                + "ScreenName varchar(500) NOT NULL,"
                                + "UserID varchar(100) NOT NULL,"
                                + "StatusCount int(9),"
                                + "Followers int(9),"
                                + "Followings int(9),"
                                + "ProfilePicture varchar(500),"
                                + "ProfileBanner varchar(500),"
                                + "Description varchar (500),"
                                + "LocalProfilePicture varchar(500),"
                                + "LocalProfileBanner varchar(500),"
                                + "FollowBack int(1),"
                                + "UnfFollowed int(1)"
                                + "PRIMARY KEY (ID)"
                                + ")";
                        int followback = 0;
                        if(FollowBack){
                            followback = 1;
                        }
                        
                        String Query = "INSERT INTO Ahsan_Data.Twitter_Followers(ID, Name, ScreenName, UserID, "
                                + "StatusCount, Followers, Followings, "
                                + "ProfilePicture, ProfileBanner, Description, "
                                + "LocalProfilePicture, LocalProfileBanner,FollowBack,UnFollowed) "
                                + "VALUES (null,'"+Name+"','"+ScreenName+"','"+UserId+"',"
                                + "'"+StatusCount+"','"+FollowersCount+"','"+FollowingCount+"',"
                                + "'"+ProfilePicture+"','"+ProfileBanner+"','"+Description+"',"
                                + "'"+LocalProfilePicture+"','"+LocalProfileBanner+"',"+followback
                                + ",0)";
                        updatestatement.executeUpdate(Query);
                        
                    }
                    
                }
                
            } while ((cursor = pagableFollowers.getNextCursor()) != 0);
            
            String query = "SELECT * from Ahsan_Data.Twitter_Followers";
            ResultSet FollowersStatus = checkstatement.executeQuery(query) ;
            int unfollowerscount = 0;
            
            while(FollowersStatus.next()){
                String UserID = FollowersStatus.getString("UserID");
                int unfollowed = 1;
                
                for(int i = 0; i < listFriends.size(); i++ ){
                    User user = (User) listFriends.get(i);
                    if((user.getId()+"").equals(UserID)){
                        unfollowed = 0;
                    }
                }
                
                //Setting unfollow True if Value is not Found in ArrayList of Twitter Followers
                if(unfollowed == 1){
                    unfollowerscount++;
                    System.out.println("Unfollowed by: "+FollowersStatus.getString("ScreenName"));
                    String unfollowing = "UPDATE Ahsan_Data.Twitter_Followers SET UnFollowed = 1 WHERE Twitter_Followers.ID = "+FollowersStatus.getInt("ID");
                    updatestatement.executeUpdate(unfollowing);
                }
            }
            System.out.println("Unfol   lowed by: "+unfollowerscount+" Persons");
            
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    public void UpdateFollowings(Twitter twitter, Connection con){
        try{
//            Connection con = new DBConnectivity().ConnectDB();
//            Statement st = con.createStatement();
//            String DB="CREATE TABLE Ahsan_Data.Twitter_Followings("
//                                + "ID int(9) NOT NULL AUTO_INCREMENT,"
//                                + "Name varchar(500),"
//                                + "ScreenName varchar(500) NOT NULL,"
//                                + "UserID varchar(100) NOT NULL,"
//                                + "StatusCount int(9),"
//                                + "Followers int(9),"
//                                + "Followings int(9),"
//                                + "ProfilePicture varchar(500),"
//                                + "ProfileBanner varchar(500),"
//                                + "Description varchar (500),"
//                                + "LocalProfilePicture varchar(500),"
//                                + "LocalProfileBanner varchar(500),"
//                                + "PRIMARY KEY (ID)"
//                                + ")";
//            st.executeUpdate(DB);

        ArrayList listFriends = new ArrayList();
            long cursor = -1;
            PagableResponseList<twitter4j.User> pagableFollowings;
            Statement checkstatement = con.createStatement();
            Statement updatestatement = con.createStatement();
            
            do {
                pagableFollowings = twitter.getFriendsList(twitter.getId(), cursor, 200);
                
                for (User user : pagableFollowings) {
                    listFriends.add(user);
                    System.out.println(user.getScreenName());
                    int found = 0;
                    String UserId = user.getId()+"";
                    String Name = user.getName();
                    Name = Name.replace("'", "\'");
                    Name = Name.replace("\'", "\\'");
                    
                    String ScreenName = user.getScreenName();
                    int StatusCount = user.getStatusesCount();
                    int FollowersCount = user.getFollowersCount();
                    int FollowingCount = user.getFriendsCount();
                    
                    String ProfilePicture = user.getOriginalProfileImageURL();
                    String ProfileBanner = user.getProfileBannerURL();
                    
                    
                    String Description = user.getDescription();
                    Description = Description.replace("'", "\'");
                    Description = Description.replace("\'", "\\'");
                    
                    String Check = "SELECT * from Ahsan_Data.Twitter_Followings WHERE UserID = "+UserId;
                    ResultSet rs = checkstatement.executeQuery(Check) ;
                    if(rs.next()){
                        found++;
                        //break;
                        }
                    
                    if(found == 1){
                        System.out.println("Entry Found!");
                    }else{
                        String LocalProfilePicture = "";
                        String LocalProfileBanner = "";
//                        try{
//                            if(!ProfilePicture.equals("null")){
//                                
//                            URL picurl = new URL(ProfilePicture);
//                            InputStream is = new BufferedInputStream(picurl.openStream());
//                            OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+UserId+"-Image"));
//                            LocalProfilePicture =file.getPath()+"/"+UserId+"-Image";
//                            for ( int z; (z = is.read()) != -1; ) {
//                                os.write(z);
//                            }
//                            is.close();
//                            os.close();
//                            }
//                            
//                        }catch(NullPointerException exx){
//                            System.out.println("Profile Picture Not Found!");
//                        }
//                        
//                        try{
//                            if(!ProfileBanner.equals("null")){
//                                ProfileBanner = ProfileBanner.replace("web", "1500x500");
//                                URL banurl = new URL(ProfileBanner);
//                                InputStream is = new BufferedInputStream(banurl.openStream());
//                                OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+UserId+"-Banner"));
//                                LocalProfileBanner = file.getPath()+"/"+UserId+"-Banner";
//                                for ( int z; (z = is.read()) != -1; ) {
//                                    os.write(z);
//                                }
//                                is.close();
//                                os.close();
//
//                            }
//                        }catch(NullPointerException exx){
//                                System.out.println("Cover Picture Not Found!");
//                        
//                        }
                        String DB="CREATE TABLE Ahsan_Data.Twitter_Followings("
                                + "ID int(9) NOT NULL AUTO_INCREMENT,"
                                + "Name varchar(500),"
                                + "ScreenName varchar(500) NOT NULL,"
                                + "UserID varchar(100) NOT NULL,"
                                + "StatusCount int(9),"
                                + "Followers int(9),"
                                + "Followings int(9),"
                                + "ProfilePicture varchar(500),"
                                + "ProfileBanner varchar(500),"
                                + "Description varchar (500),"
                                + "LocalProfilePicture varchar(500),"
                                + "LocalProfileBanner varchar(500),"
                                + "FollowBack int(1),"
                                + "PRIMARY KEY (ID)"
                                + ")";
                        
                        String Query = "INSERT INTO Ahsan_Data.Twitter_Followings(ID, Name, ScreenName, UserID, "
                                + "StatusCount, Followers, Followings, "
                                + "ProfilePicture, ProfileBanner, Description, "
                                + "LocalProfilePicture, LocalProfileBanner) "
                                + "VALUES (null,'"+Name+"','"+ScreenName+"','"+UserId+"',"
                                + "'"+StatusCount+"','"+FollowersCount+"','"+FollowingCount+"',"
                                + "'"+ProfilePicture+"','"+ProfileBanner+"','"+Description+"',"
                                + "'"+LocalProfilePicture+"','"+LocalProfileBanner+"',1,"
                                + ")";
                        updatestatement.executeUpdate(Query);
                        
                    }
                    
                }
                
            } while ((cursor = pagableFollowings.getNextCursor()) != 0);
            
        
        
        
        
        
        
        
        }catch(Exception ex){
            
        }
        
        
        
    }

    
}
