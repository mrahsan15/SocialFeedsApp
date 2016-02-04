import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Friend;
import facebook4j.Friendlist;
import facebook4j.Paging;
import facebook4j.PictureSize;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.TaggableFriend;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jinstagram.entity.users.basicinfo.UserInfo;
import twitter4j.PagableResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class dashboard extends HttpServlet {
    int FbAcc= 0;
    int TwitterAcc= 0;
    int GitHubAcc= 0,InstagramAcc=0,GooglePlusAcc = 0,LinkedInAcc=0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        InstagramService service = null ;
        StringBuffer callbackURL = null;
        
        
        Connection fab = new DBConnectivity().ConnectDB();
//        String quer = "SELECT * from TokensData.FACEBOOK_ACCOUNT";
        Facebook facebook = null;
//        try {
//            Statement fabst = fab.createStatement();
//            ResultSet fabrs = fabst.executeQuery(quer);
//            if(fabrs.next()){
//                String token = fabrs.getString("TOKEN");
//                AccessToken tokenn = new AccessToken(token,null);
//                facebook = new FacebookFactory().getInstance(tokenn);
//                facebook.setOAuthAccessToken(tokenn);
//                
//            }else{
//                response.sendRedirect(request.getContextPath()+"/login");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        } catch (IllegalStateException ex) {
//            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
        
        
//        try {
//            File file = new File("facebook4j.properties");
//            FileOutputStream fos = new FileOutputStream(file);
//            PrintWriter pw = new PrintWriter(fos);
//            pw.write("debug=true\n" +
//                    "oauth.appId=831179316980855\n" +
//                    "oauth.appSecret=f1490f5bfa6e55d0c6518a40841bac87\n" +
//                    "oauth.permissions=user_birthday, user_religion_politics, user_relationships, user_relationship_details, user_hometown, user_location, user_likes, user_education_history, user_work_history, user_website, user_managed_groups, user_events, user_photos, user_videos, user_friends, user_about_me, user_status, user_games_activity, user_tagged_places, user_posts, read_page_mailboxes, rsvp_event, email, ads_management, ads_read, read_insights, manage_pages, publish_pages, pages_show_list, pages_manage_cta, pages_manage_leads, publish_actions, read_audience_network_insights, read_custom_friendlists, user_actions.books, user_actions.music, user_actions.video, user_actions.news, user_actions.fitness, public_profile");
//            System.out.println("File Created at: "+file.getAbsolutePath());
//            pw.flush();
//            pw.close();
//            fos.close();
//        } catch (FileNotFoundException ex) {
//            System.out.println(ex);
//        } catch (IOException ex) {
//            System.out.println(ex);
//        } finally {
//            }
//            
//            
//            
        
        
//        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
//        if(facebook == null){
//            System.out.println("Facebook Get Attribute is Null!");
//            facebook = new FacebookFactory().getInstance();
//            request.getSession().setAttribute("facebook", facebook);
//            callbackURL = request.getRequestURL();
//            int index = callbackURL.lastIndexOf("/");
//            callbackURL.replace(index, callbackURL.length(), "").append("/callback");
//            response.sendRedirect(callbackURL.toString());
//        }else{
//            String oauthCode = request.getParameter("code");
//            try {
//                facebook.getOAuthAccessToken(oauthCode);
//            } catch (FacebookException e) {
//                throw new ServletException(e);
//            }
//            try {
//                out.println("<h1>"+facebook.getName()+"</h1>");
//            } catch (FacebookException ex) {
//                Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IllegalStateException ex) {
//                Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        try{
//            ConfigurationBuilder cb = new ConfigurationBuilder().
//            setOAuthAppId("831179316980855")
//                    .setOAuthAppSecret("f1490f5bfa6e55d0c6518a40841bac87")
//                    .setOAuthPermissions("user_birthday, user_religion_politics, user_relationships, user_relationship_details, user_hometown, user_location, user_likes, user_education_history, user_work_history, user_website, user_managed_groups, user_events, user_photos, user_videos, user_friends, user_about_me, user_status, user_games_activity, user_tagged_places, user_posts, read_page_mailboxes, rsvp_event, email, ads_management, ads_read, read_insights, manage_pages, publish_pages, pages_show_list, pages_manage_cta, pages_manage_leads, publish_actions, read_audience_network_insights, read_custom_friendlists, user_actions.books, user_actions.music, user_actions.video, user_actions.news, user_actions.fitness, public_profile");
//            Configuration conf = cb.build() ;
//            FacebookFactory ff = new FacebookFactory(conf);
//            facebook = ff.getInstance();
//            facebook = new FacebookFactory().getInstance();
//            request.getSession().setAttribute("facebook", facebook);
//            callbackURL = request.getRequestURL();
//            int index = callbackURL.lastIndexOf("/");
//            callbackURL.replace(index, callbackURL.length(), "").append("/FacebookConnect");
//        }catch(IllegalStateException eex){
//            
//            
//        }
//    
        
        Twitter twitter = new TwitterFactory().getInstance();
        
        String code = "";
        String token = "";
        
        try {
            service = new InstagramAuthService()
            .apiKey("e0bbe4b568cd453e925d7962ad2b9c7c")
            .apiSecret("f11f9582bef947b4b28bf871ce36a06c")
            .callback("http://192.168.1.100:8080/SocialFeedsApp/dashboard")
            .build();
            System.out.println("Worked1!");
            
            code = request.getParameter("code");
            System.out.println(code);
            
            try{
                System.out.println("Error Code: 202!");
                Cookie[] cookies = request.getCookies();
            
            for(int i = 0; i< cookies.length; i++){
                if((cookies[i].getName()).equals("token")){
                    token = cookies[i].getValue();
                }
            }
            
            }catch(Exception ex){
                System.out.println("Error Code: 203!");
                System.out.println(ex);
            }
            
            try{
                if(token.equals("")){
                    System.out.println("Token if statement executed!");
                }
            }catch(Exception ex){
                System.out.println("Exception Caught!");
                token = "";
            }
            
            
            if(token.equals("")){
                System.out.println("Token is null!");
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
                    PrintPage(insta,out,facebook,twitter);
                }
                

            }else{
                Instagram insta = new Instagram(new Token(token,""));
                Connection con = new DBConnectivity().ConnectDB();
                try{
                    
                    Statement st = con.createStatement();
                    String Query = "SELECT * from TokensData.INSTAGRAM_ACCOUNT WHERE ACCOUNTNAME LIKE "+insta.getCurrentUserInfo().getData().getUsername();
                    ResultSet rs = st.executeQuery(Query);
                    rs.next();
                    if(rs.getString("TOKEN").equals("")){

                    }
                
                }catch(Exception ex){
                    System.out.println("Error Code: 200!");
                    String Query = "INSERT INTO TokensData.INSTAGRAM_ACCOUNT (ID, ACCOUNTNAME, TOKEN)"
                            + " VALUES (NULL, '"+insta.getCurrentUserInfo().getData().getUsername()+"', '"+token+"')";
                    Statement st  = con.createStatement();
                    st.executeUpdate(Query);
                }
                
                request.setAttribute("twitter", twitter);
                request.setAttribute("instagram", insta);
                PrintPage(insta, out,facebook,twitter);

            }

        }
        
        catch(Exception ex){
            System.out.println("Error Code: 201!");
            System.out.println(ex);
            response.sendRedirect(service.getAuthorizationUrl(null));
        }
    }
    public void PrintPage(Instagram insta,PrintWriter out,Facebook facebook,Twitter twitter){
        UserInfo info = null;
        Connection con = new DBConnectivity().ConnectDB();
        
        try {
            info = insta.getCurrentUserInfo();
            String CopyRightSyntax = "Copyright Apisylux Dashboard Panel 2016";
            String ProfilePic = info.getData().getProfilePicture();
            String FullName  = info.getData().getFullName();
            String CurrentUserID = info.getData().getId();
            String Bio = info.getData().getBio();
            int follower = info.getData().getCounts().getFollowedBy();
            int following = info.getData().getCounts().getFollows();
            int mediacount = info.getData().getCounts().getMedia();
            
            System.out.println("Instagram Initialization Done!");
            
//            String FbFullName = facebook.getName();
//            User MyFb;
//            
//            MyFb = facebook.users().getUser(facebook.getId(), new Reading().fields("email","bio","work","picture","cover"));
//            String FbBio = MyFb.getBio();
//            String FbUserName;
//            FbUserName = MyFb.getUsername();
//            String FbCover = MyFb.getCover().getSource();
//            String FbProfilePicture =facebook.getPictureURL(PictureSize.large).toString();
//            int FacebookFriendsCount = facebook.getFriends().getSummary().getTotalCount();
            
            
            String TwitterName = twitter.getScreenName();
            twitter4j.User MyTwitter =twitter.users().showUser(TwitterName);
            String TwitterProfilePicture = MyTwitter.getOriginalProfileImageURL();
            String TwitterBio = MyTwitter.getDescription();
            int TwitterTweets = MyTwitter.getStatusesCount();
            int TwitterFollowers = MyTwitter.getFollowersCount();
            int TwitterFollowings = MyTwitter.getFriendsCount();
            
            
//             try {
//            // get friends
//            long cursor = -1;
//            ArrayList listFriends = new ArrayList();
//            PagableResponseList<twitter4j.User> pagableFollowings;
//            do {
//                pagableFollowings = twitter.getFriendsList(twitter.getId(), cursor, 200);
//                
//                for (twitter4j.User user : pagableFollowings) {
//                    listFriends.add(user);
//                }
//            } while ((cursor = pagableFollowings.getNextCursor()) != 0);
//            
//            System.out.println("Followings are: "+listFriends.size());
//
//            // get followers
//            cursor = -1;
//            ArrayList listFollowers = new ArrayList();
//            PagableResponseList<twitter4j.User> pagableFollowers;
//            do {
//                pagableFollowers = twitter.getFollowersList(twitter.getId(), cursor,200);
//                for (twitter4j.User user : pagableFollowers) {
//                    listFollowers.add(user); // ArrayList<User>
//                    System.out.println(user.getScreenName());
//                }
//            } while ((cursor = pagableFollowers.getNextCursor()) != 0);
//            System.out.println("Followings are: "+listFollowers.size());
//
//        } catch (TwitterException e) {
//            System.out.println("Twitter Exception caught in Here!!!");
//        }
            
            
            
//            System.out.println("Facebook Initialization Done!");
            
            new CommonWidgets(insta,out).HeadRegion(out);
            
            
            out.println("<body>");
            out.println(
"		<!-- Header Start -->\n" +
"		<header>\n" );
            
            new CommonWidgets(insta,out).Logo(out);
            new CommonWidgets(insta,out).Search(out);
            new CommonWidgets(insta,out).RightNavBar(out);
            
            
            out.println(
"\n" +
"		</header>\n" +
        
"		<!-- Header ends -->\n");
            out.println(
"		<!-- Left sidebar starts -->\n" +
"		<aside id=\"sidebar\">\n");
            
            new CommonWidgets(insta,out).CurrentUser(out);
            
            new CommonWidgets(insta,out).Menu(out, "Dashboard");
            
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
"		</aside>\n" +
"		<!-- Left sidebar ends -->\n");
            out.println(
"		<!-- Dashboard Wrapper starts -->\n" +
"		<div class=\"dashboard-wrapper\">\n" );
            out.println(
"			<!-- Top Bar starts -->\n" +
"			<div class=\"top-bar\">\n" +
"				<div class=\"page-title\">\n" +
"					"+FullName+"\n" +
"				</div>\n");
            out.println(
"				<ul class=\"stats hidden-xs\">\n");
            out.println(
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"downloads_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
"							<h4><span id=\"\">"+follower+"</span> <i class=\"fa fa-chevron-up up\"></i></h4>\n" +
"							<h5>Followers</h5>\n" +
"						</div>\n" +
"					</li>\n");
            out.println(
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"users_online_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
"							<h4><span id=\"\">"+following+"</span> <i class=\"fa fa-chevron-down down\"></i></h4>\n" +
"							<h5>Followings</h5>\n" +
"						</div>\n" +
"					</li>\n");
            out.println(
"				</ul>\n" +
"			</div>\n" +
"			<!-- Top Bar ends -->\n");
            out.println(
"			<!-- Main Container starts -->\n" +
"			<div class=\"main-container\">\n");
            out.println(
"				<!-- Container fluid Starts -->\n" +
"				<div class=\"container-fluid\">\n" );
            out.println(
"					<!-- Current Stats Start -->\n" +
"					<div class=\"current-stats\">\n" +
"						<div class=\"row\">\n" );

            out.println(
"							<div class=\"col-lg-2 col-md-4 col-sm-4 col-xs-6\">\n" +
"								<div class=\"info-bg center-align-text\">\n" +
"									<div class=\"spacer-xs\">\n" +
"										<i class=\"fa fa-facebook fa-2x\"></i>\n" +
"										<small class=\"text-white\">Facebook</small>\n" +
"										<h3 class=\"no-margin no-padding\">"+FbAcc+"</h3>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n");
            out.println(
"							<div class=\"col-lg-2 col-md-4 col-sm-4 col-xs-6\">\n" +
"								<div class=\"twitter-bg center-align-text\">\n" +
"									<div class=\"spacer-xs\">\n" +
"										<i class=\"fa fa-twitter fa-2x\"></i>\n" +
"										<small class=\"text-white\">Twitter</small>\n" +
"										<h3 class=\"no-margin no-padding text-white\">"+TwitterAcc+"</h3>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n");
            out.println(
"							<div class=\"col-lg-2 col-md-4 col-sm-4 col-xs-6\">\n" +
"								<div class=\"success-bg center-align-text\">\n" +
"									<div class=\"spacer-xs\">\n" +
"										<i class=\"fa fa-foursquare fa-2x\"></i>\n" +
"										<small class=\"text-white\">Instagram</small>\n" +
"										<h3 class=\"no-margin no-padding text-white\">"+InstagramAcc+"</h3>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n");
        
            out.println(
"							<div class=\"col-lg-2 col-md-4 col-sm-4 col-xs-6\">\n" +
"								<div class=\"danger-bg center-align-text\">\n" +
"									<div class=\"spacer-xs\">\n" +
"										<i class=\"fa fa-github fa-2x\"></i>\n" +
"										<small class=\"text-white\">Github</small>\n" +
"										<h3 class=\"no-margin no-padding\">"+GitHubAcc+"</h3>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n");
            out.println(
"							<div class=\"col-lg-2 col-md-4 col-sm-4 col-xs-6\">\n" +
"								<div class=\"brown-bg center-align-text\">\n" +
"									<div class=\"spacer-xs\">\n" +
"										<i class=\"fa fa-google-plus fa-2x\"></i>\n" +
"										<small class=\"text-white\">Google Plus</small>\n" +
"										<h3 class=\"no-margin no-padding\">"+GooglePlusAcc+"</h3>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n");
            out.println(
"							<div class=\"col-lg-2 col-md-4 col-sm-4 col-xs-6\">\n" +
"								<div class=\"linkedin-bg center-align-text\">\n" +
"									<div class=\"spacer-xs\">\n" +
"										<i class=\"fa fa-linkedin fa-2x\"></i>\n" +
"										<small class=\"text-white\">LinkedIn</small>\n" +
"										<h3 class=\"no-margin no-padding\">"+LinkedInAcc+"</h3>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n");
            out.println(
"						</div>\n" +
"					</div>\n" +
"					<!-- Current Stats End -->\n" );
            out.println(
"					<!-- Spacer starts -->\n" +
"					<div class=\"spacer\">\n" );
            

            out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n");
            out.println(
"								<!-- Widget starts -->\n");
            out.println(
"								<div class=\"blog\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Twitter Panel</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\">\n" +
"										<div class=\"row\">\n");
            out.println(
"											<div class=\"col-lg-10 col-md-10 col-sm-12 col-xs-12\">\n" +
"												<div class=\"chart-height-lg\">"
        + "<img src=\""
        +TwitterProfilePicture
        +"\" width=\"150\" height=\"150\" /><h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+TwitterName+"</h3>"
        +"<br><br><p style=\"font-size:15px; margin-left: 170px;\">"+TwitterBio+"</p>"
        + "</div>\n" +
"											</div>\n");
//            out.println(
//"											<div class=\"visitors-total\">\n" +
//"												<h3 style=\"margin-right: 125px;\">"+TwitterFollowers+"</h3>\n" +
//"												<p style=\"margin-right: 125px;\">Twitter Followers</p>\n" +
//"											</div>\n");
            out.println(
"											<div class=\"visitors-total\">\n" +
"												<h3>"+TwitterTweets+"</h3>\n" +
"												<p>Total Tweets</p>\n" +
"											</div>\n");
            out.println(
"											<div class=\"visit-stats\">\n" +
"												<ul class=\"clearfix\">\n" );
            out.println(
"												</ul>\n" +
"											</div>\n" +
"										</div>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
                        out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n" );

            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
            out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n" );
            out.println(
"								<!-- Widget starts -->\n" +
"								<div class=\"blog blog-info\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Twitter Followers "+ TwitterFollowers+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n");
            
            
            Statement twitterstatement = con.createStatement();
            
            String TwitterQuery = "SELECT * from Ahsan_Data.Twitter_Followers";
            
            ResultSet twitterfollowers = twitterstatement.executeQuery(TwitterQuery);
            
            while(twitterfollowers.next()){
                String TwitterProfilePic = twitterfollowers.getString("ProfilePicture");
                String TwitterProfileBan = twitterfollowers.getString("ProfileBanner");
                String TwitterScreenName = twitterfollowers.getString("ScreenName");
                String TwitterUserName = twitterfollowers.getString("Name");
                String TwitterUserId = twitterfollowers.getString("UserID");
                String Following = "";
                if(twitterfollowers.getInt("FollowBack") == 1){
                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
                }else{
                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
                }
                
                out.println(
"<li class=\"client clearfix\">\n" +
"   <img src=\""+TwitterProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
"   <div class=\"client-details\">\n" +
"       <p>\n" +
"           <span class=\"name\"><a href=\"Profile?username="+TwitterUserId+"&source=twitter\">"+TwitterUserName+"</a></span>\n" +
"           <span class=\"email\"><a href=\"Profile?username="+TwitterUserId+"&source=twitter\">@"+TwitterScreenName+"</a></span>\n" +
"       </p>\n" +
"       <ul class=\"icons-nav\">\n" +
        Following +
//"	<li>\n" +
//"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Delete\">\n" +
//"	<i class=\"fa fa-trash-o\"></i>\n" +
//"	</a>\n" +
//"	</li>\n" +
//"	<li>\n" +
//"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Email\">\n" +
//"	<i class=\"fa fa-envelope-o\"></i>\n" +
//"	</a>\n" +
//"	</li>\n" +
//"	<li>\n" +
//"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Contact\">\n" +
//"<i class=\"fa fa-phone\"></i>\n" +
//"</a>\n" +
//"</li>\n" +
"</ul>\n" +
"</div>\n" +
"</li>\n");
            }
            
//            }
//            for(int i = 0; i < friendslist.size();i++){
//                
//                friendslist.get(i).getName();
//                String usernamee = friendslist.get(i).;
//                String profile = friendslist.get(i).getPicture().getURL().toString();
//                String fullname = friendslist.get(i).getName();
//                String userId = friendslist.get(i).getName();
//                
//                
            
            
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            out.println(
"							</div>\n");
            out.println(
"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n");
            out.println(
"								<!-- Widget starts -->\n" +
"								<div class=\"blog blog-danger\">\n");
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Twitter Followings "+TwitterFollowings+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n" );

            
            twitterstatement = con.createStatement();
            
            TwitterQuery = "SELECT * from Ahsan_Data.Twitter_Followings";
            
            ResultSet twitterfollowings = twitterstatement.executeQuery(TwitterQuery);
            
            while(twitterfollowings.next()){
                String TwitterProfilePic = twitterfollowings.getString("ProfilePicture");
                String TwitterProfileBan = twitterfollowings.getString("ProfileBanner");
                String TwitterScreenName = twitterfollowings.getString("ScreenName");
                String TwitterUserName = twitterfollowings.getString("Name");
                String TwitterUserId = twitterfollowings.getString("UserID");
                String Following = "";
                if(twitterfollowings.getInt("FollowBack") == 1){
                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
                }else{
                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Not Following</button>";
                }
                
                out.println(
"<li class=\"client clearfix\">\n" +
"   <img src=\""+TwitterProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
"   <div class=\"client-details\">\n" +
"       <p>\n" +
"           <span class=\"name\"><a href=\"Profile?username="+TwitterUserId+"&source=twitter\">"+TwitterUserName+"</a></span>\n" +
"           <span class=\"email\"><a href=\"Profile?username="+TwitterUserId+"&source=twitter\">@"+TwitterScreenName+"</a></span>\n" +
"       </p>\n" +
"       <ul class=\"icons-nav\">\n" +
        Following +
        
//"	<li>\n" +
//"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Delete\">\n" +
//"	<i class=\"fa fa-trash-o\"></i>\n" +
//"	</a>\n" +
//"	</li>\n" +
//"	<li>\n" +
//"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Email\">\n" +
//"	<i class=\"fa fa-envelope-o\"></i>\n" +
//"	</a>\n" +
//"	</li>\n" +
//"	<li>\n" +
//"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Contact\">\n" +
//"<i class=\"fa fa-phone\"></i>\n" +
//"</a>\n" +
//"</li>\n" +
"</ul>\n" +
"</div>\n" +
"</li>\n");
            }
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
            
//            
//            out.println(
//"						<!-- Row Start -->\n" +
//"						<div class=\"row\">\n" +
//"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n");
//            out.println(
//"								<!-- Widget starts -->\n");
//            out.println(
//"								<div class=\"blog\">\n" );
//            out.println(
//"									<div class=\"blog-header\">\n" +
//"										<h5 class=\"blog-title\">Facebook Panel</h5>\n" +
//"									</div>\n");
//            out.println(
//"									<div class=\"blog-body\">\n" +
//"										<div class=\"row\">\n");
//            out.println(
//"											<div class=\"col-lg-10 col-md-10 col-sm-12 col-xs-12\">\n" +
//"												<div class=\"chart-height-lg\">"
//        + "<img src=\""
//        +FbProfilePicture
//        +"\" width=\"150\" height=\"150\" /><h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+FbFullName+"</h3>"
//        +"<br><br><p style=\"font-size:15px; margin-left: 170px;\">"+FbBio+"</p>"
//        + "</div>\n" +
//"											</div>\n");
//            out.println(
//"											<div class=\"visitors-total\">\n" +
//"												<h3>"+FacebookFriendsCount+"</h3>\n" +
//"												<p>Facebook Friends</p>\n" +
//"											</div>\n");
//            out.println(
//"											<div class=\"visit-stats\">\n" +
//"												<ul class=\"clearfix\">\n" );
//            out.println(
//"												</ul>\n" +
//"											</div>\n" +
//"										</div>\n" +
//"									</div>\n" +
//"								</div>\n" +
//"								<!-- Widget ends -->\n");
//            out.println(
//"							</div>\n" +
//"						</div>\n" +
//"						<!-- Row End -->\n");
//                        out.println(
//"						<!-- Row Start -->\n" +
//"						<div class=\"row\">\n" +
//"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n" );
//
//            out.println(
//"							</div>\n" +
//"						</div>\n" +
//"						<!-- Row End -->\n");
//            out.println(
//"						<!-- Row Start -->\n" +
//"						<div class=\"row\">\n" +
//"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n" );
//            out.println(
//"								<!-- Widget starts -->\n" +
//"								<div class=\"blog blog-info\">\n" );
//            out.println(
//"									<div class=\"blog-header\">\n" +
//"										<h5 class=\"blog-title\">Facebook Friends "+ FacebookFriendsCount+"</h5>\n" +
//"									</div>\n");
//            out.println(
//"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
//"										<ul class=\"clients-list\">\n");
//            
//            
////            Statement st = con.createStatement();
////            
////            String Query = "SELECT * from Ahsan_Data.Ahsan_Followers";
////            
////            ResultSet followers = st.executeQuery(Query);
//            
//            ResponseList<TaggableFriend> friendslist =facebook.getTaggableFriends(new Reading().limit(FacebookFriendsCount));
////            ResponseList<Friend> friendslist =facebook.getBelongsFriend(facebook.getId(),new Reading().fields("id","picture").limit(FacebookFriendsCount-1));
////            System.out.println(friendslist);
//            for(int i = 0; i < friendslist.size();i++){
//                
//                friendslist.get(i).getName();
////                String usernamee = friendslist.get(i).;
//                String profile = friendslist.get(i).getPicture().getURL().toString();
//                String fullname = friendslist.get(i).getName();
//                String userId = friendslist.get(i).getName();
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"   <img src=\""+profile+"\" class=\"avatar\" alt=\"Client\">\n" +
//"   <div class=\"client-details\">\n" +
//"       <p>\n" +
//"           <span class=\"name\"><a href=\"Profile?username="+userId+"\">"+fullname+"</a></span>\n" +
////"           <span class=\"email\"><a href=\"Profile?username="+userId+"\">"+usernamee+"</a></span>\n" +
//"       </p>\n" +
//"       <ul class=\"icons-nav\">\n" +
//"	<li>\n" +
//"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Delete\">\n" +
//"	<i class=\"fa fa-trash-o\"></i>\n" +
//"	</a>\n" +
//"	</li>\n" +
//"	<li>\n" +
//"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Email\">\n" +
//"	<i class=\"fa fa-envelope-o\"></i>\n" +
//"	</a>\n" +
//"	</li>\n" +
//"	<li>\n" +
//"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Contact\">\n" +
//"<i class=\"fa fa-phone\"></i>\n" +
//"</a>\n" +
//"</li>\n" +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
////            
//            
//            out.println(
//"										</ul>\n" +
//"									</div>\n" +
//"								</div>\n" +
//"								<!-- Widget ends -->\n");
//            out.println(
//"							</div>\n");
//            out.println(
//"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n");
////            out.println(
////"								<!-- Widget starts -->\n" +
////"								<div class=\"blog blog-danger\">\n");
////            out.println(
////"									<div class=\"blog-header\">\n" +
////"										<h5 class=\"blog-title\">Followings "+following+"</h5>\n" +
////"									</div>\n");
////            out.println(
////"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
////"										<ul class=\"clients-list\">\n" );
////
//            
////            Query = "SELECT * from Ahsan_Data.Ahsan_Followings";
////            
////            ResultSet followings = st.executeQuery(Query);
////            while(followings.next()){
////                String usernamee = followings.getString("USERNAME");;
////                String profile = followings.getString("PICTUREURL");
////                String fullname = followings.getString("FULLNAME");
////                String userId = followings.getString("USERID");
////            out.println(
////"<li class=\"client clearfix\">\n" +
////"   <img src=\""+profile+"\" class=\"avatar\" alt=\"Client\">\n" +
////"   <div class=\"client-details\">\n" +
////"       <p>\n" +
////"           <span class=\"name\"><a href=\"Profile?username="+userId+"\">"+fullname+"</a></span>\n" +
////"           <span class=\"email\"><a href=\"Profile?username="+userId+"\">"+usernamee+"</a></span>\n" +
////"       </p>\n" +
////"       <ul class=\"icons-nav\">\n" +
////"	<li>\n" +
////"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Delete\">\n" +
////"	<i class=\"fa fa-trash-o\"></i>\n" +
////"	</a>\n" +
////"	</li>\n" +
////"	<li>\n" +
////"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Email\">\n" +
////"	<i class=\"fa fa-envelope-o\"></i>\n" +
////"	</a>\n" +
////"	</li>\n" +
////"	<li>\n" +
////"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Contact\">\n" +
////"<i class=\"fa fa-phone\"></i>\n" +
////"</a>\n" +
////"</li>\n" +
////"</ul>\n" +
////"</div>\n" +
////"</li>\n");
////                }
////            out.println(
////"										</ul>\n" +
////"									</div>\n" +
////"								</div>\n" +
////"								<!-- Widget ends -->\n");
//            
//            out.println(
//"							</div>\n" +
//"						</div>\n" +
//"						<!-- Row End -->\n");

            

            out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n");
            out.println(
"								<!-- Widget starts -->\n");
            out.println(
"								<div class=\"blog\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Instagram Panel</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\">\n" +
"										<div class=\"row\">\n");
            out.println(
"											<div class=\"col-lg-10 col-md-10 col-sm-12 col-xs-12\">\n" +
"												<div class=\"chart-height-lg\">"
        + "<img src=\""
        +ProfilePic
        +"\" /><h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+FullName+"</h3>"
        +"<br><br><p style=\"font-size:15px; margin-left: 170px;\">"+Bio+"</p>"
        + "</div>\n" +
"											</div>\n");
            out.println(
"											<div class=\"visitors-total\">\n" +
"												<h3>"+mediacount+"</h3>\n" +
"												<p>Media Uploaded</p>\n" +
"											</div>\n");
            out.println(
"											<div class=\"visit-stats\">\n" +
"												<ul class=\"clearfix\">\n" );
            out.println(
"												</ul>\n" +
"											</div>\n" +
"										</div>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
                        
            out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n" );

            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
            out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n" );
            out.println(
"								<!-- Widget starts -->\n" +
"								<div class=\"blog blog-info\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Followers "+ follower+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n");
            

            Statement st = con.createStatement();
            
            String Query = "SELECT * from Ahsan_Data.Ahsan_Followers";
            
            ResultSet followers = st.executeQuery(Query);
            while(followers.next()){
                String usernamee = followers.getString("USERNAME");
                String profile = followers.getString("PICTUREURL");
                String fullname = followers.getString("FULLNAME");
                String userId = followers.getString("USERID");
                
                out.println(
"<li class=\"client clearfix\">\n" +
"   <img src=\""+profile+"\" class=\"avatar\" alt=\"Client\">\n" +
"   <div class=\"client-details\">\n" +
"       <p>\n" +
"           <span class=\"name\"><a href=\"Profile?username="+userId+"&source=insta\">"+fullname+"</a></span>\n" +
"           <span class=\"email\"><a href=\"Profile?username="+userId+"&source=insta\">"+usernamee+"</a></span>\n" +
"       </p>\n" +
"       <ul class=\"icons-nav\">\n" +
"	<li>\n" +
"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Delete\">\n" +
"	<i class=\"fa fa-trash-o\"></i>\n" +
"	</a>\n" +
"	</li>\n" +
"	<li>\n" +
"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Email\">\n" +
"	<i class=\"fa fa-envelope-o\"></i>\n" +
"	</a>\n" +
"	</li>\n" +
"	<li>\n" +
"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Contact\">\n" +
"<i class=\"fa fa-phone\"></i>\n" +
"</a>\n" +
"</li>\n" +
"</ul>\n" +
"</div>\n" +
"</li>\n");
            }
            
            
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            out.println(
"							</div>\n");
            out.println(
"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n" +
"								<!-- Widget starts -->\n" +
"								<div class=\"blog blog-danger\">\n");
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Followings "+following+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n" );

            
            Query = "SELECT * from Ahsan_Data.Ahsan_Followings";
            
            ResultSet followings = st.executeQuery(Query);
            while(followings.next()){
                String usernamee = followings.getString("USERNAME");;
                String profile = followings.getString("PICTUREURL");
                String fullname = followings.getString("FULLNAME");
                String userId = followings.getString("USERID");
            out.println(
"<li class=\"client clearfix\">\n" +
"   <img src=\""+profile+"\" class=\"avatar\" alt=\"Client\">\n" +
"   <div class=\"client-details\">\n" +
"       <p>\n" +
"           <span class=\"name\"><a href=\"Profile?username="+userId+"&source=insta\">"+fullname+"</a></span>\n" +
"           <span class=\"email\"><a href=\"Profile?username="+userId+"&source=insta\">"+usernamee+"</a></span>\n" +
"       </p>\n" +
"       <ul class=\"icons-nav\">\n" +
"	<li>\n" +
"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Delete\">\n" +
"	<i class=\"fa fa-trash-o\"></i>\n" +
"	</a>\n" +
"	</li>\n" +
"	<li>\n" +
"	<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Email\">\n" +
"	<i class=\"fa fa-envelope-o\"></i>\n" +
"	</a>\n" +
"	</li>\n" +
"	<li>\n" +
"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Contact\">\n" +
"<i class=\"fa fa-phone\"></i>\n" +
"</a>\n" +
"</li>\n" +
"</ul>\n" +
"</div>\n" +
"</li>\n");
                }
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");

            out.println(
"					</div>\n" +
"					<!-- Spacer ends -->\n" );
            out.println(
"				</div>\n" +
"				<!-- Container fluid ends -->\n" +
"			</div>\n" +
"			<!-- Main Container ends -->\n" );
            out.println(
"			<!-- Right sidebar starts -->\n" +
"			<div class=\"right-sidebar\">\n");
            out.println(
"			</div>\n" +
"			<!-- Right sidebar ends -->\n");
            out.println(
"			<!-- Footer starts -->\n" +
"			<footer>\n" +
CopyRightSyntax +
"			</footer>\n" +
"			<!-- Footer ends -->\n" +
"			<!-- Footer ends -->\n");
            out.println(
"		</div>\n" +
"		<!-- Dashboard Wrapper ends -->\n" );
            out.println(
"		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\n" +
"		<script src=\"js/jquery.js\"></script>\n" +
"\n" +
"		<!-- jQuery UI JS -->\n" +
"		<script src=\"js/jquery-ui-v1.10.3.js\"></script>\n" +
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
"		<!-- Flot Charts -->\n" +
"		<script src=\"js/flot/jquery.flot.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.tooltip.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.resize.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.stack.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.orderBar.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.pie.min.js\"></script>\n" +
"\n" +
"		<!-- JVector Map -->\n" +
"		<script src=\"js/jvectormap/jquery-jvectormap-1.2.2.min.js\"></script>\n" +
"		<script src=\"js/jvectormap/jquery-jvectormap-usa.js\"></script>\n" +
"\n" +
"		<!-- Custom Index -->\n" +
"		<script src=\"js/custom.js\"></script>\n" +
"		<script src=\"js/custom-index.js\"></script>\n" +
"	");
            out.println("</body>");
            out.println("</html>");
     } catch (Exception ex) {
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
