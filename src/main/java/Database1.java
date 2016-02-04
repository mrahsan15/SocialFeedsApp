
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database1 {
    
    public Database1(String Prefix){
        Connection con = new DBConnectivity().ConnectDB();
        //CreateCookiesTable(con);
        try {
            String Query = "SELECT * from "+Prefix+"_Data."+Prefix+"_Basic";
            Statement st = con.createStatement();
            st.executeQuery(Query);
        } catch (SQLException ex) {
            CreateCookiesTable(con);
            CreateDatabase(con, Prefix);
            CreateFollowersTable(con,Prefix);
            CreateFollowingsTable(con,Prefix);
            CreateBasicInfoTable(con,Prefix);
        }
    }
    public void CreateDatabase(Connection con,String Prefix){
        try {
            con = new DBConnectivity().ConnectDB();
            String Query = "CREATE DATABASE "+Prefix+"_Data";
            
            Statement st = con.createStatement();
            st.executeUpdate(Query);
        } catch (SQLException ex) {
            Logger.getLogger(Database1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void CreateFollowersTable(Connection con,String Prefix){
        try {
            con = new DBConnectivity().ConnectDB();
            
            String Query = "CREATE TABLE "+Prefix+"_Data."+Prefix+"_Followers("
                    + "ID int(5) NOT NULL AUTO_INCREMENT,"
                    + "USERNAME varchar(100) NOT NULL,"
                    + "USERID varchar(100) NOT NULL,"
                    + "FULLNAME varchar(100),"
                    + "PICTURE varchar(200),"
                    + "PICTUREURL varchar(200),"
                    + "FOLLOWING boolean,"
                    + "FOLLOWER boolean,"
                    + "PRIMARY KEY(ID)"
                    + ")";
            
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void CreateFollowingsTable(Connection con, String Prefix){
        try {
            con = new DBConnectivity().ConnectDB();
            
            String Query = "CREATE TABLE "+Prefix+"_Data."+Prefix+"_Followings("
                    + "ID int(5) NOT NULL AUTO_INCREMENT,"
                    + "USERNAME varchar(100) NOT NULL,"
                    + "USERID varchar(100) NOT NULL,"
                    + "FULLNAME varchar(100),"
                    + "PICTURE varchar(200),"
                    + "PICTUREURL varchar(200),"
                    + "FOLLOWING boolean,"
                    + "FOLLOWER boolean,"
                    + "PRIMARY KEY(ID)"
                    + ")";
            
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void CreateBasicInfoTable(Connection con,String Prefix){
        try {
            con = new DBConnectivity().ConnectDB();
            
            String Query = "CREATE TABLE "+Prefix+"_Data."+Prefix+"_Basic("
                    + "ID int(5) NOT NULL AUTO_INCREMENT,"
                    + "USERNAME varchar(50) NOT NULL,"
                    + "FULLNAME varchar(100),"
                    + "FOLLOWER int(5),"
                    + "FOLLOWING int(5),"
                    + "MEDIA int(5),"
                    + "PICTURE varchar(200),"
                    + "PICTUREURL varchar(200),"
                    + "PRIMARY KEY(ID)"
                    + ")";
            
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void CreateCookiesTable(Connection con){
        try {
            con = new DBConnectivity().ConnectDB();
            String CreateDB = "CREATE DATABASE TokensData";
            Statement st = con.createStatement();
            st.executeUpdate(CreateDB);
            String Query = "CREATE TABLE TokensData.INSTAGRAM_ACCOUNT("
                    + "ID int(2) NOT NULL AUTO_INCREMENT,"
                    + "ACCOUNTNAME varchar(100),"
                    + "TOKEN varchar(100) NOT NULL,"
                    + "PRIMARY KEY(ID)"
                    + ")";
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}