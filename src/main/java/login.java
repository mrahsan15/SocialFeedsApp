import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.auth.AuthorizationConfiguration;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Facebook facebook = new FacebookFactory().getInstance();
            request.getSession().setAttribute("facebook", facebook);
            StringBuffer callbackURL = request.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");
            response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<a href=\""+facebook.getOAuthAuthorizationURL(callbackURL.toString())+"\">"
                    + "Click here to login</a>");
            out.println("</body>");
            out.println("</html>");
        }    }
public void createproperty(){
    FileOutputStream fos = null;
        try {
            File file = new File("facebook4j.properties");
            fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write("debug=true\n" +
                    "oauth.appId=831179316980855\n" +
                    "oauth.accessToken=CAALz9APu7HcBAH7iIsbklFc7E25m6bDgwfUcEH1Rw4BUaEbaHVQsBGKnf6ZAdZAGgHKj3obi5uksssr4vgdX1rZB1SgvpjJYoOUkQSIV4CaO8AMXtB2mCIZAdcVYEots0hvw3CKB2ZAUwnvTShtSL2JqZAxJ32Uzo0S36BCMhQR1Ip5lYdwQCHR1tJMBCUZBSabyWira6WzZCQZDZD\n" +
                    "oauth.appSecret=f1490f5bfa6e55d0c6518a40841bac87\n" +
                    "oauth.permissions=public_profile,user_friends,email,user_about_me,user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,user_events,user_games_activity,user_hometown,user_likes,user_location,user_managed_groups,user_photos,user_posts,user_relationships,user_relationship_details,user_religion_politics,user_tagged_places,user_videos,user_website,user_work_history,read_custom_friendlists,read_insights,read_audience_network_insights,read_page_mailboxes,manage_pages,publish_pages,publish_actions,rsvp_event,pages_show_list,pages_manage_cta,ads_read,ads_management");
            System.out.println(file.getAbsolutePath());
            pw.flush();
            pw.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
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
