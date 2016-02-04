/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class Profile extends HttpServlet {
    HttpServletRequest Request = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("username");  
        String source = request.getParameter("source");
//        String user = "705245394";
//        String source = "twitter";
//        
        
//        response.sendRedirect("http://"+source+".com/mr_ahsan15");
        InstagramService service = null ;
        String code = "";
        Twitter twitter = (Twitter) request.getAttribute("twitter");
        try{
            if(twitter.getScreenName().equals("null")){
                
            }
            else{
                twitter = new TwitterFactory().getInstance();
            }
        }catch(Exception ex){
            twitter = new TwitterFactory().getInstance();
            System.out.println("No Attribute! Instant Factory for Twitter");
        }
        try {
            service = new InstagramAuthService()
            .apiKey("e0bbe4b568cd453e925d7962ad2b9c7c")
            .apiSecret("f11f9582bef947b4b28bf871ce36a06c")
            .callback("http://192.168.1.100:8080/SocialFeedsApp/dashboard")
            .scope("basic, public_content,comments,follower_list,relationships,likes")
            .build();
            System.out.println("Worked1!");
            code = request.getParameter("code");
            System.out.println("Worked2!");
            
            Cookie[] cookies = request.getCookies();
            String token = "";
            for(int i = 0; i< cookies.length; i++){
                if((cookies[i].getName()).equals("token")){
                    token = cookies[i].getValue();
                }
            }
            System.out.println("Worked3!");
            
            if(token.equals("")){
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
                    PrintPage(insta,out,user,source,twitter);
                }

            }else{
                Instagram insta = new Instagram(new Token(token,""));
                System.out.println("Worked5!");
                PrintPage(insta, out,user,source,twitter);
            }
            System.out.println("Worked4!");
            
        }   
        catch(Exception ex){
            System.out.println(ex);
            response.sendRedirect(service.getAuthorizationUrl(null));
        }
}
    
    public void PrintPage(Instagram insta, PrintWriter out, String user,String source,Twitter twitter){
        UserInfo info = null;
        CommonWidgets widget = new CommonWidgets(insta, out);
        ArrayList commentsList = new ArrayList();
        try {
            info = insta.getCurrentUserInfo();
            String CopyRightSyntax = "Copyright Apisylux Dashboard Panel 2016";
            String ProfilePic = info.getData().getProfilePicture();
            String FullName  = info.getData().getFullName();
            String CurrentUserID = info.getData().getId();
            String CurrentUserName = info.getData().getUsername();
            String Bio = info.getData().getBio();
            int follower = info.getData().getCounts().getFollowedBy();
            int following = info.getData().getCounts().getFollows();
            int mediacount = info.getData().getCounts().getMedia();
            
            
            String UserProfilePic = "" ;
            String UserFullName = "" ;
            String UserBio = "";
            String UserName = user;
            int UserFollowers = 0;
            int UserFollowings= 0;
            int UserMediaCount = 0;
            int favourites= 0;
            UserInfo userinfo ;
            System.out.println("Username is: "+user);
            try{
                if((user.equals("null"))){
                    user = insta.getCurrentUserInfo().getData().getId();
                }else if(source.equals("insta")){
                    userinfo = insta.getUserInfo(user);
                    UserProfilePic = userinfo.getData().getProfilePicture();
                    UserFullName = userinfo.getData().getFullName();
                    UserBio = userinfo.getData().getBio();
                    UserFollowers = userinfo.getData().getCounts().getFollowedBy();
                    UserFollowings = userinfo.getData().getCounts().getFollows();
                    UserMediaCount = userinfo.getData().getCounts().getMedia();
                }else if(source.equals("twitter")){
                    System.out.println("Get: "+twitter.users().showUser("GetUjala").getProfileImageURL());
                    
                    twitter4j.User userdetail = twitter.users().showUser(Long.parseLong(user));
                    UserProfilePic = userdetail.getOriginalProfileImageURL();
                    UserFullName = userdetail.getName();
                    UserBio = userdetail.getDescription();
                    UserFollowers = userdetail.getFollowersCount();
                    UserFollowings = userdetail.getFriendsCount();
                    UserMediaCount = userdetail.getStatusesCount();
                    favourites = userdetail.getFavouritesCount();
                }
            }catch(NullPointerException ex){
                System.out.println("Parameter is Null.");
                user = insta.getCurrentUserInfo().getData().getId();
//                userinfo = insta.getUserInfo(user);
                UserProfilePic = ProfilePic;
                UserFullName = FullName;
                UserBio = Bio;
                UserName = CurrentUserName;
                UserFollowers = follower;
                UserFollowings = following;
                UserMediaCount = mediacount;
            }
            
            widget.HeadRegion(out);

            out.println(
"\n" +
"	<body>\n" );
            out.println(
"\n" +
"		<!-- Header Start -->\n" +
"		<header>\n" +
"\n" );
            
            widget.Logo(out);

            widget.Search(out);

            
            widget.RightNavBar(out);

            
            out.println(
"\n" +
"		</header>\n" +
"		<!-- Header ends -->\n" );
            out.println(
"\n" +
"		<!-- Left sidebar starts -->\n" +
"		<aside id=\"sidebar\">\n" );
            
            widget.CurrentUser(out);

            
            
            widget.Menu(out, "Profile");
            out.println(
"			<!-- Freebies Starts -->\n" +
"			<div class=\"freebies\">\n" +
"				\n" +
"				<!-- Sidebar Extras -->      \n" +
"				<div class=\"sidebar-addons\">\n" +
"					<ul class=\"views\">\n" +
"						<li>\n" +
"							<i class=\"fa fa-circle-o text-success\"></i>\n" +
"							<div class=\"details\">\n" +
"								<p>Signups</p>\n" +
"							</div>\n" +
"							<span class=\"label label-success\">8</span>\n" +
"						</li>\n" +
"						<li>\n" +
"							<i class=\"fa fa-circle-o text-info\"></i>\n" +
"							<div class=\"details\">\n" +
"								<p>Users Online</p>\n" +
"							</div>\n" +
"							<span class=\"label label-info\">7</span>\n" +
"						</li> \n" +
"						<li>\n" +
"							<i class=\"fa fa-circle-o text-danger\"></i>\n" +
"							<div class=\"details\">\n" +
"								<p>Images Uploaded</p>\n" +
"							</div>\n" +
"							<span class=\"label label-danger\">4</span>\n" +
"						</li>         \n" +
"					</ul>\n" +
"				</div>\n" +
"\n" +
"			</div>\n" +
"			<!-- Freebies Starts -->\n" );
            out.println(
"\n" +
"		</aside>\n" +
"		<!-- Left sidebar ends -->\n" );
            out.println(
"\n" +
"		<!-- Dashboard Wrapper starts -->\n" +
"		<div class=\"dashboard-wrapper\">\n" );
            out.println(
"\n" +
"			<!-- Top Bar starts -->\n" +
"			<div class=\"top-bar\">\n" +
"				<div class=\"page-title\">\n" +
"					User Profile\n" +
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
"							<h5>Followings</h5>\n" +
"						</div>\n" +
"					</li>\n" +
"				</ul>\n" +
"			</div>\n" +
"			<!-- Top Bar ends -->\n" );
            out.println(
"\n" +
"			<!-- Main Container starts -->\n" +
"			<div class=\"main-container\">\n" +
"\n" +
"				<!-- Container fluid Starts -->\n" +
"				<div class=\"container-fluid\">\n" +
"					\n" +
"					<!-- Spacer Starts -->\n" +
"					<div class=\"spacer\">\n" +
"\n" +
"						<!-- Row start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-md-12 col-sm-12 col-sx-12\">\n" +
"								<div class=\"current-profile\" style=\"background:"+UserProfilePic+";\">\n" +
"									<div class=\"user-bg\"></div>\n" +
"									<div class=\"user-pic\" style=\"background:url("+UserProfilePic+") no-repeat;\">&nbsp; </div>\n" +
"									<div class=\"user-details\">\n" +
"										<h4 class=\"user-name\">"+UserFullName+"<i>!</i></h4>\n" +
"										<h5 class=\"description\">"+UserBio+".</h5>\n" +
"									</div>\n" +
"									<div class=\"social-list\">\n" +
"										<div class=\"row\">\n" +
"											<div class=\"col-md-6 col-md-offset-3\">\n" +
"												<div class=\"row\">\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+UserMediaCount+"</h3>\n" +
"														<small>Posts</small>\n" +
"													</div>\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+UserFollowers+"</h3>\n" +
"														<small>Followers</small>\n" +
"													</div>\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+UserFollowings+"</h3>\n" +
"														<small>Followings</small>\n" +
"													</div>\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+favourites+"</h3>\n" +
"														<small>Favourites</small>\n" +
"													</div>\n" +
"												</div>\n" +
"											</div>\n" +
"										</div>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n" +
"						</div>\n" +
"						<!-- Row end -->\n" +
"\n" +
"						<!-- Row start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-sx-12\">\n" +
"								<!-- Timeline starts -->\n" +
"								<div class=\"timeline animated no-padding\">\n" );
            String Caption = "";
            String MediaUrl="";
            String VideoTags ="";
            String UUserName = "";
            String UUserFullName = "";
            String UUserProfilePic = "";
            String time = "";
            int medianumber = 0;
            int likes = 0;
            int comments = 0;

            
            if(source.equals("twitter")){
                
                twitter4j.Paging paging = new twitter4j.Paging(1, 200);
                ResponseList<Status> listmf= twitter.getUserTimeline(Long.parseLong(user), paging);
            
                
                for(int i = 0 ; i < listmf.size() ;i++){
                    Status feeddata = listmf.get(i);
//                    String MediaType = feeddata.getType();
                    UUserFullName = feeddata.getUser().getName();
                    UUserProfilePic = feeddata.getUser().getProfileImageURL();
                    UUserName = feeddata.getUser().getScreenName();
//                    time = feeddata.getCreatedAt()+"000";
                    java.util.Date date = feeddata.getCreatedAt();
                    SimpleDateFormat Time = new SimpleDateFormat("hh:mm a");
                    SimpleDateFormat Date = new SimpleDateFormat("MMM d, yy");
                    likes = feeddata.getFavoriteCount();
                    comments = feeddata.getRetweetCount();
                    String TweetText = feeddata.getText();
//                    feeddata.getUser()
                    
                    out.println("<div class=\"timeline-row\">\n" +
"							<div class=\"timeline-time\">\n" +
"								"+Time.format(date)+"<small>"+Date.format(date)+"</small>\n" +
"							</div>\n" +
"							<div class=\"timeline-icon\">\n" +
"								<div class=\"twitter-bg\">\n" +
"									<i class=\"fa fa-twitter\"></i>\n" +
"								</div>\n" +
"							</div>\n" +
"							<div class=\"panel timeline-content\">\n" +
"								<div class=\"panel-body\">\n" +
//"									<h2 class=\"text-info\">"+UUserName+"</h2>\n" +
                                                        "<div style=\"float:left; padding:0;\">"+
"<img src=\""+UUserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">\n " +
"<div style=\"padding:0; margin-left:10px; float:right;\">"
                + "<font size=\"+1\"><Strong>"+UUserFullName+"</strong></font>"
                + "<br>("+UUserName+")\n<br>"
                + "</div></div><br>" +

"									<p style=\"padding-top: 50px;\">" +
//"										<i class=\"fa  fa-lg fa-quote-left\"></i>"+
                            TweetText+
//                            "<i class=\"fa fa-lg fa-quote-right\"></i>\n" +
"									</p>\n" +

"								</div>\n" +
"							</div>\n" +
"						</div>\n" +
"						");
                    
                }
                
                
                
                
            }else{
                
            List<MediaFeedData> listmf = insta.getRecentMediaFeed(user).getData();
            
            for(int i = 0 ; i < listmf.size() ;i++){
                MediaFeedData feeddata = listmf.get(i);
                String MediaType = feeddata.getType();
                UUserFullName = feeddata.getUser().getFullName();
                UUserProfilePic = feeddata.getUser().getProfilePictureUrl();
                UUserName = feeddata.getUser().getUserName();
                time = feeddata.getCreatedTime()+"000";
                Date date = new Date(Long.parseLong(time));
                SimpleDateFormat Time = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat Date = new SimpleDateFormat("MMM d, yy");
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
                    VideoTags = "<video width=\"320\" height=\"320\" autoplay><source src=\""+MediaUrl+"\" type=\"video/mp4\"/></video>";
                }
                

            
            out.println(
"									<div class=\"timeline-row\">\n" +
"										<div class=\"timeline-time\">\n" +
"											"+Time.format(date)+"<small>"+Date.format(date)+"</small>\n" +
"										</div>\n" +
"										<div class=\"timeline-icon\">\n" +
"											<div class=\"linkedin-bg\">\n" +
"												<i class=\"fa fa-image\"></i>\n" +
"											</div>\n" +
"										</div>\n" +
"										<div class=\"panel timeline-content\">\n" +
"											<div class=\"panel-body\">\n" +
"<div style=\"float:left; padding:0;\">"+
"<img src=\""+UUserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">\n " +
"<div style=\"padding:0; margin-left:10px; float:right;\">"
                + "<font size=\"+1\"><Strong>"+UUserFullName+"</strong></font>"
                + "<br>("+UUserName+")\n<br>"
                + "</div></div><br>" +

"" +
VideoTags+
        
        
//"												<img class=\"img-responsive\" src=\""+UUserProfilePic+"\" alt=\"User\" />\n" +
"												<p>\n" + Caption+
//"													Sed pretium, ligula sollicitudin laoreet viverra, tortor libero sodales leo, eget blandit nunc tortor eu nibh. Nullam mollis. Ut justo. Suspendisse potenti.\n" +
"												</p>\n" 
                        + "<button data-toggle=\"button\" class=\"btn btn-danger\"><i class=\"fa fa-thumbs-up \"></i>"+likes+"</button>"
                + "<a class=\"show-popup\" href=\"#\" data-showpopup=\""+(i+1)+"\" ><button type=\"button\" class=\"btn btn-info\">"+comments+"</button></a>"+

"											</div>\n" +
"										</div>\n" +
"									</div>\n" );
            List<CommentData> comment = feeddata.getComments().getComments();
            
            String popup1 = "<div class=\"overlay-content popup"+(i+1)+"\"  style=\"display: block;top: 20px;padding-top: 10px;\">"
                    + "<div class=\"closeframe\" style=\"margin-left: 91%;\">    <button class=\"close-btn\">Close</button></div>\n" +
"<table>\n" ;
            String popup2 = "";
            String completepopup = "";
//            System.out.println(comment.size());
            for(int z = 0; z < comment.size(); z++){
                //comment.get(z).getCreatedTime();
                String CommentText = comment.get(z).getText();
                String USERID = comment.get(z).getCommentFrom().getId() ;
                String USERNAME= comment.get(z).getCommentFrom().getUsername();
//                System.out.println(CommentText);
                popup2 =popup2 +"<tr>" + 
                            "<td align=\"left\" style=\"width:100%;\"><a href=\"Profile?username="+USERID+"&source=insta\">"+USERNAME+"</a>: "+CommentText+"</td>\n" +
                        "</tr>";
            }
            String popup3 = "</table>\n" + "</div>";
            completepopup = popup1+popup2+popup3;
            commentsList.add(completepopup);
        }
            out.println(
"								</div>\n" +
"								<!-- Timeline ends -->\n" +
"							</div>\n" +
"						</div>\n" +
"						<!-- Row end -->\n" +
"\n" +
"					</div>\n" +
"					<!-- Spacer Ends -->\n" +
"\n" +
"				</div>\n" +
"				<!-- Container fluid ends -->\n" +
"\n" +
"			</div>\n" +
"			<!-- Main Container ends -->\n" );
            for(int i = 0 ; i < commentsList.size(); i++){
                out.println(((String)commentsList.get(i)));
//                System.out.println((String)commentsList.get(i));
            }
            
            }
            
            
            
            out.println(
"\n" +
"			<!-- Right sidebar starts -->\n" +
"			<div class=\"right-sidebar\">\n" +
"				\n" +
"\n" +
"			</div>\n" +
"			<!-- Right sidebar ends -->\n" );
            out.println(
"\n" +
"			<!-- Footer starts -->\n" +
"			<footer>\n" +
"				Copyright Everest Admin Panel 2014.\n" +
"			</footer>\n" +
"			<!-- Footer ends -->\n" +
"			<!-- Footer ends -->\n" +
"\n" +
"		</div>\n" +
"		<!-- Dashboard Wrapper ends -->\n" +
"\n" +
"		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\n" +
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
"		<!-- Custom Index -->\n" +
"		<script src=\"js/custom.js\"></script>\n" +
"		<script type=\"text/javascript\">\n" +
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
"		</script>\n" );
            

            out.println(
"	</body>\n" +
"</html>");
    }catch(Exception ex){
        System.out.println(ex);
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
