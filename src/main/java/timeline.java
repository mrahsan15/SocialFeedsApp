import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.TaggableFriend;
import facebook4j.auth.AccessToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.common.Pagination;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;


public class timeline extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,NullPointerException {
        
        
        
        response.setContentType("text/html;charset=UTF-8");
                response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        InstagramService service = null ;
        String code = "";
        String CopyRightSyntax = "Copyright Apisylux Dashboard Panel 2016";
        
        Facebook facebook = null;
        Connection fab = new DBConnectivity().ConnectDB();
        String quer = "SELECT * from TokensData.FACEBOOK_ACCOUNT";
        try {
            Statement fabst = fab.createStatement();
            ResultSet fabrs = fabst.executeQuery(quer);
            if(fabrs.next()){
                String token = fabrs.getString("TOKEN");
                AccessToken tokenn = new AccessToken(token,null);
                facebook = new FacebookFactory().getInstance();
                facebook.setOAuthAccessToken(tokenn);
                System.out.println(facebook.getName());
            }else{
                response.sendRedirect(request.getContextPath()+"/login");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (FacebookException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
        
        try {
            service = new InstagramAuthService()
            .apiKey("e0bbe4b568cd453e925d7962ad2b9c7c")
            .apiSecret("f11f9582bef947b4b28bf871ce36a06c")
            .callback("http://192.168.1.100:8080/SocialFeedsApp/timeline")
            .build();
            System.out.println("Worked1!");
            code = request.getHeader("code");
            HttpSession session = request.getSession();
            
            Cookie[] cookies = request.getCookies();
            String token = "";
            for(int i = 0; i< cookies.length; i++){
                if((cookies[i].getName()).equals("token")){
                    token = cookies[i].getValue();
                }
            }
            
            
            if(token.equals("")){
                System.out.println("Token Not Found in Cookie.");
                if(request.getParameter("code").equals("null")){
                    response.sendRedirect(service.getAuthorizationUrl(null));
                }
                else{
                    Verifier verifier = new Verifier(request.getParameter("code"));
                    Token tokens = service.getAccessToken(null, verifier);
                    token = tokens.getToken();
                    System.out.println("Token system :"+token);
                    Cookie tokentoken = new Cookie("token",token);
                    response.addCookie(tokentoken);
                    String name = new Instagram(new Token(token,"")).getCurrentUserInfo().getData().getFullName();
                    System.out.println("FullName is :" + name);
                    Instagram insta =new Instagram(new Token(token,""));
                    PrintPage(insta, out,facebook);
                }

            }else{
                System.out.println("Token Found in Cookie.");
                String name = new Instagram(new Token(token,"")).getCurrentUserInfo().getData().getFullName();
                System.out.println("FullName is :" + name);
                Instagram insta = new Instagram(new Token(token,""));
                PrintPage(insta,out,facebook);
            }
            
            
//            if((request.getParameter("code")).equals("null")){
//            }else{
//                Instagram insta;
//                Token token;            
//                String heading;
//                if(session.isNew()){
//                    code = request.getParameter("code");
//                    System.out.println("Code IS: "+code);
//                    Verifier verifier = new Verifier(code);
//                    token = service.getAccessToken(null, verifier);
//                    session.setAttribute("token", token);
//                    insta = new Instagram(token);
//                    
//                    System.out.println("Worked3!");
//                }else{
//                    heading = "Welcome Back!";
//                    token = (Token)session.getAttribute("token");
//                    
//                    insta = new Instagram(token);
//                    System.out.println("Worked3!");
//                }
//            }
            
        }catch(Exception ex){
            System.out.println(ex); 
            response.sendRedirect(service.getAuthorizationUrl(null));
        }
    }
public void PrintPage(Instagram insta, PrintWriter out,Facebook facebook){
    UserInfo info = null;
    
    
    
    CommonWidgets widget = new CommonWidgets(insta, out);

        try {
            info = insta.getCurrentUserInfo();
    String ProfilePic = info.getData().getProfilePicture();
    String FullName  = info.getData().getFullName();
    String CurrentUserID = info.getData().getId();
    String Bio = info.getData().getBio();
    int follower = info.getData().getCounts().getFollowedBy();
    int following = info.getData().getCounts().getFollows();
    int mediacount = info.getData().getCounts().getMedia();
    
    
    int friend = 0;
        ResponseList<Friend> friendlist=facebook.friends().getBelongsFriend("988763721143756");
        
        
        friend = friend+ friendlist.size();
        friendlist.getSummary().getTotalCount();
        
        for(int i = 0; i < friendlist.size(); i++){
            System.out.println(friendlist.get(i).getName());
        }
        
    System.out.println("UserFeed Fetched: "+friend);
    
    
    widget.HeadRegion(out);
    
//    out.println("<!DOCTYPE html>\n" +
//"<html lang=\"en\">\n" +
//"	\n" +
//"<!-- Mirrored from jesus.gallery/everest/timeline.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Apr 2015 10:44:31 GMT -->");
//        
//        
//        out.println("<head>");
//        out.println("<meta charset=\"UTF-8\" />\n" +
//"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n" +
//"		<meta name=\"description\" content=\"Everest Admin Panel\" />\n" +
//"		<meta name=\"keywords\" content=\"Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap\" />\n" +
//"		<meta name=\"author\" content=\"Bootstrap Gallery\" />\n" +
//"		<link rel=\"shortcut icon\" href=\"img/favicon.ico\">");
//        
//        
//        
//        out.println("<title>Social Feed Apisylux</title>");
//        
//        
//        
//        out.println("		<!-- Bootstrap CSS -->\n" +
//"		<link href=\"css/bootstrap.css\" rel=\"stylesheet\" media=\"screen\">\n" +
//"\n" +
//"		<!-- Animate CSS -->\n" +
//"		<link href=\"css/animate.css\" rel=\"stylesheet\" media=\"screen\">\n" +
//"\n" +
//"		<!-- Alertify CSS -->\n" +
//"		<link href=\"css/alertify/alertify.core.css\" rel=\"stylesheet\">\n" +
//"		<link href=\"css/alertify/alertify.default.css\" rel=\"stylesheet\">\n" +
//"\n" +
//"		<!-- Main CSS -->\n" +
//"		<link href=\"css/main.css\" rel=\"stylesheet\" media=\"screen\">\n" +
//"\n" +
//"		<!-- Font Awesome -->\n" +
//"		<link href=\"fonts/font-awesome.min.css\" rel=\"stylesheet\">\n" +
//"\n" +
//"		<!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->\n" +
//"		<!--[if lt IE 9]>\n" +
//"			<script src=\"js/html5shiv.js\"></script>\n" +
//"			<script src=\"js/respond.min.js\"></script>\n" +
//"		<![endif]-->");
//        out.println("<style>");
//        out.println("#UserFeedFrom{\n" +
//"     margin: 0 auto;\n" +
//"     padding: 0;\n" +
//"     overflow: auto;\n" +
//"}\n" +
//"#UserPic{\n" +
//" width: 250px;\n" +
//" padding: 0;\n" +
//"     margin: 0;\n" +
//"     display: block;\n" +
//"     border: 1px solid white;\n" +
//"     position: fixed;\n" +
//"}\n" +
//"#Username{\n" +
//"		width: 750px;\n" +
//"		margin-left:48px;\n" +
//"     display: block;\n" +
//"     float: left;\n" +
//"     border: 1px solid white;\n" +
//"}");
//        out.println("#heart {\n" +
//"    position: relative;\n" +
//"    width: 100px;\n" +
//"    height: 90px;\n" +
//"}\n" +
//"#heart:before,\n" +
//"#heart:after {\n" +
//"    position: absolute;\n" +
//"    content: \"\";\n" +
//"    left: 50px;\n" +
//"    top: 0;\n" +
//"    width: 50px;\n" +
//"    height: 80px;\n" +
//"    background: red;\n" +
//"    -moz-border-radius: 50px 50px 0 0;\n" +
//"    border-radius: 50px 50px 0 0;\n" +
//"    -webkit-transform: rotate(-45deg);\n" +
//"       -moz-transform: rotate(-45deg);\n" +
//"        -ms-transform: rotate(-45deg);\n" +
//"         -o-transform: rotate(-45deg);\n" +
//"            transform: rotate(-45deg);\n" +
//"    -webkit-transform-origin: 0 100%;\n" +
//"       -moz-transform-origin: 0 100%;\n" +
//"        -ms-transform-origin: 0 100%;\n" +
//"         -o-transform-origin: 0 100%;\n" +
//"            transform-origin: 0 100%;\n" +
//"}\n" +
//"#heart:after {\n" +
//"\n" +
//"    left: 0;\n" +
//"    -webkit-transform: rotate(45deg);\n" +
//"       -moz-transform: rotate(45deg);\n" +
//"        -ms-transform: rotate(45deg);\n" +
//"         -o-transform: rotate(45deg);\n" +
//"            transform: rotate(45deg);\n" +
//"    -webkit-transform-origin: 100% 100%;\n" +
//"       -moz-transform-origin: 100% 100%;\n" +
//"        -ms-transform-origin: 100% 100%;\n" +
//"         -o-transform-origin: 100% 100%;\n" +
//"            transform-origin :100% 100%;\n" +
//"}\n" +
//"");
//        
//        out.println("</style></head>");
//        
        
        
        out.println("<body>");
        
        
        out.println("<!-- Header Start -->\n" +
"		<header>");
        
        
        
//        out.println("			<!-- Logo starts -->\n" +
//"			<div class=\"logo\">\n" +
//"				<a href=\"#\">\n" +
////"					<img src=\"img/logo.png\" alt=\"logo\">\n" +
//"					<span class=\"menu-toggle hidden-xs\">\n" +
//"						<i class=\"fa fa-bars\"></i>\n" +
//"					</span>\n" +
//"				</a>\n" +
//"			</div>\n" +
//"			<!-- Logo ends -->");
        widget.Logo(out);
        
        
//        out.println("			<!-- Custom Search Starts -->\n" +
//"			<div class=\"custom-search pull-left hidden-xs hidden-sm\">\n" +
//"				<input type=\"text\" class=\"search-query\" placeholder=\"Search here\">\n" +
//"				<i class=\"fa fa-search\"></i>\n" +
//"			</div>\n" +
//"			<!-- Custom Search Ends -->");
        widget.Search(out);
        widget.RightNavBar(out);
//        out.println("			<!-- Mini right nav starts -->\n" +
//"			<div class=\"pull-right\">\n" +
//"				<ul id=\"mini-nav\" class=\"clearfix\">\n" +
//"					<li class=\"list-box hidden-lg hidden-md hidden-sm\" id=\"mob-nav\">\n" +
//"						<a href=\"#\">\n" +
//"							<i class=\"fa fa-reorder\"></i>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li class=\"list-box dropdown hidden-xs\">\n" +
//"						<a id=\"drop7\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
//"							<i class=\"fa fa-image\"></i>\n" +
//"						</a>\n" +
//"						<span class=\"info-label info-bg animated rubberBand\">7+</span>\n" +
//"						<ul class=\"blog-gallery dropdown-menu fadeInDown animated clearfix recent-tweets\">\n" +
//"							<li>\n" +
//"								<img src=\"img/user1.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user2.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user3.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user4.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user5.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user6.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user7.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user8.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user9.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<img src=\"img/user3.jpg\" alt=\"User\">\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"					<li class=\"list-box dropdown hidden-xs\">\n" +
//"						<a id=\"drop5\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
//"							<i class=\"fa fa-th\"></i>\n" +
//"						</a>\n" +
//"						<span class=\"info-label success-bg animated rubberBand\">6</span>\n" +
//"						<ul class=\"dropdown-menu fadeInDown animated quick-actions\">\n" +
//"							<li class=\"plain\">Recently Viewed</li>\n" +
//"							<li>\n" +
//"								<a href=\"profile.html\">\n" +
//"									<i class=\"fa fa-user text-info\"></i>\n" +
//"									<p>Users</p>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
////"								<a href=\"login.html\">\n" +
//"									<i class=\"fa fa-tasks text-warning\"></i>\n" +
//"									<p>Tasks</p>\n" +
////"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href=\"typography.html\">\n" +
//"									<i class=\"fa fa-font text-danger\"></i>\n" +
//"									<p>Fonts</p>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href=\"graphs.html\">\n" +
//"									<i class=\"fa fa-globe text-success\"></i>\n" +
//"									<p>Graphs</p>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href=\"graphs.html\">\n" +
//"									<i class=\"fa fa-bank text-danger\"></i>\n" +
//"									<p>Home</p>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href=\"invoice.html\">\n" +
//"									<i class=\"fa fa-file-text text-success\"></i>\n" +
//"									<p>Invoice</p>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"					<li class=\"list-box dropdown hidden-xs\">\n" +
//"						<a id=\"drop1\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
//"							<i class=\"fa fa-bell\"></i>\n" +
//"						</a>\n" +
//"						<span class=\"info-label danger-bg animated rubberBand\">4</span>\n" +
//"						<ul class=\"dropdown-menu bounceIn animated messages\">\n" +
//"							<li class=\"plain\">\n" +
//"								Messages\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<div class=\"user-pic\">\n" +
//"									<img src=\"img/user4.jpg\" alt=\"User\">\n" +
//"								</div>\n" +
//"								<div class=\"details\">\n" +
//"									<strong class=\"text-danger\">Wilson</strong>\n" +
//"									<span>Uploaded 28 new files yesterday.</span>\n" +
//"								</div>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<div class=\"user-pic\">\n" +
//"									<img src=\"img/user1.jpg\" alt=\"User\">\n" +
//"								</div>\n" +
//"								<div class=\"details\">\n" +
//"									<strong class=\"text-danger\">Adams</strong>\n" +
//"									<span>Got 12 new messages.</span>\n" +
//"								</div>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<div class=\"user-pic\">\n" +
//"									<img src=\"img/user3.jpg\" alt=\"User\">\n" +
//"								</div>\n" +
//"								<div class=\"details\">\n" +
//"									<strong class=\"text-info\">Sam</strong>\n" +
//"									<span>Uploaded new project files today.</span>\n" +
//"								</div>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<div class=\"user-pic\">\n" +
//"									<img src=\"img/user5.jpg\" alt=\"User\">\n" +
//"								</div>\n" +
//"								<div class=\"details\">\n" +
//"									<strong class=\"text-info\">Jennifer</strong>\n" +
//"									<span>128 new purchases last 3 hours.</span>\n" +
//"								</div>\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"					<li class=\"list-box user-profile hidden-xs\">\n" +
//"						<a href=\"login.html\" class=\"user-avtar animated rubberBand\">\n" +
//"							<img src=\""+ProfilePic+"\" alt=\"user avatar\">\n" +
//"						</a>\n" +
//"					</li>\n" +
//"				</ul>\n" +
//"			</div>\n" +
//"			<!-- Mini right nav ends -->");
        
        out.println("		</header>\n" +
"		<!-- Header ends -->");
        out.println("		<!-- Left sidebar starts -->\n" +
"		<aside id=\"sidebar\">");
//        out.println("			<!-- Current User Starts -->\n" +
//"			<div class=\"current-user\">\n" +
//    "				<div class=\"user-avatar animated rubberBand\">\n" +
//    "					<img src=\""+ProfilePic+"\" alt=\"Current User\">\n" +
//    "					<span class=\"busy\"></span>\n" +
//    "				</div>\n" +
//"				<div class=\"user-name\">Welcome Mr. "+FullName+"</div>\n" +
//"				<ul class=\"user-links\">\n" +
//"					<li>\n" +
//"						<a href=\"profile.html\">\n" +
//"							<i class=\"fa fa-user text-success\"></i>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href=\"invoice.html\">\n" +
//"							<i class=\"fa fa-file-pdf-o text-warning\"></i>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href=\"components.html\">\n" +
//"							<i class=\"fa fa-sliders text-info\"></i>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href=\"login.html\">\n" +
//"							<i class=\"fa fa-sign-out text-danger\"></i>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"				</ul>\n" +
//"			</div>\n" +
//"			<!-- Current User Ends -->");
        widget.CurrentUser(out);
        widget.Menu(out, "Timeline");
//        out.println("			<!-- Menu start -->\n" +
//"			<div id='menu'>\n" +
//"				<ul>\n" +
//"					<li>\n" +
//"						<a href='dashboard'>\n" +
//"							<i class=\"fa fa-desktop\"></i>\n" +
//"							<span>Dashboard</span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li class=\"highlight\">\n" +
//"						<a href='timeline'>\n" +
//"							<i class=\"fa fa-sliders\"></i> \n" +
//"							<span>Timeline</span>\n" +
//"							<span class=\"current-page\"></span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href='blog.html'>\n" +
//"							<i class=\"fa fa-pencil\"></i> \n" +
//"							<span>Blog</span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href='graphs.html'>\n" +
//"							<i class=\"fa fa-flask\"></i> \n" +
//"							<span>Graphs</span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href='calendar.html'>\n" +
//"							<i class=\"fa fa-calendar\"></i> \n" +
//"							<span>Calendar</span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href='gallery.html'>\n" +
//"							<i class=\"fa fa-image\"></i> \n" +
//"							<span>Gallery</span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li>\n" +
//"						<a href='maps.html'>\n" +
//"							<i class=\"fa fa-globe\"></i> \n" +
//"							<span>Vector Maps</span>\n" +
//"						</a>\n" +
//"					</li>\n" +
//"					<li class='has-sub'>\n" +
//"						<a href='#'>\n" +
//"							<i class=\"fa fa-flask\"></i> \n" +
//"							<span>Bonus Pages</span>\n" +
//"						</a>\n" +
//"						<ul>\n" +
//"							<li>\n" +
//"								<a href='invoice.html'>\n" +
//"									<span>Invoice</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							\n" +
//"							<li>\n" +
//"								<a href='profile.html'>\n" +
//"									<span>Profile</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href=\"pricing.html\">\n" +
//"									<span>Pricing</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='login.html'>\n" +
//"									<span>Login</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='error.html'>\n" +
//"									<span>404</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='basic-template.html'>\n" +
//"									<span>Basic Template</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"					<li class='has-sub'>\n" +
//"						<a href='#'>\n" +
//"							<i class=\"fa fa-tasks\"></i>\n" +
//"							<span>UI Elements</span>\n" +
//"						</a>\n" +
//"						<ul>\n" +
//"							<li>\n" +
//"								<a href='buttons.html'>\n" +
//"									<span>Buttons</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='panels.html'>\n" +
//"									<span>Panels</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='icons.html'>\n" +
//"									<span>Icons</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='grid.html'>\n" +
//"									<span>Grid</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='components.html'>\n" +
//"									<span>Components</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='notifications.html'>\n" +
//"									<span>Notifications</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"					<li class='has-sub'>\n" +
//"						<a href='#'>\n" +
//"							<i class=\"fa fa-columns\"></i>\n" +
//"							<span>Forms</span>\n" +
//"						</a>\n" +
//"						<ul>\n" +
//"							<li>\n" +
//"								<a href='form-elements.html'>\n" +
//"									<span>Form Elements</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='form-layouts.html'>\n" +
//"									<span>Form Layouts</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='editor.html'>\n" +
//"									<span>Editor</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"					<li class='has-sub'>\n" +
//"						<a href='#'>\n" +
//"							<i class=\"fa fa-bars\"></i> \n" +
//"							<span>Tables</span>\n" +
//"						</a>\n" +
//"						<ul>\n" +
//"							<li>\n" +
//"								<a href='tables.html'>\n" +
//"									<span>Normal Tables</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"							<li>\n" +
//"								<a href='datatables.html'>\n" +
//"									<span>Data Tables</span>\n" +
//"								</a>\n" +
//"							</li>\n" +
//"						</ul>\n" +
//"					</li>\n" +
//"				</ul>\n" +
//"			</div>\n" +
//"			<!-- Menu End -->");
        
        out.println("			<!-- Freebies Starts -->\n" +
"			<div class=\"freebies\">");
        
//        out.println("				<!-- Sidebar Extras -->      \n" +
//"				<div class=\"sidebar-addons\">\n" +
//"					<ul class=\"views\">\n" +
//"						<li>\n" +
//"							<i class=\"fa fa-circle-o text-success\"></i>\n" +
//"							<div class=\"details\">\n" +
//"								<p>Signups</p>\n" +
//"							</div>\n" +
//"							<span class=\"label label-success\">8</span>\n" +
//"						</li>\n" +
//"						<li>\n" +
//"							<i class=\"fa fa-circle-o text-info\"></i>\n" +
//"							<div class=\"details\">\n" +
//"								<p>Users Online</p>\n" +
//"							</div>\n" +
//"							<span class=\"label label-info\">7</span>\n" +
//"						</li> \n" +
//"						<li>\n" +
//"							<i class=\"fa fa-circle-o text-danger\"></i>\n" +
//"							<div class=\"details\">\n" +
//"								<p>Images Uploaded</p>\n" +
//"							</div>\n" +
//"							<span class=\"label label-danger\">4</span>\n" +
//"						</li>         \n" +
//"					</ul>\n" +
//"				</div>");
        
        out.println("			</div>\n" +
"			<!-- Freebies Starts -->");
        out.println("</aside>\n" +
"		<!-- Left sidebar ends -->");
        out.println("		<!-- Dashboard Wrapper starts -->\n" +
"		<div class=\"dashboard-wrapper\">");
        
        
        out.println("			<!-- Top Bar starts -->\n" +
"			<div class=\"top-bar\">\n" +
"				<div class=\"page-title\">\n" +
"					Timeline\n" +
"				</div>\n" +
"				<ul class=\"stats hidden-xs\">\n" +
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"downloads_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
"							<h4><span id=\"\">"+follower+"</span> <i class=\"fa fa-chevron-up up\"></i></h4>\n" +
"							<h5>Followers</h5>\n" +
"						</div>\n" +
"					</li>\n" +
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"users_online_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
"							<h4><span id=\"\">"+following+"</span> <i class=\"fa fa-chevron-down down\"></i></h4>\n" +
"							<h5>Following</h5>\n" +
"						</div>\n" +
"					</li>\n" +
"				</ul>\n" +
"			</div>\n" +
"			<!-- Top Bar ends -->");
        out.println("<!-- Main Container starts -->\n" +
"			<div class=\"main-container\">");
        out.println("				<!-- Container fluid Starts -->\n" +
"				<div class=\"container-fluid\">");
        out.println("					<!-- Timeline starts -->\n" );
        out.println(
"					<div class=\"timeline animated\">\n"); 
        
        
        
//	out.println("					<div class=\"timeline-row\">\n" +
//"							<div class=\"timeline-time\">\n" +
//"								7:51 PM<small>Aug 28</small>\n" +
//"							</div>\n" +
//"							<div class=\"timeline-icon\">\n" +
//"								<div class=\"danger-bg\">\n" +
//"									<i class=\"fa fa-pencil\"></i>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"							<div class=\"panel timeline-content\">\n" +
//"								<div class=\"panel-body\">\n" +
//"									<h2 class=\"text-info\">Heading</h2>\n" +
//"									<p>\n" +
//"										Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
//"									</p>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"						</div>\n");
//        out.println(""+
//"						<div class=\"timeline-row\">\n" +
//"							<div class=\"timeline-time\">\n" +
//"								6:32 PM<small>Aug 21</small>\n" +
//"							</div>\n" +
//"							<div class=\"timeline-icon\">\n" +
//"								<div class=\"warning-bg\">\n" +
//"									<i class=\"fa fa-quote-right\"></i>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"							<div class=\"panel timeline-content\">\n" +
//"								<div class=\"panel-body\">\n" +
//"									<p>\n" +
//"										Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.\n" +
//"									</p>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"						</div>\n" +
//"						<div class=\"timeline-row\">\n" +
//"							<div class=\"timeline-time\">\n" +
//"								6:00 AM<small>Aug 15</small>\n" +
//"							</div>\n" +
//"							<div class=\"timeline-icon\">\n" +
//"								<div class=\"success-bg\">\n" +
//"									<i class=\"fa fa-image\"></i>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"							<div class=\"panel timeline-content\">\n" +
//"								<div class=\"panel-body\">\n" +
//"									<h2 class=\"text-info\">Timeline Title</h2>\n" +
//"									<div class=\"row\">\n" +
//"										<div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\">\n" +
//"											<img class=\"img-responsive\" src=\"img/user3.jpg\" alt=\"User\" />\n" +
//"										</div>\n" +
//"										<div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\">\n" +
//"											<img class=\"img-responsive\" src=\"img/user7.jpg\" alt=\"User\" />\n" +
//"										</div>\n" +
//"										<div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\">\n" +
//"											<img class=\"img-responsive\" src=\"img/user8.jpg\" alt=\"User\" />\n" +
//"										</div>\n" +
//"										<div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\">\n" +
//"											<img class=\"img-responsive\" src=\"img/user9.jpg\" alt=\"User\" />\n" +
//"										</div>\n" +
//"									</div>\n" +
//"									<p>\n" +
//"										It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" +
//"									</p>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"						</div>\n" +
//"						<div class=\"timeline-row\">\n" +
//"							<div class=\"timeline-time\">\n" +
//"								5:23 PM<small>Aug 12</small>\n" +
//"							</div>\n" +
//"							<div class=\"timeline-icon\">\n" +
//"								<div class=\"twitter-bg\">\n" +
//"									<i class=\"fa fa-twitter\"></i>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"							<div class=\"panel timeline-content\">\n" +
//"								<div class=\"panel-body\">\n" +
//"									<h2 class=\"text-info\">Twitter post</h2>\n" +
//"									<p>\n" +
//"										<i class=\"fa  fa-lg fa-quote-left\"></i> There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form <i class=\"fa fa-lg fa-quote-right\"></i>\n" +
//"									</p>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"						</div>\n" +
//"						<div class=\"timeline-row\">\n" +
//"							<div class=\"timeline-time\">\n" +
//"								6:14 PM<small>Aug 9</small>\n" +
//"							</div>\n" +
//"							<div class=\"timeline-icon\">\n" +
//"								<div class=\"fb-bg\">\n" +
//"									<i class=\"fa fa-facebook\"></i>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"							<div class=\"panel timeline-content\">\n" +
//"								<div class=\"panel-body\">\n" +
//"									<h2 class=\"text-info\">Timeline Post</h2>\n" +
//"									<p>\n" +
//"										Lorem ipsum velit ullamco anim pariatur proident eu deserunt laborum. Lorem ipsum ad in nostrud adipisicing cupidatat anim officia ad id cupidatat veniam quis elit ullamco.\n" +
//"									</p>\n" +
//"								</div>\n" +
//"							</div>\n" +
//"						</div>\n");
//        Pagination userfeedpage = new Pagination();
//        UserFeed userfeed = insta.getUserFeedInfoNextPage(userfeedpage);
//        
        MediaFeed mediafeed = insta.getUserFeeds();
        int size = mediafeed.getData().size();
        String Caption = "";
        String MediaUrl="";
        String VideoTags ="";
        String UserName = "";
        String UserFullName = "";
        String UserProfilePic = "";
        int likes = 0;
        int comments = 0;
        for(int i = 0; i < size; i++){
            MediaFeedData feeddata = mediafeed.getData().get(i);
            String MediaType = feeddata.getType();
            UserFullName = feeddata.getUser().getFullName();
            UserProfilePic = feeddata.getUser().getProfilePictureUrl();
            UserName = feeddata.getUser().getUserName();
            likes = feeddata.getLikes().getCount();
            comments = feeddata.getComments().getCount();
            
            try{
            if(feeddata.getCaption().getText().equals("null")){
                
            }else{
                Caption = feeddata.getCaption().getText();
            }
                
            }catch(Exception ex){
                System.out.println("Expection Caught in Between Code!");
                Caption = "";
            }
            if(MediaType.equals("image")){
                MediaUrl = feeddata.getImages().getLowResolution().getImageUrl();
                VideoTags = "<img class=\"img-responsive\" src=\""+MediaUrl+"\" alt=\"User\" />\n";
            }else if(MediaType.equals("video")){
                MediaUrl = feeddata.getVideos().getLowResolution().getUrl();
                VideoTags = "<video width=\"320\" height=\"320\" controls preload=\"none\" autoplay=\"false\" >"
                        + "<source src=\""+MediaUrl+"\" type=\"video/mp4\"/></video>";
            }
            
            
//            System.out.println(MediaUrl);
        out.println(""+
"						<div class=\"timeline-row\">\n" +
"							<div class=\"timeline-time\">\n" +
"								12:00 AM<small>Aug 6</small>\n" +
"							</div>\n" +
"							<div class=\"timeline-icon\">\n" +
"								<div class=\"success-bg\">\n" +
"									<i class=\"fa fa-camera-retro fa-2x\"></i>\n" +
"								</div>\n" +
"							</div>\n" +
"							<div class=\"panel timeline-content\">\n" +
"								<div class=\"panel-body\">\n" +
""+
"<div style=\"float:left; padding:0;\">"+
"<img src=\""+UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">\n " +
"<div style=\"padding:0; margin-left:10px; float:right;\">"
                + "<font size=\"+1\"><Strong>"+UserFullName+"</strong></font>"
                + "<br>("+UserName+")\n<br>"
                + "</div></div><br>" +

"" +
VideoTags+
//"									<img class=\"img-responsive\" src=\""+MediaUrl+"\" alt=\"User\" />\n" +
"									<p>\n" +Caption+
//"										Sed pretium, ligula sollicitudin laoreet viverra, tortor libero sodales leo, eget blandit nunc tortor eu nibh. Nullam mollis. Ut justo. Suspendisse potenti.\n" +
"									</p>\n" 
//                + "<div style=\"margin-bottom:10px;\">"
                + "<button data-toggle=\"button\" class=\"btn btn-danger\"><i class=\"fa fa-thumbs-up \"></i>"+likes+"</button>"
                + "<button type=\"button\" class=\"btn btn-info\">"+comments+"</button>"
                
//                + "</div>"
                +
"								</div>\n" +

"							</div>\n" +
"						</div>\n");
        
        }
        
        out.println(" " +
"					</div>\n" +
"					<!-- Timeline ends -->");
        out.println("				</div>\n" +
"				<!-- Container fluid ends -->");
        out.println("			</div>\n" +
"			<!-- Main Container ends -->");
        out.println("			<!-- Right sidebar starts -->\n" +
"			<div class=\"right-sidebar\">");
        out.println("				<!-- Addons starts -->\n" +
"				<div class=\"add-on clearfix\">\n" +
"					<div class=\"add-on-wrapper\">\n" +
"						<h5>Tasks</h5>\n" +
"						<div class=\"todo\">\n" +
"							<fieldset class=\"todo-list\">\n" +
"								<label class=\"todo-list-item info\">\n" +
"									<input type=\"checkbox\" class=\"todo-list-cb\">\n" +
"									<span class=\"todo-list-mark\"></span>\n" +
"									<span class=\"todo-list-desc\">Attend seminar</span>\n" +
"								</label>\n" +
"								<label class=\"todo-list-item danger\">\n" +
"									<input type=\"checkbox\" class=\"todo-list-cb\">\n" +
"									<span class=\"todo-list-mark\"></span>\n" +
"									<span class=\"todo-list-desc\">UX workshop</span>\n" +
"								</label>\n" +
"								<label class=\"todo-list-item success\">\n" +
"									<input type=\"checkbox\" class=\"todo-list-cb\">\n" +
"									<span class=\"todo-list-mark\"></span>\n" +
"									<span class=\"todo-list-desc\">Pickup kids @4pm</span>\n" +
"								</label>\n" +
"								<label class=\"todo-list-item danger\">\n" +
"									<input type=\"checkbox\" class=\"todo-list-cb\" checked>\n" +
"									<span class=\"todo-list-mark\"></span>\n" +
"									<span class=\"todo-list-desc\">Send wishes</span>\n" +
"								</label>\n" +
"								<label class=\"todo-list-item success\">\n" +
"									<input type=\"checkbox\" class=\"todo-list-cb\">\n" +
"									<span class=\"todo-list-mark\"></span>\n" +
"									<span class=\"todo-list-desc\">Redesign Application</span>\n" +
"								</label>\n" +
"								<label class=\"todo-list-item info\">\n" +
"									<input type=\"checkbox\" class=\"todo-list-cb\">\n" +
"									<span class=\"todo-list-mark\"></span>\n" +
"									<span class=\"todo-list-desc\">Send an email</span>\n" +
"								</label>\n" +
"							</fieldset>\n" +
"						</div>\n" +
"					</div>\n" +
"				</div>\n" +
"				<!-- Addons ends -->");
        out.println("				<!-- Addons starts -->\n" +
"				<div class=\"add-on clearfix\">\n" +
"					<div class=\"add-on-wrapper\">\n" +
"						<h5>Revenue</h5>\n" +
"						<ul class=\"revenue-from\">\n" +
"							<li>\n" +
"								<small>New Delhi</small>\n" +
"								<div class=\"progress progress-xs\">\n" +
"									<div class=\"progress-bar progress-bar-info\" role=\"progressbar\" aria-valuenow=\"56\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 56%\">\n" +
"										<span class=\"sr-only\">56% Complete</span>\n" +
"									</div>\n" +
"								</div>\n" +
"								<span class=\"revenue-perc info\">56%</span>\n" +
"							</li>\n" +
"							<li>\n" +
"								<small>Birmingham</small>\n" +
"								<div class=\"progress progress-xs\">\n" +
"									<div class=\"progress-bar progress-bar-danger\" role=\"progressbar\" aria-valuenow=\"22\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 22%\">\n" +
"										<span class=\"sr-only\">22% Complete</span>\n" +
"									</div>\n" +
"								</div>\n" +
"								<span class=\"revenue-perc danger\">22%</span>\n" +
"							</li>\n" +
"							<li>\n" +
"								<small>California</small>\n" +
"								<div class=\"progress progress-xs\">\n" +
"									<div class=\"progress-bar progress-bar-warning\" role=\"progressbar\" aria-valuenow=\"43\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 43%\">\n" +
"										<span class=\"sr-only\">43% Complete</span>\n" +
"									</div>\n" +
"								</div>\n" +
"								<span class=\"revenue-perc warning\">43%</span>\n" +
"							</li>\n" +
"							<li>\n" +
"								<small>Berlin</small>\n" +
"								<div class=\"progress progress-xs\">\n" +
"									<div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"32\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 32%\">\n" +
"										<span class=\"sr-only\">32% Complete</span>\n" +
"									</div>\n" +
"								</div>\n" +
"								<span class=\"revenue-perc success\">32%</span>\n" +
"							</li>\n" +
"						</ul>\n" +
"					</div>\n" +
"				</div>\n" +
"				<!-- Addons ends -->");
        out.println("				<!-- Addons starts -->\n" +
"				<div class=\"add-on clearfix\">\n" +
"					<h5>Articles</h5>\n" +
"					<ul class=\"articles\">\n" +
"						<li>\n" +
"							<a href=\"#\">\n" +
"								<span class=\"label-bullet\">\n" +
"									&nbsp;\n" +
"								</span>\n" +
"								Raw denim you probably\n" +
"								<span class=\"date\">12th Jan @1:45am / 8 Comments</span>\n" +
"							</a>\n" +
"						</li>\n" +
"						<li>\n" +
"							<a href=\"#\">\n" +
"								<span class=\"label-bullet\">\n" +
"									&nbsp;\n" +
"								</span>\n" +
"								Skateboard dolor brunch\n" +
"								<span class=\"date\">11th Jan @11:19am / 15 Comments</span>\n" +
"							</a>\n" +
"						</li>\n" +
"						<li>\n" +
"							<a href=\"#\" class=\"no-border\">\n" +
"								<span class=\"label-bullet\">\n" +
"									&nbsp;\n" +
"								</span>\n" +
"								accusam / 3 Commentsus terry richardson\n" +
"								<span class=\"date\">10th Jan @8:12am / 13 Comments</span>\n" +
"							</a>\n" +
"						</li>\n" +
"					</ul>\n" +
"				</div>\n" +
"				<!-- Addons ends -->");
        out.println("			</div>\n" +
"			<!-- Right sidebar ends -->");
        out.println("			<!-- Footer starts -->\n" +
"			<footer>\n" +
"				Copyright Everest Admin Panel 2014.\n" +
"			</footer>\n" +
"			<!-- Footer ends -->");
        out.println("		</div>\n" +
"		<!-- Dashboard Wrapper ends -->");
        out.println("		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\n" +
"		<script src=\"js/jquery.js\"></script>\n" +
"\n" +
"		<!-- Include all compiled plugins (below), or include individual files as needed -->\n" +
"		<script src=\"js/bootstrap.min.js\"></script>\n" +
"\n" +
 "		<!-- Sparkline graphs -->\n" +
"		<script src=\"js/sparkline.js\"></script>\n" +
"\n" +
"		<!-- jquery ScrollUp JS -->\n" +
"		<script src=\"js/scrollup/jquery.scrollUp.js\"></script>\n" +
"\n" +
"		<!-- Notifications JS -->\n" +
"		<script src=\"js/alertify/alertify.js\"></script>\n" +
"		<script src=\"js/alertify/alertify-custom.js\"></script>\n" +
"\n" +
"		<!-- Custom Index -->\n" +
"		<script src=\"js/custom.js\"></script>");
        out.println("		<script type=\"text/javascript\">\n" +
"			(function() {\n" +
"				$(document).ready(function() {\n" +
"					var timelineAnimate;\n" +
"					timelineAnimate = function(elem) {\n" +
"						return $(\".timeline.animated .timeline-row\").each(function(i) {\n" +
"							var bottom_of_object, bottom_of_window;\n" +
"							bottom_of_object = $(this).position().top + $(this).outerHeight();\n" +
"							bottom_of_window = $(window).scrollTop() + $(window).height();\n" +
"							if (bottom_of_window > bottom_of_object) {\n" +
"								return $(this).addClass(\"active\");\n" +
"							}\n" +
"						});\n" +
"					};\n" +
"					timelineAnimate();\n" +
"					return $(window).scroll(function() {\n" +
"						return timelineAnimate();\n" +
"					});\n" +
"				});\n" +
"\n" +
"			}).call(this);\n" +
"\n" +
"		</script>");
        out.println("	</body>\n" +
"\n" +
"<!-- Mirrored from jesus.gallery/everest/timeline.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Apr 2015 10:44:31 GMT -->\n" +
"</html>");
}     catch (Exception ex) {
            out.println(ex);
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
