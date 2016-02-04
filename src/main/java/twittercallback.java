import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
import twitter4j.auth.RequestToken;

public class twittercallback extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
    
            
            Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
            RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
            String verifier = request.getParameter("oauth_verifier");
            try {
                AccessToken token = twitter.getOAuthAccessToken(requestToken, verifier);
                Connection con = new DBConnectivity().ConnectDB();
                Statement st = con.createStatement();
                
                String Query = "INSERT INTO TokensData.TWITTER_ACCOUNT (ID, ACCOUNTNAME, TOKEN)"
                            + " VALUES (NULL, '"+twitter.getScreenName()+"', '"+token+"')";
                
                st.executeUpdate(Query);
                System.out.println("Token updated! : "+token);
                
                request.getSession().removeAttribute("requestToken");
            } catch (TwitterException e) {
                throw new ServletException(e);
            } catch (SQLException ex) {
                Logger.getLogger(twittertoken.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect(request.getContextPath() + "/Test");
            
            
            
            
            
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet twittercallback</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet twittercallback at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
