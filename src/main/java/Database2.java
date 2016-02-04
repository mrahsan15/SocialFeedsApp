
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database2 {
    public Database2(){
        
    }
    public void updateFollowers(String Database,String Query){
        try {
            Connection con = new DBConnectivity().ConnectDB();
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void updateFollowings(String Database,String Query){
        try {
            Connection con = new DBConnectivity().ConnectDB();
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void updateBasics(String Database,String Query){
        try {
            Connection con = new DBConnectivity().ConnectDB();
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            
        } catch (SQLException ex) {
            Logger.getLogger(Database2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
