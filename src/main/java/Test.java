    
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Test extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
//            Connection con = new DBConnectivity().ConnectDB();
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * from TWITTER_ACCOUNT");
//            Twitter twitter;
//            String token= "";
//            if(rs.next()){
//                token = rs.getString("TOKEN");
//                System.out.println("Token Submitted!");
//            }
//            
//            twitter = new TwitterFactory().getInstance(new AccessToken(token,null));
//            Twitter twitter = new TwitterFactory().getInstance();
//            Thread r = new UpdateTwitterService();
//            r.start();
//          
            
            
//            response.setHeader("username", "Mr_ahsan15");
//            response.addHeader("username", "Mr_ahsan15");
//            
            out.println("<html><head>");
            out.println("<script>");
            out.println("FB.logout(function(response) {\n" +
"  // user is now logged out\n" +
"});"
                    + "}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body onload=\"FB.logout()\">");
            out.println("<form name=\"logout\"class=\"_w0d\" action=\"https://www.facebook.com/logout.php\" method=\"post\" onsubmit=\"return window.Event &amp;&amp; Event.__inlineSubmit &amp;&amp; Event.__inlineSubmit(this,event)\" id=\"u_5_c\"><input type=\"hidden\" name=\"fb_dtsg\" value=\"AQFu2d6rKbYl:AQGoj9Or1ygL\" autocomplete=\"off\"><input type=\"hidden\" autocomplete=\"off\" name=\"ref\" value=\"mb\"><input type=\"hidden\" autocomplete=\"off\" name=\"h\" value=\"AfdXgAAaxjbWCkuP\"></form>");
            out.println("</body></html>");
            
            
        } catch (SecurityException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
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

