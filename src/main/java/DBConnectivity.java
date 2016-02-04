
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectivity {
    public DBConnectivity(){
        
    }
    public Connection ConnectDB(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/";
            con = DriverManager.getConnection(url, "root", "root");
            
        }catch(Exception ex){
            System.out.println();
        }
        return con;
    }
}
