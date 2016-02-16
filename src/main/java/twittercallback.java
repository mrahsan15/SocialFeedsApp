import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class twittercallback extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try{
        AccessToken token = null;
            Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
            
            RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
            String verifier = request.getParameter("oauth_verifier");
            
            try {
                token = twitter.getOAuthAccessToken(requestToken, verifier);
            }catch(Exception ex){
                System.out.println("Token couldn't get.");
            }
            String UserLogged = "";
            try{
                Cookie[] cookies = request.getCookies();
                for(int i = 0; i< cookies.length; i++){
                    if((cookies[i].getName()).equals("LoggedIn")){
                        UserLogged= cookies[i].getValue();
                    }
                }
            }catch(Exception ex){
                System.out.println(ex);
                System.out.println("Cookie Not Found here! Visit Some Bakery Please!");
            }
            String Account = twitter.getId()+"";
            String CheckAccount = "SELECT * from Ahsan_Data.TokensData WHERE ACCOUNTID = '"+Account+"'";
            String LoggedInDetail = "SELECT * from Ahsan_Data.LoginAccounts WHERE UserName = '"+UserLogged+"'";
            Connection con = new DBConnectivity().ConnectDB();
            Statement st  = con.createStatement();
            ResultSet rs = null;
            rs = st.executeQuery(LoggedInDetail);
            rs.next();
            int id = rs.getInt("ID");;
            rs = st.executeQuery(CheckAccount);
            try{
                if(rs.next()){
                    if(rs.getString("ACCOUNTID").equals(Account)){
                        int accountno = rs.getInt("ID");
                        String Update = "UPDATE Ahsan_Data.TokensData SET TOKEN = '"+token.getToken()+"' WHERE ID ="+accountno;
                        st.executeUpdate(Update);
                    }
                }else{
                    try{
                        rs = st.executeQuery(LoggedInDetail);
                        if(rs.next()){
                            id = rs.getInt("ID");
                        }
                        String Query = "INSERT INTO Ahsan_Data.TokensData(ID, ACCOUNTNAME, ACCOUNTID, TOKEN, ACCOUNT)"
                            + "VALUES (null ,'Twitter','"+Account+"','"+token.getToken()+"',"+id+")";

                        st.executeUpdate(Query);
                    }catch(Exception exx){
                        System.out.println(exx);
                    }
                }
            }catch(Exception ex){
                System.out.println(ex);
                try{
                rs = st.executeQuery(LoggedInDetail);
                if(rs.next()){
                    id = rs.getInt("ID");
                }
                String Query = "INSERT INTO Ahsan_Data.TokensData(ID, ACCOUNTNAME, ACCOUNTID, TOKEN, ACCOUNT)"
                    + "VALUES (null ,Twitter,'"+Account+"','"+token.getToken()+"',"+id+")";
                st.executeUpdate(Query);
                }
                catch(Exception exx){
                    System.out.println(exx);
                }
            }
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }catch(Exception ex){
            System.out.println("Whole Exception error!");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
