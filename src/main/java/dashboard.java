import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.PictureSize;
import facebook4j.Reading;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class dashboard extends HttpServlet {
    int FbAcc= 0;
    int TwitterAcc= 0;
    int GitHubAcc= 0,InstagramAcc=0,GooglePlusAcc = 0,LinkedInAcc=0;
    Facebook facebook = null;
    Twitter twitter = null;
    Instagram instagram = null;
    ArrayList AccountsLinked  = new ArrayList();
    ApiObjects apiobjects = null;
    HttpServletRequest request;
    HttpServletResponse response;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection con = new DBConnectivity().ConnectDB();
        int LoggedUserID= 0;
        InstagramService service = null ;
        String UserLogged = "";
//        if(request.getSession().isNew()){
//            response.sendRedirect("index.html");
//        }
        try{
            Cookie[] cookies = request.getCookies();
            for(int i = 0; i< cookies.length; i++){
                if((cookies[i].getName()).equals("LoggedIn")){
                    UserLogged= cookies[i].getValue();
                }else{
//                    response.sendRedirect("login.jsp");
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
            System.out.println("Cookie Not Found here! Visit Some Bakery Please!");
            
        }
//        if(LoggedUserID == 0){
//            response.sendRedirect("login.jsp");
//        }
        String LoggedInDetail = "SELECT * from Ahsan_Data.LoginAccounts WHERE UserName = '"+UserLogged+"'";
        Statement statement = null;
        ResultSet LoggedInUserID;
        try{
            statement = con.createStatement();
        LoggedInUserID = statement.executeQuery(LoggedInDetail);
        if(LoggedInUserID.next()){
           LoggedUserID = LoggedInUserID.getInt("ID");
        }
        }catch(Exception ex){
            System.out.println("DB Connectivity Issue. ID not Getting!");
        }    
        apiobjects = new ApiObjects(con, LoggedUserID);
        FbAcc = apiobjects.getFacebookObjects().size();
        TwitterAcc = apiobjects.getTwitterObjects().size();
        InstagramAcc = apiobjects.getInstagramObjects().size();
        LinkedInAcc = apiobjects.getLinkedInObjects().size();
        GooglePlusAcc = apiobjects.getGooglePlusObjects().size();
        
        try {
            PrintPage(out);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    
    public void PrintPage(PrintWriter out){
        CommonWidgets widgets = new CommonWidgets(out) ;
        
        UserInfo info = null;
        Connection con = new DBConnectivity().ConnectDB();
        Statement getAccountList =null;
        String AccountUserInfo = "SELECT * from Ahsan_Data.LoginAccounts";
        String TwitterFollowersQuery = "SELECT * from Ahsan_Data.Twitter_Followers";
        String TwitterFollowingsQuery = "SELECT * from Ahsan_Data.Twitter_Followings";
        String InstaFollowersQuery = "SELECT * from Ahsan_Data.Insta_Followers";
        String InstaFollowingsQuery = "SELECT * from Ahsan_Data.Insta_Followings";
        ResultSet AccountList = null;
        try {
            String FullName = "";
            String ProfilePicture = "";
            getAccountList= con.createStatement();
            ResultSet UserInfo = getAccountList.executeQuery(AccountUserInfo);
            if(UserInfo.next()){
                FullName  = UserInfo.getString("FirstName")+" "+UserInfo.getString("LastName");
                ProfilePicture = UserInfo.getString("ProfilePic");
            }
            String CopyRightSyntax = "Copyright Apisylux Dashboard Panel 2016";
            
            widgets.HeadRegion(out);
            out.println("<body>");
            out.println(
"		<!-- Header Start -->\n" +
"		<header>\n" );
            widgets.Logo(out);
            widgets.Search(out);
            widgets.RightNavBar(out);
            out.println(
                    "</header>\n" +
"		<!-- Header ends -->\n");
            out.println(
"		<!-- Left sidebar starts -->\n" +
"		<aside id=\"sidebar\">\n");
            widgets.CurrentUser(out);
            widgets.Menu(out, "Dashboard");
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
"					"+FullName+
"				</div>\n");
            out.println(
"				<ul class=\"stats hidden-xs\">\n");
            out.println(
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"downloads_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
//"							<h4><span id=\"\">"+follower+"</span> <i class=\"fa fa-chevron-up up\"></i></h4>\n" +
"							<h5>Followers</h5>\n" +
"						</div>\n" +
"					</li>\n");
            out.println(
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"users_online_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
//"							<h4><span id=\"\">"+following+"</span> <i class=\"fa fa-chevron-down down\"></i></h4>\n" +
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
            ArrayList Accounts = apiobjects.getFacebookObjects();
            for(int i = 0; i < FbAcc; i++){
                TokenPackets tokenpacket = (TokenPackets) Accounts.get(i);
                String token = tokenpacket.Token;
                Facebook facebook = new FacebookFactory().getInstance(new facebook4j.auth.AccessToken(token, null));
                
                Reading reading = new Reading().fields("id","bio","name");
                
                facebook4j.User user= facebook.users().getMe(reading);
                String FBProfilePic= "";
                try{
                    FBProfilePic = facebook.getPictureURL(PictureSize.large).toString();
                }catch(Exception ex){
                    
                }
                String ID = user.getId();
                String FBName = user.getName();
                String Bio = user.getBio();
                
                
                out.println(
"						<!-- Row Start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">\n");
            out.println(
"								<!-- Widget starts -->\n");
            out.println(
"								<div class=\"blog\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Facebook Panel</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\">\n" +
"										<div class=\"row\">\n");
            out.println(
"											<div class=\"col-lg-10 col-md-10 col-sm-12 col-xs-12\">\n" +
"												<div class=\"chart-height-lg\" style=\"height:320px;\">"
        + "<img src=\""
        +FBProfilePic
        +"\" width=\"150\" height=\"150\" /><h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+FBName+"</h3>"
        +"<br><br><p style=\"font-size:15px; margin-left: 170px;\">"+Bio+"</p>"
        + "</div>\n" +
"											</div>\n");
//            out.println(
//"											<div class=\"visitors-total\">\n" +
//"												<h3 style=\"margin-right: 125px;\">"+TwitterFollowers+"</h3>\n" +
//"												<p style=\"margin-right: 125px;\">Twitter Followers</p>\n" +
//"											</div>\n");
            out.println(
"											<div class=\"visitors-total\">\n" +
"												<h3>"+""+"</h3>\n" +
//"												<p>Total Tweets</p>\n" +
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
"								<!-- Widget ends -->\n"
        + "</div>");
                        out.println(
"								<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">"
        + "<!-- Widget starts -->\n" +
"								<div class=\"blog blog-info\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Facebook Friends: "+ facebook.friends().getBelongsFriend(ID).getSummary().getTotalCount()+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n");
            
//            AccountList = getAccountList.executeQuery(TwitterFollowersQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            }catch(Exception ex){
//                System.out.println("Twitter Followers Query Issue!");
//            }
            
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            out.println(
"							</div>\n");
            out.println(
"							\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
            }
            Accounts = apiobjects.getTwitterObjects();
            for(int i = 0; i < TwitterAcc; i++){
                try{
                    
                TokenPackets tokenpackets = (TokenPackets) Accounts.get(i);
                String token = tokenpackets.Token;
                String tokensecret = tokenpackets.TokenSecret;
                Twitter twitter = new TwitterFactory().getInstance(new twitter4j.auth.AccessToken(token,tokensecret));
                
                String TwitterScreenName = twitter.getScreenName();
                twitter4j.User user  = twitter.users().showUser(TwitterScreenName);
                String TwitterFullName = user.getName();
                String TwitterProfilePicture = user.getOriginalProfileImageURL();
                String TwitterBio = user.getDescription();
                int mediacount = user.getStatusesCount();
                int TwitterFollowers = user.getFollowersCount();
                int TwitterFollowings = user.getFriendsCount();
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
        + TwitterProfilePicture
        +"\" width=\"150\" height=\"150\" />"
        + "<h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+TwitterFullName+"</h3>"
        + "<p style=\"text-color: black; margin-left: 170px;\">("+TwitterScreenName+")</p>"
        +"<br><br><p style=\"font-size:15px; margin-left: 170px;\">"+TwitterBio+"</p>"
        + "</div>\n" +
"											</div>\n");
            out.println(
"											<div class=\"visitors-total\">\n" +
"												<h3>"+mediacount+"</h3>\n" +
"												<p>Tweets</p>\n" +
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
"							\n" );
            out.println(
"								<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">"
        + "<!-- Widget starts -->\n" +
"								<div class=\"blog blog-info\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Twitter Followers "+ TwitterFollowers+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n");
            
//            AccountList = getAccountList.executeQuery(TwitterFollowersQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            }catch(Exception ex){
//                System.out.println("Twitter Followers Query Issue!");
//            }
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

            
//            AccountList = getAccountList.executeQuery(TwitterFollowingsQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            
//            }catch(Exception ex){
//                System.out.println("Twitter Followings Query Error!");
//            }
//            
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");


                }catch(Exception ex){
                    
                }
                
            }
            Accounts = apiobjects.getInstagramObjects();
            for(int i = 0; i < InstagramAcc; i++){
                TokenPackets tokenpackets = (TokenPackets) Accounts.get(i);
                String token = tokenpackets.Token;
                Instagram instagram = null;
                UserInfoData user = null;
                try{
                    instagram = new Instagram(new Token(token, ""));
                    user = instagram.getCurrentUserInfo().getData();
                }catch(Exception ex){
                    response.sendRedirect("InstaConnect");
                }
                String InstagramName = user.getFullName();
                String InstagramUsername = user.getUsername();
                String InstagramBio = user.getBio();
                String InstagramProfilePicture = user.getProfilePicture();
                int mediacount = user.getCounts().getMedia();
                int InstagramFollowers= user.getCounts().getFollowedBy();
                int InstagramFollowings = user.getCounts().getFollows();
                
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
        + InstagramProfilePicture
        +"\" width=\"150\" height=\"150\" />"
        + "<h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+InstagramName+"</h3>"
        + "<p style=\"text-color: black; margin-left: 170px;\">"+ "("+InstagramUsername+")"+"</p>"
        +"<br><br><p style=\"font-size:15px; margin-left: 170px;\">"+InstagramBio+"</p>"
        + "</div>\n" +
"											</div>\n");
            out.println(
"											<div class=\"visitors-total\">\n" +
"												<h3>"+mediacount+"</h3>\n" +
"												<p>Posts</p>\n" +
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
"							\n" );
            out.println(
"								<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">"
        + "<!-- Widget starts -->\n" +
"								<div class=\"blog blog-info\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Instagram Followers "+ InstagramFollowers+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n");
            
//            AccountList = getAccountList.executeQuery(TwitterFollowersQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            }catch(Exception ex){
//                System.out.println("Twitter Followers Query Issue!");
//            }
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
"										<h5 class=\"blog-title\">Instagram Followings "+InstagramFollowings+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n" );

            
//            AccountList = getAccountList.executeQuery(TwitterFollowingsQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            
//            }catch(Exception ex){
//                System.out.println("Twitter Followings Query Error!");
//            }
//            
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
                
            }
            
            Accounts = apiobjects.getGooglePlusObjects();
            for(int i = 0 ; i < GooglePlusAcc; i++){
                TokenPackets tokenpackets = (TokenPackets) Accounts.get(i);
                String token = tokenpackets.Token;
                Credential credential = new GoogleObjectCall().getFlow().loadCredential(token);
                Plus plus = new GoogleObjectCall().getPlus(credential);
                Person plusinfo = plus.people().get("me").execute();
                String PlusProfilePicture = plusinfo.getImage().getUrl().replace("sz=50", "sz=150");
                String PlusFullName = plusinfo.getDisplayName();
                String PlusBio = plusinfo.getTagline();
                
                int PlusFollowers = plusinfo.getCircledByCount();
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
"										<h5 class=\"blog-title\">GooglePlus Panel</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\">\n" +
"										<div class=\"row\">\n");
            out.println(
"											<div class=\"col-lg-10 col-md-10 col-sm-12 col-xs-12\">\n" +
"												<div class=\"chart-height-lg\">"
        + "<img src=\""
        + PlusProfilePicture
        +"\" width=\"150\" height=\"150\" />"
        + "<h3 style=\"text-color: black; margin-top: -130px; margin-left: 170px;\">"+PlusFullName+"</h3>"
        +"<br><div><p style=\"font-size:15px; margin-left: 170px;\">"+PlusBio+"</p></div>"
        + "</div>\n" +
"											</div>\n");
            out.println(
"											<div class=\"visitors-total\">\n" +
"												<h3>"+""+"</h3>\n" +
"												<p>Posts</p>\n" +
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
"							\n" );
            out.println(
"								<div class=\"col-lg-6 col-md-6 col-sm-12 col-xs-12\">"
        + "<!-- Widget starts -->\n" +
"								<div class=\"blog blog-info\">\n" );
            out.println(
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Plus Followers: "+ PlusFollowers+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n");
            
//            AccountList = getAccountList.executeQuery(TwitterFollowersQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            }catch(Exception ex){
//                System.out.println("Twitter Followers Query Issue!");
//            }
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
//"										<h5 class=\"blog-title\">Instagram Followings "+InstagramFollowings+"</h5>\n" +
"									</div>\n");
            out.println(
"									<div class=\"blog-body\" style=\"overflow:scroll; height:350px;\">\n" +
"										<ul class=\"clients-list\">\n" );

            
//            AccountList = getAccountList.executeQuery(TwitterFollowingsQuery);
//            try{
//                while(AccountList.next()){
//                String ProfilePic = AccountList.getString("ProfilePicture");
//                String ProfileBan = AccountList.getString("ProfileBanner");
//                String ScreenName = AccountList.getString("ScreenName");
//                String UserName = AccountList.getString("Name");
//                String UserId = AccountList.getString("UserID");
//                String Following = "";
//                if(AccountList.getInt("FollowBack") == 1){
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Following</button>";
//                }else{
//                    Following = "<button type=\"button\" class=\"btn btn-info btn-rounded\">Follow</button>";
//                }
//                
//                out.println(
//"<li class=\"client clearfix\">\n" +
//"<img src=\""+ProfilePic+"\" class=\"avatar\" alt=\"Client\">\n" +
//"<div class=\"client-details\">\n" +
//"<p>\n" +
//"<span class=\"name\"><a href=\"Profile?username="+UserId+"&source=twitter\">"+UserName+"</a></span>\n" +
//"<span class=\"email\"><a href=\"Profile?username="+UserId+"&source=twitter\">@"+ScreenName+"</a></span>\n" +
//"</p>\n" +
//"<ul class=\"icons-nav\">\n" +
//        Following +
//"</ul>\n" +
//"</div>\n" +
//"</li>\n");
//            }
//            
//            }catch(Exception ex){
//                System.out.println("Twitter Followings Query Error!");
//            }
//            
            out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"								<!-- Widget ends -->\n");
            
            out.println(
"							</div>\n" +
"						</div>\n" +
"						<!-- Row End -->\n");
                
                
            }
            
            
            
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
            //Add Account Button
            out.println("<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n" +
"								<div class=\"panel\">\n" +
"									<div class=\"panel-heading\">\n" +
"										<h4 class=\"panel-title\">Add Accounts</h4>\n" +
"									</div>\n" +
"									<div class=\"panel-body\">\n" +
"										<div class=\"demo-btn-group center-align-text\">\n" +
"											<button type=\"button\" id=\"loading-btn\" data-loading-text=\"Loading...\" class=\"btn btn-info\" style=\"background-color:#5B90BF;\" onclick=\"window.location.href='login'\">Add Facebook Account</button>\n" +
"											<button type=\"button\" id=\"loading-btn\" data-loading-text=\"Loading...\" class=\"btn btn-info\" style=\"background-color:#00ACEE;\" onclick=\"window.location.href='twitterlogin'\">Add Twitter Account</button>\n" +
"											<button type=\"button\" id=\"loading-btn\" data-loading-text=\"Loading...\" class=\"btn btn-info\" style=\"background-color:#76BBAD;\" onclick=\"window.location.href='InstaConnect';\">Add Instagram Account</button>\n" +
"											<button type=\"button\" id=\"loading-btn\" data-loading-text=\"Loading...\" class=\"btn btn-info\" style=\"background-color:#1A85BD;\" onclick=\"window.location.href='';\">Add LinkedIn Account</button>\n" +
"											<button type=\"button\" id=\"loading-btn\" data-loading-text=\"Loading...\" class=\"btn btn-info\" style=\"background-color:#D66061;\" onclick=\"window.location.href='googlelogin?usergetting="+GooglePlusAcc+"';\">Add Google+ Account</button>\n" +
"										</div>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n" +
"						</div>");
            
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
"		<!-- jQuery UI JS -->\n" +
"		<script src=\"js/jquery-ui-v1.10.3.js\"></script>\n" +
"		<!-- Include all compiled plugins (below), or include individual files as needed -->\n" +
"		<script src=\"js/bootstrap.min.js\"></script>\n" +

"		<!-- Sparkline graphs -->\n" +
"		<script src=\"js/sparkline.js\"></script>\n" +

"		<!-- jquery ScrollUp JS -->\n" +
"		<script src=\"js/scrollup/jquery.scrollUp.js\"></script>\n" +

"		<!-- Notifications JS -->\n" +
"		<script src=\"js/alertify/alertify.js\"></script>\n" +
"		<script src=\"js/alertify/alertify-custom.js\"></script>\n" +

"		<!-- Flot Charts -->\n" +
"		<script src=\"js/flot/jquery.flot.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.tooltip.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.resize.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.stack.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.orderBar.min.js\"></script>\n" +
"		<script src=\"js/flot/jquery.flot.pie.min.js\"></script>\n" +

"		<!-- JVector Map -->\n" +
"		<script src=\"js/jvectormap/jquery-jvectormap-1.2.2.min.js\"></script>\n" +
"		<script src=\"js/jvectormap/jquery-jvectormap-usa.js\"></script>\n"+
"		<!-- Custom Index -->\n" +
"		<script src=\"js/custom.js\"></script>\n" +
"		<script src=\"js/custom-index.js\"></script>\n");
            out.println("</body>");
            out.println("</html>");
     } catch (Exception ex) {
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
