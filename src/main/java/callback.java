import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;
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
import org.jinstagram.auth.model.Token;
public class callback extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            AccessToken token = null;
            Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
            String oauthCode = request.getParameter("code");
            try {
                token = facebook.getOAuthAccessToken(oauthCode);
            } catch (FacebookException e) {
                throw new ServletException(e);
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
            String Account = facebook.getId();
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
                            + "VALUES (null ,'Facebook','"+facebook.getId()+"','"+token.getToken()+"',"+id+")";

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
                    + "VALUES (null ,Facebook,'"+facebook.getId()+"','"+token.getToken()+"',"+id+")";
                st.executeUpdate(Query);
                }
                catch(Exception exx){
                    System.out.println(exx);
                }
            }
            response.sendRedirect(request.getContextPath() + "/dashboard");
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
