
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.entity.common.Pagination;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;

public class UpdateService implements Runnable {
    public UpdateService(){
        
    }

    @Override
    public void run() {
        try {
            Connection con = new DBConnectivity().ConnectDB();
            Statement st= con.createStatement();
            String Query = "SELECT * from TokensData.INSTAGRAM_ACCOUNT";
            ResultSet rs = st.executeQuery(Query);
            //rs.next();
            while(rs.next()){
                Token token = new Token(rs.getString("TOKEN"),"");
                Instagram insta = new Instagram(token);
                UserInfoData user = insta.getCurrentUserInfo().getData();
                
                UpdateFollowers(insta);
                UpdateFollowings(insta);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstagramException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void UpdateFollowers(Instagram insta){
        
        String Prefix = "Ahsan";
        Connection con = new DBConnectivity().ConnectDB();
        try {
            
            File file = new File("/home/"+System.getProperty("user.name")+"/.socialfeedapp/"+insta.getCurrentUserInfo().getData().getUsername()+"/FollowersProfile/");
            file.mkdirs();
            int followers = insta.getCurrentUserInfo().getData().getCounts().getFollowedBy();
            
            List< UserFeedData> userfeeddata =insta.getUserFollowedByList(insta.getCurrentUserInfo().getData().getId()).getUserList();
            Pagination pagination = insta.getUserFollowedByList(insta.getCurrentUserInfo().getData().getId()).getPagination();
            ArrayList<UserFeedData> al = new ArrayList<UserFeedData>();
            
            for(int i = 0; i < userfeeddata.size();i++){
                al.add(userfeeddata.get(i));
            }
            
            while(pagination.hasNextPage()){
                userfeeddata =insta.getUserFollowedByListNextPage(pagination).getUserList();
                pagination = insta.getUserFollowedByListNextPage(pagination).getPagination();
                for(int i = 0; i < userfeeddata.size();i++){
                    al.add(userfeeddata.get(i));
                }
            }
            
            
            for(int i =0; i < al.size(); i++){
                String username = al.get(i).getUserName();
                String fullname = (al.get(i).getFullName()).replace("'", "\'");
                fullname = fullname.replace("'", "\'");
                fullname = fullname.replace("\'", "\\'");
                String userid = al.get(i).getId();
                String profilepic = al.get(i).getProfilePictureUrl();
                
                Statement st = con.createStatement();
                String CheckUser = "SELECT * from "+Prefix+"_Data."+Prefix+"_Followers WHERE USERNAME LIKE '"+username+"'";
                ResultSet CheckUsers =st.executeQuery(CheckUser);
                if (CheckUsers.next()){
                    System.out.println("Already in List!");
                    //Profile Picture Change Detect
                    String ProfilePicUrl = CheckUsers.getString("PICTUREURL");
                    if(ProfilePicUrl.equals(profilepic)){
                        System.out.println("Same Picture buh -_-");
                    }else{
                        String PicUpdateQuery = "UPDATE "+Prefix+"_Data."+Prefix+"_Followers SET PICTUREURL = '"+profilepic+"' WHERE Ahsan_Followers.ID = "+CheckUsers.getInt("ID");
                        st.executeUpdate(PicUpdateQuery);
                        
                        URL url = new URL(profilepic);
                        InputStream is = new BufferedInputStream(url.openStream());
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+username));
                        for ( int z; (z = is.read()) != -1; ) {
                            os.write(z);
                        }
                        is.close();
                        os.close();
                        System.out.print("File Copied to: "+file.getAbsolutePath());
                        System.out.println("Yeah Picture Updated!");
                    }
                }else{
                    String followbackquery = "SELECT * from "+Prefix+"_Data."+Prefix+"_Followings WHERE USERNAME LIKE '"+username+"'";
                    ResultSet rs = st.executeQuery(followbackquery);
                    rs.next();
                    int followingback = 0 ;
                    while(rs.next()){
                        if(rs.getString("FULLNAME").equals("")){
                            followingback = 1;
                        }
                    }
                    URL url = new URL(profilepic);
                    InputStream is = new BufferedInputStream(url.openStream());
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+username));
                    for ( int z; (z = is.read()) != -1; ) {
                        os.write(z);
                    }
                    is.close();
                    os.close();
                    System.out.print("File Copied to: "+file.getAbsolutePath());
                    String LocalPicturePath = file.getPath()+"/"+username;
                    String Query = "INSERT INTO "+Prefix+"_Data."+Prefix+"_Followers "
                            + "(ID, USERNAME,USERID, FULLNAME, PICTURE,PICTUREURL, FOLLOWING, FOLLOWER) "
                            + "VALUES (NULL, '"+username+"','"+userid+"', '"+fullname+"', '"+LocalPicturePath
                            +"','"+profilepic+""
                            +"', "+followingback+", 1);";
                    
                    System.out.println(fullname);
                    System.out.println(LocalPicturePath);
                    st.executeUpdate(Query);
                }
            }
        } catch (InstagramException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void UpdateFollowings(Instagram insta){
        String Prefix = "Ahsan";
        Connection con = new DBConnectivity().ConnectDB();
        try {
            
            File file = new File("/home/"+System.getProperty("user.name")+"/.socialfeedapp/"+insta.getCurrentUserInfo().getData().getUsername()+"/FollowingsProfile/");
            file.mkdirs();
            int following = insta.getCurrentUserInfo().getData().getCounts().getFollows();
            
            List< UserFeedData> userfeeddata =insta.getUserFollowList(insta.getCurrentUserInfo().getData().getId()).getUserList();
            Pagination pagination = insta.getUserFollowList(insta.getCurrentUserInfo().getData().getId()).getPagination();
            
            ArrayList<UserFeedData> al = new ArrayList<UserFeedData>();
            
            for(int i = 0; i < userfeeddata.size();i++){
                al.add(userfeeddata.get(i));
            }
            
            while(pagination.hasNextPage()){
                userfeeddata =insta.getUserFollowListNextPage(pagination).getUserList();
                pagination = insta.getUserFollowListNextPage(pagination).getPagination();
                for(int i = 0; i < userfeeddata.size();i++){
                    al.add(userfeeddata.get(i));
                }
            }
            
            
            for(int i =0; i < al.size(); i++){
                String username = al.get(i).getUserName();
                String fullname = (al.get(i).getFullName()).replace("'", "\'");
                fullname = fullname.replace("'", "\'");
                fullname = fullname.replace("\'", "\\'");
                String userid = al.get(i).getId();
                String profilepic = al.get(i).getProfilePictureUrl();
                
                Statement st = con.createStatement();
                String CheckUser = "SELECT * from "+Prefix+"_Data."+Prefix+"_Followings WHERE USERNAME LIKE '"+username+"'";
                ResultSet CheckUsers =st.executeQuery(CheckUser);
                if (CheckUsers.next()){
                    System.out.println("Already in List!");
                    //Profile Picture Change Detect
                    String ProfilePicUrl = CheckUsers.getString("PICTUREURL");
                    if(ProfilePicUrl.equals(profilepic)){
                        System.out.println("Same Picture buh -_-");
                    }else{
                        String PicUpdateQuery = "UPDATE "+Prefix+"_Data."+Prefix+"_Followings SET PICTUREURL = '"+profilepic+"' WHERE Ahsan_Followers.ID = "+CheckUsers.getInt("ID");
                        st.executeUpdate(PicUpdateQuery);
                        
                        URL url = new URL(profilepic);
                        InputStream is = new BufferedInputStream(url.openStream());
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+username));
                        for ( int z; (z = is.read()) != -1; ) {
                            os.write(z);
                        }
                        is.close();
                        os.close();
                        System.out.print("File Copied to: "+file.getAbsolutePath());
                        System.out.println("Yeah Picture Updated!");
                    }
                }else{
                    String followbackquery = "SELECT * from "+Prefix+"_Data."+Prefix+"_Followers WHERE USERNAME LIKE '"+username+"'";
                    ResultSet rs = st.executeQuery(followbackquery);
                    rs.next();
                    int followback = 0 ;
                    while(rs.next()){
                        if(rs.getString("FULLNAME").equals("")){
                            followback = 1;
                        }
                    }
                    URL url = new URL(profilepic);
                    InputStream is = new BufferedInputStream(url.openStream());
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file.getPath()+"/"+username));
                    for ( int z; (z = is.read()) != -1; ) {
                        os.write(z);
                    }
                    is.close();
                    os.close();
                    System.out.print("File Copied to: "+file.getAbsolutePath());
                    String LocalPicturePath = file.getPath()+"/"+username;
                    String Query = "INSERT INTO "+Prefix+"_Data."+Prefix+"_Followings "
                            + "(ID, USERNAME, USERID ,FULLNAME, PICTURE,PICTUREURL, FOLLOWING, FOLLOWER) "
                            + "VALUES (NULL, '"+username+"','"+userid+"','"+fullname+"', '"+LocalPicturePath
                            +"','"+profilepic+""
                            +"',1, "+followback+")";
                    
                    System.out.println(fullname);
                    System.out.println(LocalPicturePath);
                    st.executeUpdate(Query);
                }
            }
        } catch (InstagramException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}