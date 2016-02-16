import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
public class InstaConnect extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Verifier verifier = null;
        InstagramService service = new InstagramAuthService()
            .apiKey("e0bbe4b568cd453e925d7962ad2b9c7c")
            .apiSecret("f11f9582bef947b4b28bf871ce36a06c")
            .callback("http://localhost:8080/SocialFeedsApp/InstaConnect")
            .build();
        Instagram insta = null;
        String heading = "";
        Token token = null;
        try (PrintWriter out = response.getWriter()) {
            String code = request.getParameter("code");
            
            if(code == null){
                String authorizationUrl = service.getAuthorizationUrl(null);
                out.println("<a href=\""+authorizationUrl+"\">Click here to login!</a>");
                
            }else{
                HttpSession session = request.getSession();
            
            
            if(session.isNew()){
                heading= "Welcome New User";
                verifier = new Verifier(code);
                token = service.getAccessToken(null, verifier);
                session.setAttribute("token", token);
                insta = new Instagram(token);
            }else{
                heading = "Welcome Back!";
                token = (Token)session.getAttribute("token");
                insta = new Instagram(token);
            }
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet main</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+heading+"</h1>");
            out.println("<h1>"+token.getToken()+"</h1>");
            out.println("<h1>"+token.getSecret()+"</h1>");
            out.println("<h1>"+token.getRawResponse()+"</h1>");
            out.println("<h1>"+token.toString()+"</h1>");
            out.println("<h1>"+code+"</h1>");
            out.println("<h1>"+verifier.getValue()+"</h1>");
            
            out.println(insta.getCurrentUserInfo().getData().getFullName());
            out.println("<img src=\"");
            out.println(insta.getUserFeeds().getData().get(0).getImages().getStandardResolution().getImageUrl());
            out.println("\" >");
            out.println(insta.getUserFeeds().getData().get(0).getType());
            out.println(insta.getPopularMedia().getData().get(0).getImages().getStandardResolution().getImageUrl());
            
            //out.println("<br>"+info.getData().getFullName());
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
