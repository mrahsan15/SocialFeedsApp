
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.exceptions.InstagramException;

public class CommonWidgets {
    Instagram insta = null;
    public CommonWidgets(Instagram instagram,PrintWriter out){
        insta = instagram;
    }
    public void HeadRegion(PrintWriter out){
        out.println("<!DOCTYPE html>");
            out.println("<html>");
            
            out.println("<head>");
            out.println(
"		<meta charset=\"UTF-8\" />\n" +
"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> \n" +
"		<meta name=\"description\" content=\"Everest Admin Panel\" />\n" +
"		<meta name=\"keywords\" content=\"Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap\" />\n" +
"		<meta name=\"author\" content=\"Bootstrap Gallery\" />\n");
            out.println(
"		<link rel=\"shortcut icon\" href=\"img/favicon.ico\">\n");
            out.println(
"		<title>Social Feeds App</title>");
            out.println(
"		<!-- Bootstrap CSS -->\n" +
"		<link href=\"css/bootstrap.css\" rel=\"stylesheet\" media=\"screen\">\n");
            out.println("<meta content=\"width=320px, initial-scale=1, user-scalable=yes\" name=\"viewport\" />"
                    + "    <script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js\"></script>"
                    + "<script type=\"text/javascript\" src=\"js/overlaypopup.js\"></script>\n" +
"               <link type=\"text/css\" rel=\"stylesheet\" href=\"css/overlaypopup.css\" />");
            out.println(
"		<!-- Animate CSS -->\n" +
"		<link href=\"css/animate.css\" rel=\"stylesheet\" media=\"screen\">\n");
            out.println(
"		<!-- Alertify CSS -->\n" +
"		<link href=\"css/alertify/alertify.core.css\" rel=\"stylesheet\">\n" +
"		<link href=\"css/alertify/alertify.default.css\" rel=\"stylesheet\">\n");
            out.println(
"		<!-- Main CSS -->\n" +
"		<link href=\"css/main.css\" rel=\"stylesheet\" media=\"screen\">\n");
            out.println(
"		<!-- Datepicker CSS -->\n" +
"		<link rel=\"stylesheet\" type=\"text/css\" href=\"css/datepicker.css\">\n");
            out.println("<link rel=\"stylesheet\" href=\"css/summernote.css\">");
            out.println(
"		<!-- Font Awesome -->\n" +
"		<link href=\"fonts/font-awesome.min.css\" rel=\"stylesheet\">\n");
            out.println(
"		<!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->\n" +
"		<!--[if lt IE 9]>\n" +
"			<script src=\"js/html5shiv.js\"></script>\n" +
"			<script src=\"js/respond.min.js\"></script>\n" +
"		<![endif]-->\n");
            
            out.println("</head>");
    }
    public void Logo(PrintWriter out){
        out.println(
"			<!-- Logo starts -->\n" +
"			<div class=\"logo\">\n" +
"				<a href=\"#\">\n" +
//"					<img src=\"img/logo.png\" alt=\"logo\">\n" +
"					<span class=\"menu-toggle hidden-xs\">\n" +
"						<i class=\"fa fa-bars\"></i>\n" +
"					</span>\n" +
"				</a>\n" +
"			</div>\n" +
"			<!-- Logo ends -->\n" );
            
    }
    public void Search(PrintWriter out){
        out.println(
"			<!-- Custom Search Starts -->\n" +
"			<div class=\"custom-search pull-left hidden-xs hidden-sm\">\n" +
"				<input type=\"text\" class=\"search-query\" placeholder=\"Search here\">\n" +
"				<i class=\"fa fa-search\"></i>\n" +
"			</div>\n" +
"			<!-- Custom Search Ends -->\n" );
            
    }
    public void RightNavBar(PrintWriter out){
        try {
            out.println(
                    "			<!-- Mini right nav starts -->\n" +
                            "			<div class=\"pull-right\">\n");
            out.println(
                    "				<ul id=\"mini-nav\" class=\"clearfix\">\n" +
                            "					<li class=\"list-box hidden-lg hidden-md hidden-sm\" id=\"mob-nav\">\n" +
                            "						<a href=\"#\">\n");
            out.println(
                    "							<i class=\"fa fa-reorder\"></i>\n" +
                            "						</a>\n" +
                            "					</li>\n");
            out.println(
                    "					<li class=\"list-box dropdown hidden-xs\">\n");
            out.println(
                    "						<a id=\"drop7\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
                            "							<i class=\"fa fa-image\"></i>\n" +
                            "						</a>\n");
            out.println(
                    "						<span class=\"info-label info-bg animated rubberBand\">0</span>\n");
            out.println(
                    "						<ul class=\"blog-gallery dropdown-menu fadeInDown animated clearfix recent-tweets\">\n");
//            out.println(
//"							<li>\n" +
//"								<img src=\"img/user1.jpg\" alt=\"User\">\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<img src=\"img/user2.jpg\" alt=\"User\">\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<img src=\"img/user3.jpg\" alt=\"User\">\n" +
//"							</li>\n");
//            out.println("<li>\n" +
//                    "<img src=\"img/user4.jpg\" alt=\"User\">\n" +
//                    "</li>\n");
//            out.println("<li>\n" +
//                    "<img src=\"img/user5.jpg\" alt=\"User\">\n" +
//                    "</li>\n");
//            out.println("<li>\n" +
//                    "<img src=\"img/user6.jpg\" alt=\"User\">\n" +
//                    "</li>\n");
//            out.println("<li>\n" +
//                    "<img src=\"img/user7.jpg\" alt=\"User\">\n" +
//                    "</li>\n");
//            out.println(
//"							<li>\n" +
//"								<img src=\"img/user8.jpg\" alt=\"User\">\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<img src=\"img/user9.jpg\" alt=\"User\">\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<img src=\"img/user3.jpg\" alt=\"User\">\n" +
//"							</li>\n");
            out.println(
                    "						</ul>\n");
            out.println(
                    "					</li>\n");
            out.println(
                    "					<li class=\"list-box dropdown hidden-xs\">\n");
            out.println(
                    "						<a id=\"drop5\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
                            "							<i class=\"fa fa-th\"></i>\n" +
                            "						</a>\n");
            out.println(
                    "						<span class=\"info-label success-bg animated rubberBand\">0</span>\n");
            out.println(
                    "						<ul class=\"dropdown-menu fadeInDown animated quick-actions\">\n" );
            out.println(
                    "							<li class=\"plain\">Recently Viewed</li>\n");
//            out.println(
//"							<li>\n");
//            out.println(
//"								<a href=\"Profile\">\n" +
//"									<i class=\"fa fa-file-word-o text-success\"></i>\n" +
//"									<p>Profile</p>\n" +
//"								</a>\n" );
//            out.println(
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<a href=\"gallery.html\">\n" +
//"									<i class=\"fa fa-image text-danger\"></i>\n" +
//"									<p>Gallery</p>\n" +
//"								</a>\n" +
//"							</li>\n" );
//            out.println(
//"							<li>\n" +
//"								<a href=\"timeline\">\n" +
//"									<i class=\"fa fa-list-ol text-info\"></i>\n" +
//"									<p>Timeline</p>\n" +
//"								</a>\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<a href=\"graphs.html\">\n" +
//"									<i class=\"fa fa-map-marker text-warning\"></i>\n" +
//"									<p>Charts</p>\n" +
//"								</a>\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<a href=\"editor.html\">\n" +
//"									<i class=\"fa fa-pencil text-danger\"></i>\n" +
//"									<p>Editor</p>\n" +
//"								</a>\n" +
//"							</li>\n");
//            out.println(
//"							<li>\n" +
//"								<a href=\"blog.html\">\n" +
//"									<i class=\"fa fa-file-text text-success\"></i>\n" +
//"									<p>Blog</p>\n" +
//"								</a>\n" +
//"							</li>\n");
            out.println(
                    "						</ul>\n" );
            out.println(
                    "					</li>\n" );
            out.println(
                    "					<li class=\"list-box dropdown hidden-xs\">\n");
            out.println(
                    "						<a id=\"drop1\" href=\"#\" role=\"button\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\n" +
                            "							<i class=\"fa fa-bell\"></i>\n" +
                            "						</a>\n");
            out.println(
                    "						<span class=\"info-label danger-bg animated rubberBand\">0</span>\n" );
            out.println(
                    "						<ul class=\"dropdown-menu bounceIn animated messages\">\n");
            out.println(
                    "							<li class=\"plain\">\n" +
                            "								Messages\n" +
                            "							</li>\n");
            out.println(
                    "							<li>\n" );
            out.println(
                    "								<div class=\"user-pic\">\n" +
                            "									<img src=\"img/user4.jpg\" alt=\"User\">\n" +
                            "								</div>\n" );
            out.println(
                    "								<div class=\"details\">\n" +
                            "									<strong class=\"text-danger\">Wilson</strong>\n" +
                            "									<span>Uploaded 28 new files yesterday.</span>\n");
            out.println(
                    "									<div class=\"progress progress-xs no-margin\">\n" );
            out.println(
                    "										<div class=\"progress-bar progress-bar-info\" role=\"progressbar\" aria-valuenow=\"90\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 90%;\">\n" +
                            "										</div>\n");
            out.println(
                    "									</div>\n");
            out.println(
                    "								</div>\n");
            out.println(
                    "							</li>\n" );
            out.println(
                    "							<li>\n" );
            out.println(
                    "								<div class=\"user-pic\">\n" +
                            "									<img src=\"img/user1.jpg\" alt=\"User\">\n" +
                            "								</div>\n");
            out.println(
                    "								<div class=\"details\">\n");
            out.println(
                    "									<strong class=\"text-danger\">Adams</strong>\n" +
                            "									<span>Got 12 new messages.</span>\n");
            out.println(
                    "									<div class=\"progress progress-xs no-margin\">\n" );
            out.println(
                    "										<div class=\"progress-bar progress-bar-danger\" role=\"progressbar\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 50%;\">\n" +
                            "										</div>\n" );
            out.println(
                    "									</div>\n" );
            out.println(
                    "								</div>\n" +
                            "							</li>\n" +
                            "							<li>\n");
            out.println(
                    "								<div class=\"user-pic\">\n" +
                            "									<img src=\"img/user3.jpg\" alt=\"User\">\n" +
                            "								</div>\n");
            out.println(
                    "								<div class=\"details\">\n");
            out.println(
                    "									<strong class=\"text-info\">Sam</strong>\n" +
                            "									<span>Uploaded new project files today.</span>\n");
            out.println(
                    "									<div class=\"progress progress-xs no-margin\">\n" +
                            "										<div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"70\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 70%;\">\n" +
                            "										</div>\n" +
                            "									</div>\n");
            out.println(
                    "								</div>\n" );
            out.println(
                    "							</li>\n" +
                            "							<li>\n" );
            out.println(
                    "								<div class=\"user-pic\">\n" +
                            "									<img src=\"img/user5.jpg\" alt=\"User\">\n" +
                            "								</div>\n");
            out.println(
                    "								<div class=\"details\">\n" +
                            "									<strong class=\"text-info\">Jennifer</strong>\n" +
                            "									<span>128 new purchases last 3 hours.</span>\n" +
                            "									<div class=\"progress progress-xs no-margin\">\n" +
                            "										<div class=\"progress-bar progress-bar-danger\" role=\"progressbar\" aria-valuenow=\"30\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 30%;\">\n" +
                            "										</div>\n" +
                            "									</div>\n" +
                            "								</div>\n");
            out.println(
                    "							</li>\n" +
                            "						</ul>\n" +
                            "					</li>\n");
            out.println(
                    "					<li class=\"list-box user-profile hidden-xs\">\n" +
                            "						<a href=\"#\" class=\"user-avtar animated rubberBand\">\n" +
                            "							<img src=\""+insta.getCurrentUserInfo().getData().getProfilePicture()+"\" alt=\"user avatar\">\n" +
                            "						</a>\n" +
                            "					</li>\n");
            out.println(
                    "				</ul>\n" +
                            "			</div>\n");
            out.println(
                    "			<!-- Mini right nav ends -->\n");
        } catch (InstagramException ex) {
            Logger.getLogger(CommonWidgets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CurrentUser(PrintWriter out){
        UserInfo info = null;
        try {
            info = insta.getCurrentUserInfo();
        
            String ProfilePic = info.getData().getProfilePicture();
            String FullName  = info.getData().getFullName();

            out.println(
"			<!-- Current User Starts -->\n" +
"			<div class=\"current-user\">\n");
            out.println(
"				<div class=\"user-avatar animated rubberBand\">\n" +
"					<img src=\""+ProfilePic+"\" alt=\"Current User\">\n" +
"					<span class=\"busy\"></span>\n" +
"				</div>\n");
            out.println(
"				<div class=\"user-name\">Welcome Mr. "+FullName+"</div>\n");
            out.println(
"				<ul class=\"user-links\">\n");
            out.println(
"					<li>\n" +
"						<a href=\"Profile\">\n" +
"							<i class=\"fa fa-user text-success\"></i>\n" +
"						</a>\n" +
"					</li>\n");
            out.println(
"					<li>\n" +
"						<a href=\"invoice.html\">\n" +
"							<i class=\"fa fa-file-pdf-o text-warning\"></i>\n" +
"						</a>\n" +
"					</li>\n");
            out.println(
"					<li>\n" +
"						<a href=\"components.html\">\n" +
"							<i class=\"fa fa-sliders text-info\"></i>\n" +
"						</a>\n" +
"					</li>\n");
            out.println(
"					<li>\n" +
"						<a href=\"login.html\">\n" +
"							<i class=\"fa fa-sign-out text-danger\"></i>\n" +
"						</a>\n" +
"					</li>\n");
            out.println(
"				</ul>\n" +
"			</div>\n" +
"			<!-- Current User Ends -->\n");
        }catch(Exception ex){
            
        }
    }
    public void Menu(PrintWriter out, String Page){
        String CurrentPage = "<span class=\"current-page\"></span>";
        String SubmenuActive = "";
        String SubmenuActiveDisplay = "";
        String DashboardURL= "dashboard";
        String TimelineURL = "timeline";
        String BlogURL = "";
        String GraphsURL = "";
        String CalendarURL = "";
        String GalleryURL = "";
        String VectorMapsURL = "";
        String InvoiceURL = "";
        String ProfileURL = "Profile";
        String PricingURL = "";
        String LoginURL = "";
        String ErrorURL = "";
        String BasicTemplateURL = "";
        String Dashboard = "<li>\n" +
"						<a href='"+DashboardURL+"'>\n" +
"							<i class=\"fa fa-desktop\"></i>\n" +
"							<span>Dashboard</span>\n" + 
                                                        
"						</a>\n" +
"					</li>\n";
        
        String Timeline = "<li>\n" +
"						<a href='"+TimelineURL+"'>\n" +
"							<i class=\"fa fa-sliders\"></i> \n" +
"							<span>Timeline</span>\n" +
                                                        
"						</a>\n" +
"					</li>";
        String Profile="<li>\n" +
                "<a href='"+ProfileURL+"'>\n" +
"                   <i class=\"fa fa-user\"></i> \n" +
"                       <span>Profile</span>\n" +
"		</a>\n" +
"	</li>\n";
        
        
        String Graphs = "<li>\n" +
"						<a href='"+GraphsURL+"'>\n" +
"							<i class=\"fa fa-flask\"></i> \n" +
"							<span>Graphs</span>\n" +
"						</a>\n" +
"					</li>";
        
        
        String Calendar = "<li>\n" +
"						<a href='"+CalendarURL+"'>\n" +
"							<i class=\"fa fa-calendar\"></i> \n" +
"							<span>Calendar</span>\n" +
"						</a>\n" +
"					</li>";
        String Gallery ="<li>\n" +
"						<a href='"+GalleryURL+"'>\n" +
"							<i class=\"fa fa-image\"></i> \n" +
"							<span>Gallery</span>\n" +
"						</a>\n" +
"					</li>";
        String VectorMaps = "<li>\n" +
"						<a href='"+VectorMapsURL+"'>\n" +
"							<i class=\"fa fa-globe\"></i> \n" +
"							<span>Vector Maps</span>\n" +
"						</a>\n" +
"					</li>";
        String Invoice = "<li>\n" +
"                           <a href='"+InvoiceURL+"'>\n" +
"                               <span>Invoice</span>\n" +
"                           </a>\n" +
"                         </li>\n";
        
        
        String Blog="<li>\n" +
"								<a href='"+BlogURL+"'>\n" +
"									<span>Blog</span>\n" +
"								</a>\n" +
"							</li>\n" ;
        String Pricing = "<li>\n" +
"								<a href=\""+PricingURL+"\">\n" +
"									<span>Pricing</span>\n" +
"								</a>\n" +
"							</li>\n";
        String Login="<li>\n" +
"								<a href='"+LoginURL+"'>\n" +
"									<span>Login</span>\n" +
"								</a>\n" +
"							</li>\n";
        String Error = "<li>\n" +
"								<a href='"+ErrorURL+"'>\n" +
"									<span>404</span>\n" +
"								</a>\n" +
"							</li>\n" ;
        
        String BasicTemplate = "<li>\n" +
"                                   <a href='"+BasicTemplateURL+"'>\n" +
"                                   <span>Basic Template</span>\n" +
"				</a>\n" +
"				</li>\n";
        
        
        switch(Page){
            case "Dashboard":
                Dashboard = "<li class=\"highlight\">\n" +
"						<a href=''>\n" +
"							<i class=\"fa fa-desktop\"></i>\n" +
"							<span>Dashboard</span>\n" + 
                                                        "<span class=\"current-page\"></span>"+
"						</a>\n" +
"					</li>\n";
                break;
            case "Timeline":
                Timeline = "<li class=\"highlight\">\n" +
"						<a href=''>\n" +
"							<i class=\"fa fa-sliders\"></i> \n" +
"							<span>Timeline</span>\n" +
                                                        "<span class=\"current-page\"></span>"+
"						</a>\n" +
"					</li>";
                break;
            case "Profile":
                Profile = "<li class=\"highlight\">\n" +
                        "<a href='"+ProfileURL+"'>\n" +
        "                   <i class=\"fa fa-user\"></i> \n" +
"                               <span>Profile</span>\n" +
                        "<span class=\"current-page\"></span>"+
"                        </a>\n" +
"               	</li>\n";
                break;
                
            case "Graphs":
                Graphs= "<li>\n" +
"						<a href='"+GraphsURL+"'>\n" +
"							<i class=\"fa fa-flask\"></i> \n" +
"							<span>Graphs</span>\n" +
"						</a>\n" +
"					</li>";
                break;
                
            case "Calendar":
                Calendar = "<li>\n" +
"						<a href='"+CalendarURL+"'>\n" +
"							<i class=\"fa fa-calendar\"></i> \n" +
"							<span>Calendar</span>\n" +
"						</a>\n" +
"					</li>";
                break;
                
            case "Gallery":
                Gallery = "<li>\n" +
"						<a href='"+GalleryURL+"'>\n" +
"							<i class=\"fa fa-image\"></i> \n" +
"							<span>Gallery</span>\n" +
"						</a>\n" +
"					</li>";
                break;
                
                
            case "VectorMaps":
                VectorMaps = "<li>\n" +
"						<a href='"+VectorMapsURL+"'>\n" +
"							<i class=\"fa fa-globe\"></i> \n" +
"							<span>Vector Maps</span>\n" +
"						</a>\n" +
"					</li>";
                break;
                
            case "Invoice":
                SubmenuActive =" style=\"display:block;\"";
                SubmenuActiveDisplay= " class=\"select\"";
                Invoice = "<li>\n" +
"                           <a href='"+InvoiceURL+"' class=\"select\">\n" +
"                               <span>Invoice</span>\n" +
"                           </a>\n" +
"                         </li>";
                break;
            case "Blog":
                SubmenuActive =" highlight active";
                SubmenuActiveDisplay= " style=\"display:block;\"";
                Blog = "<li>\n" +
"								<a href='"+BlogURL+"' class=\"select\">\n" +
"									<span>Profile</span>\n" +
"								</a>\n" +
"							</li>";
                break;
                
            case "Pricing":
                SubmenuActive =" style=\"display:block;\"";
                SubmenuActiveDisplay= " class=\"select\"";
                Pricing = "<li>\n" +
"								<a href=\""+PricingURL+"\" class=\"select\">\n" +
"									<span>Pricing</span>\n" +
"								</a>\n" +
"							</li>";
                break;
                
            case "Login":
                SubmenuActive =" style=\"display:block;\"";
                SubmenuActiveDisplay= " class=\"select\"";
                Login = "<li>\n" +
"								<a href='"+LoginURL+"' class=\"select\">\n" +
"									<span>Login</span>\n" +
"								</a>\n" +
"							</li>";
                break;
                
            case "Error":
                SubmenuActive =" style=\"display:block;\"";
                SubmenuActiveDisplay= " class=\"select\"";
                Error = "<li>\n" +
"								<a href='"+ErrorURL+"' class=\"select\">\n" +
"									<span>404</span>\n" +
"								</a>\n" +
"							</li>";
                break;
                
            case "BasicTemplate":
                SubmenuActive =" style=\"display:block;\"";
                SubmenuActiveDisplay= " class=\"select\"";
                BasicTemplate = "<li>\n" +
"                                   <a href='"+BasicTemplateURL+"' class=\"select\">\n" +
"                                   <span>Basic Template</span>\n" +
"				</a>\n" +
"				</li>";
                break;
                
        }
        
        
        
        
        out.println(
"			<!-- Menu start -->\n" +
"			<div id='menu'>\n" +
"				<ul>\n");
            
            out.println(Dashboard);
            out.println(Timeline);
            
            out.println(Profile);
            out.println(Graphs);
            out.println(Calendar);
            out.println(Gallery);
            out.println(VectorMaps);
            out.println(
"					<li class='has-sub'"+ SubmenuActive +">\n" +
"						<a href='#'>\n" +
"							<i class=\"fa fa-flask\"></i> \n" +
"							<span>Bonus Pages</span>\n" +
"						</a>\n" +
"						<ul "+SubmenuActiveDisplay+">\n" 
        
        +Invoice
        +Blog+
        Pricing+
        Login+
        Error+
        BasicTemplate+

"						</ul>\n" +
"					</li>\n");
            out.println(
"					<li class='has-sub'>\n" +
"						<a href='#'>\n" +
"							<i class=\"fa fa-tasks\"></i>\n" +
"							<span>UI Elements</span>\n" +
"						</a>\n" +
"						<ul>\n" +
"							<li>\n" +
"								<a href='buttons.html'>\n" +
"									<span>Buttons</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='panels.html'>\n" +
"									<span>Panels</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='icons.html'>\n" +
"									<span>Icons</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='grid.html'>\n" +
"									<span>Grid</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='components.html'>\n" +
"									<span>Components</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='notifications.html'>\n" +
"									<span>Notifications</span>\n" +
"								</a>\n" +
"							</li>\n" +
"						</ul>\n" +
"					</li>\n");
            out.println(
"					<li class='has-sub'>\n" +
"						<a href='#'>\n" +
"							<i class=\"fa fa-columns\"></i>\n" +
"							<span>Forms</span>\n" +
"						</a>\n" +
"						<ul>\n" +
"							<li>\n" +
"								<a href='form-elements.html'>\n" +
"									<span>Form Elements</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='form-layouts.html'>\n" +
"									<span>Form Layouts</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='editor.html'>\n" +
"									<span>Editor</span>\n" +
"								</a>\n" +
"							</li>\n" +
"						</ul>\n" +
"					</li>\n");
            out.println(
"					<li class='has-sub'>\n" +
"						<a href='#'>\n" +
"							<i class=\"fa fa-bars\"></i> \n" +
"							<span>Tables</span>\n" +
"						</a>\n" +
"						<ul>\n" +
"							<li>\n" +
"								<a href='tables.html'>\n" +
"									<span>Normal Tables</span>\n" +
"								</a>\n" +
"							</li>\n" +
"							<li>\n" +
"								<a href='datatables.html'>\n" +
"									<span>Data Tables</span>\n" +
"								</a>\n" +
"							</li>\n" +
"						</ul>\n" +
"					</li>\n");
            out.println(
"				</ul>\n" +
"			</div>\n" +
"			<!-- Menu End -->\n");
            
    }
}