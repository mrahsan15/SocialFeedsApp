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

public class SignIn extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection con = new DBConnectivity().ConnectDB();
            Statement logincheck = con.createStatement();
            String loggedin = (String) request.getSession().getAttribute("loggedin");
            
            
            String Query= "";
//            String formhandler = request.getParameter("user");
            String username = request.getParameter("username");
//            if(formhandler.equals("login")){
                    Query = "SELECT * from Ahsan_Data.LoginAccounts WHERE UserName LIKE '"+request.getParameter("username")+"'";
                    ResultSet login = logincheck.executeQuery(Query);
                    try{
                        if(login.next()){
                            Cookie tokentoken = new Cookie("LoggedIn",username);
                            response.addCookie(tokentoken);
                            response.sendRedirect("dashboard");
                            
                        }
                    }catch(Exception ex){
                        response.sendRedirect("login.jsp");
                    }
//            }else if(formhandler.equals("signup")){
//                String UserName = request.getParameter("username");
//                String Password = request.getParameter("password");
//                String FullName = request.getParameter("fullname");
//                
//                Query= "INSERT INTO Ahsan_Data.LoginAccounts (ID, UserName, Password, FullName) "
//                        + "VALUES (NULL,'"+UserName+"','"+Password+"','"+FullName+"')";
//                
//            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SignIn</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignIn at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
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
