import facebook4j.Facebook;
import facebook4j.FacebookException;
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
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
public class callback extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
            String oauthCode = request.getParameter("code");
            try {
                facebook.getOAuthAccessToken(oauthCode);
            } catch (FacebookException e) {
                throw new ServletException(e);
            
            }
            
            Connection con = new DBConnectivity().ConnectDB();
            System.out.println("Error Code: 200!");
            String Query = "INSERT INTO TokensData.FACEBOOK_ACCOUNT (ID, ACCOUNTNAME, TOKEN)"
                            + " VALUES (NULL, '"+facebook.getName()+"', '"+facebook.getOAuthAccessToken().getToken()+"')";
            Statement st  = con.createStatement();
            
            st.executeUpdate(Query);
            System.out.println("Token updated! : "+facebook.getOAuthAccessToken().getToken());
            
                
            
            response.sendRedirect(request.getContextPath() + "/dashboard");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet callback</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet callback at " + facebook.getName() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (FacebookException ex) {
            Logger.getLogger(callback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(callback.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(callback.class.getName()).log(Level.SEVERE, null, ex);
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
