import facebook4j.Facebook;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import twitter4j.Location;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.api.TrendsResources;


public class timeline extends HttpServlet {
    String query = "";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,NullPointerException {
        
        
        
        response.setContentType("text/html;charset=UTF-8");
                response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        InstagramService service = null ;
        String code = "";
        String CopyRightSyntax = "Copyright Apisylux Dashboard Panel 2016";
        query = request.getParameter("q");
        Facebook facebook = null;
        Connection fab = new DBConnectivity().ConnectDB();
//        String quer = "SELECT * from TokensData.FACEBOOK_ACCOUNT";
//        try {
//            Statement fabst = fab.createStatement();
//            ResultSet fabrs = fabst.executeQuery(quer);
//            if(fabrs.next()){
//                String token = fabrs.getString("TOKEN");
//                AccessToken tokenn = new AccessToken(token,null);
//                facebook = new FacebookFactory().getInstance();
//                facebook.setOAuthAccessToken(tokenn);
//                System.out.println(facebook.getName());
//            }else{
//                response.sendRedirect(request.getContextPath()+"/login");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        } catch (FacebookException ex) {
//            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalStateException ex) {
//            Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        Twitter twitter = null;
        try{
            twitter = new TwitterFactory().getInstance();
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        
        
        try {
            service = new InstagramAuthService()
            .apiKey("e0bbe4b568cd453e925d7962ad2b9c7c")
            .apiSecret("f11f9582bef947b4b28bf871ce36a06c")
            .callback("http://192.168.1.100:8080/SocialFeedsApp/timeline")
            .build();
            
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
                if(request.getParameter("code").equals("null")){
                    response.sendRedirect(service.getAuthorizationUrl(null));
                }
                else{
                    Verifier verifier = new Verifier(request.getParameter("code"));
                    Token tokens = service.getAccessToken(null, verifier);
                    token = tokens.getToken();
                    Cookie tokentoken = new Cookie("token",token);
                    response.addCookie(tokentoken);
                    String name = new Instagram(new Token(token,"")).getCurrentUserInfo().getData().getFullName();
                    Instagram insta =new Instagram(new Token(token,""));
                    PrintPage(insta, out,facebook,twitter);
                }

            }else{
                String name = new Instagram(new Token(token,"")).getCurrentUserInfo().getData().getFullName();
                Instagram insta = new Instagram(new Token(token,""));
                PrintPage(insta,out,facebook,twitter);
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
public void PrintPage(Instagram insta, PrintWriter out,Facebook facebook,Twitter twitter){
    boolean querypassed = false;
    
    UserInfo info = null;
    ArrayList timelinefeeds = new ArrayList();
    TimelineFeedObject timeline ;
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
        try{
            if(query.equals("null")){
                
            }else{
                querypassed = true;
                Query q = new Query("#"+query);
                q.count(100);
                QueryResult qresult= twitter.search(q);
                List<Status> statuses =qresult.getTweets();
                for(int i = 0; i < statuses.size(); i++){
                    timeline= new TimelineFeedObject(statuses.get(i));
                    timelinefeeds.add(timeline);
                }
                Collections.sort(timelinefeeds,new Comparator(){
                    @Override
                    public int compare(Object o1, Object o2) {
                        return ((TimelineFeedObject)o1).datetime.compareTo(((TimelineFeedObject)o2).datetime);
                    }
                });
            }
        }catch(Exception ex){
            
        }
        
        
    
        if(!querypassed){
            //Adding Twitter HomeTimeline feeds in ArrayList
        twitter4j.Paging paging = new twitter4j.Paging(1, 200);
        twitter4j.ResponseList<Status> statuses = twitter.getHomeTimeline(paging);
        for(int i = 0; i < statuses.size(); i++){
            timeline= new TimelineFeedObject(statuses.get(i));
            timelinefeeds.add(timeline);
        }
        
        //Adding Instagram HomeTimeline feeds in ArrayList
        MediaFeed mediafeed = insta.getUserFeeds();
        List<MediaFeedData> mediafeeddata = mediafeed.getData();
        for(int i = 0; i < mediafeeddata.size(); i++){
            timeline = new TimelineFeedObject(mediafeeddata.get(i));
            timelinefeeds.add(timeline);
        }
        
        Collections.sort(timelinefeeds,new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                return ((TimelineFeedObject)o1).datetime.compareTo(((TimelineFeedObject)o2).datetime);
            }
            
        });
        
        }
        
        
        
//        ResponseList<Friend> friendlist=facebook.friends().getBelongsFriend("988763721143756");
//        
//        
//        friend = friend+ friendlist.size();
//        friendlist.getSummary().getTotalCount();
//        
//        for(int i = 0; i < friendlist.size(); i++){
//            System.out.println(friendlist.get(i).getName());
//        }
    
    
        widget.HeadRegion(out);
    
        out.println("<body>");
        out.println("<!-- Header Start -->\n" +
"		<header>");

        widget.Logo(out);
        
        widget.Search(out);
        widget.RightNavBar(out);
//      
        out.println("		</header>\n" +
"		<!-- Header ends -->");
        out.println("		<!-- Left sidebar starts -->\n" +
"		<aside id=\"sidebar\">");

        widget.CurrentUser(out);
        widget.Menu(out, "Timeline");
        
        out.println("			<!-- Freebies Starts -->\n" +
"			<div class=\"freebies\">");
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
        
//        Pagination userfeedpage = new Pagination();
//        UserFeed userfeed = insta.getUserFeedInfoNextPage(userfeedpage);
//        
        
        for(int i = timelinefeeds.size(); i >0 ; i--){
            timeline = (TimelineFeedObject) timelinefeeds.get(i-1);
            String VideoTags = "";
            String Caption = timeline.Caption;
            String HashtagUrl = "<a href=\"#\">#hashtag</a>";
            List<String> hashtags = timeline.hashtags;
            if(hashtags.size()>0){
                for(int z = 0; z < hashtags.size(); z++){
                    HashtagUrl= HashtagUrl.replace("hashtag", hashtags.get(z));
                    Caption = Caption.replace("#"+hashtags.get(z), HashtagUrl);
                }
            }

            
            
            if(timeline.source.equals("insta")){
                    
                if(timeline.mediatype.equals("image")){
                    VideoTags = "<img class=\"img-responsive\" src=\""+timeline.mediaurl+"\" alt=\"User\" />\n";
                }else if(timeline.mediatype.equals("video")){
                    VideoTags = "<video width=\"320\" height=\"320\" controls>"
                        + "<source src=\""+timeline.mediaurl+"\" type=\"video/mp4\"/></video>";
                }
                out.println(""
                        + "<div class=\"timeline-row\">"
                        + "<div class=\"timeline-time\">\n"
                        +   timeline.Time.format(timeline.datetime)
                        + "<small>"+timeline.Date.format(timeline.datetime)+"</small>"
                        + "</div>"
                        + "<div class=\"timeline-icon\">"
                        + "<div class=\"success-bg\">"
                        + "<i class=\"fa fa-camera-retro fa-2x\"></i>"
                        + "</div>"
                        + "</div>"
                        + "<div class=\"panel timeline-content\">"
                        + "<div class=\"panel-body\">"
                        + "<div style=\"float:left; padding:0;\">"
                        + "<a href=\"Profile?username="+timeline.UserID+"&source=insta\">"
                        + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                        + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                        + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                        + "<br>("+timeline.UserName+")"
                        + "</a>"
                        + "<br>"
                        + "</div></div><br>" 
                        + VideoTags
                        + "<p>" 
                        + Caption
                        + "</p>"
                        + "<button data-toggle=\"button\" class=\"btn btn-danger\"><i class=\"fa fa-thumbs-up \"></i>"+timeline.likes+"</button>"
                        + "<button type=\"button\" class=\"btn btn-info\">"+timeline.comments+"</button>"
                        + "</div>"
                        + "</div>" 
                        + "</div>\n");
            }else if(timeline.source.equals("twitter")){
                String row = "";
                String retweetedcolor = "#AAB8C2";
                String favoritedcolor = "#AAB8C2";
                if(timeline.liked){
                    favoritedcolor = "#e81c4f";
                }
                if(timeline.retweetedbyme){
                    retweetedcolor = "#19cf86";
                }
                
                
                MediaEntity[] media = timeline.mediaentity;
                if(media.length>1){
                    
                    for(int j =0; j < media.length; j++){
                        if(media[j].getType().equals("image")){
                            VideoTags = "<img class=\"img-responsive\" src=\""+media[j].getMediaURL()+"\" alt=\"User\" />\n";
                        }else if(media[j].getType().equals("video")){
                            VideoTags = "<video width=\"320\" height=\"320\" controls>"
                        + "<source src=\""+media[j].getMediaURL()+"\" type=\"video/mp4\"/></video>";
                        }else if(media[j].getType().equals("animated_gif")){
                            VideoTags = "<img class=\"img-responsive\" src=\""+media[j].getMediaURL()+"\" alt=\"User\" />\n";
                        }
                        row = row +"<div class=\"col-lg-3 col-md-3 col-sm-3 col-xs-3\">" 
                                +VideoTags
                                +"</div>";
                    }
                    
                    
                    out.println("<div class=\"timeline-row\">\n" +
"							<div class=\"timeline-time\">\n" +
                            timeline.Time.format(timeline.datetime)+ 
                            "<small>"+timeline.Date.format(timeline.datetime)+"</small>"+
                        "							</div>\n" +
"							<div class=\"timeline-icon\">\n" +
"								<div class=\"twitter-bg\">\n" +
"									<i class=\"fa fa-twitter\"></i>\n" +
"								</div>\n" +
"							</div>\n" +
"							<div class=\"panel timeline-content\">\n");
                    out.println(
                            "<div class=\"panel-body\">\n" 
                            +"<div style=\"float:left; padding:0;\">"
                            + "<a href=\"Profile?username="+timeline.UserID+"&source=twitter\">"
                            + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                            + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                            + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                            + "<br>("+timeline.UserName+")"
                            + "</a>"
                            + "<br>"
                            + "</div></div><br>" 
                            +"<div class=\"row\">\n" 
                            + row 
                            +"</div>\n" 
                            +"<p>\n" 
                            +Caption
                            +"<br>"
                            + "<i class=\"fa fa-retweet fa-2x\" style=\"\n" +
"    padding-right: 35px;color:"+retweetedcolor+";\"></i>"
                            + "<i class=\"fa fa-heart fa-2x\" style=\"\n" +
"    color: "+favoritedcolor+";\n" +
"    width: 50px;\n" +
"    padding-top: 20px;\n" +
"\"></i>"
                            + "</p>\n" +
"								</div>\n" +
"							</div>\n" +
"						</div>");
                }else if(media.length == 1){
                    
                    if((media[0].getType()).equals("photo")){
                        VideoTags = "<img class=\"img-responsive\" src=\""+media[0].getMediaURL()+"\" alt=\"User\" />\n";
                    }else if((media[0].getType()).equals("video")){
                        VideoTags = "<video width=\"320\" height=\"320\" controls>"
                    + "<source src=\""+media[0].getMediaURL()+"\" type=\"video/mp4\"/></video>";
                    }else if((media[0].getType()).equals("animated_gif")){
                        VideoTags = "<img class=\"img-responsive\" src=\""+media[0].getMediaURL()+"\" alt=\"User\" />\n";
                    }
                    
                    out.println("<div class=\"timeline-row\">\n" +
"							<div class=\"timeline-time\">\n" +
                            timeline.Time.format(timeline.datetime)+ 
                            "<small>"+timeline.Date.format(timeline.datetime)+"</small>"+
                        "							</div>\n" +
"							<div class=\"timeline-icon\">\n" +
"								<div class=\"twitter-bg\">\n" +
"									<i class=\"fa fa-twitter\"></i>\n" +
"								</div>\n" +
"							</div>\n" +
"							<div class=\"panel timeline-content\">\n" +
"								<div class=\"panel-body\">\n" 
                            + "<div style=\"float:left; padding:0;\">"
                            + "<a href=\"Profile?username="+timeline.UserID+"&source=twitter\">"
                            + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                            + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                            + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                            + "<br>("+timeline.UserName+")"
                            + "</a>"
                            + "<br>"
                            + "</div></div><br>" 
                            +   VideoTags+
"									<p style=\"padding-top: 50px;\">\n" +
                                                                            Caption+
"<br><i class=\"fa fa-retweet fa-2x\" style=\"\n" +
"    padding-right: 35px;color:"+retweetedcolor+";\"></i>"
                            + "<i class=\"fa fa-heart fa-2x\" style=\"\n" +
"    color: "+favoritedcolor+";\n" +
"    width: 50px;\n" +
"    padding-top: 20px;\n" +
"\"></i>"
                            + "</p>\n" +
"								</div>\n" +
"							</div>\n" +
"						</div>");
                }else{
                    
                    out.println("<div class=\"timeline-row\">\n" +
"							<div class=\"timeline-time\">\n" +
                            timeline.Time.format(timeline.datetime)+ 
                            "<small>"+timeline.Date.format(timeline.datetime)+"</small>"+
                        "							</div>" +
"							<div class=\"timeline-icon\">\n" +
"								<div class=\"twitter-bg\">\n" +
"									<i class=\"fa fa-twitter\"></i>\n" +
"								</div>\n" +
"							</div>\n" +
"							<div class=\"panel timeline-content\">\n" +
"								<div class=\"panel-body\">\n" + "<div style=\"float:left; padding:0;\">"
                            + "<a href=\"Profile?username="+timeline.UserID+"&source=twitter\">"
                            + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                            + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                            + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                            + "<br>("+timeline.UserName+")"
                            + "</a>"
                            + "<br>"
                            + "</div></div><br>" 
                            +"<p style=\"padding-top: 50px;\">\n" 
                            +Caption 
                            + "<br><i class=\"fa fa-retweet fa-2x\" style=\"\n" +
"    padding-right: 35px;color:"+retweetedcolor+";\"></i>"
                            + "<i class=\"fa fa-heart fa-2x\" style=\"\n" +
"    color: "+favoritedcolor+";\n" +
"    width: 50px;\n" +
"    padding-top: 20px;\n" +
"\"></i>"
                            + "</p>\n" 
                            +"</div>\n" 
                            +"</div>\n" 
                            +"</div>");
                }
            }
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
        out.println("<div class=\"row\">\n" +
"							<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n" +
"								<div class=\"blog\">\n" +
"									<div class=\"blog-header\">\n" +
"										<h5 class=\"blog-title\">Post Something</h5>\n" +
"									</div>\n" +
"									<div class=\"blog-body\">\n" +
"										\n" +
"										<button class=\"btn btn-primary btn-lg\" data-toggle=\"modal\" data-target=\"#dropper\">Show Dialog</button>\n" +
"										<div id=\"dropper\" class=\"modal fade\" tabindex=\"-1\" data-backdrop=\"static\" role=\"dialog\" aria-hidden=\"true\">\n" +
"											<div class=\"modal-dialog\">\n" +
"												<div class=\"modal-content\">\n" +
"													<div class=\"modal-body\">\n" +
"														<div id=\"dropping\"></div>\n" +
"													</div>\n" +
"													<div class=\"modal-footer\">\n" +
"														<button type=\"button\" class=\"btn btn-primary pull-left\">\n" +
"															<span class='fa fa-paperclip'></span>\n" +
"															Attach Assets\n" +
"														</button>\n" +
"														<div class=\"btn-group\">\n" +
"															<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">\n" +
"																&times; Cancel\n" +
"															</button>\n" +
"															<button type=\"button\" class=\"btn btn-warning\">\n" +
"																Post Status Update\n" +
"																<span class='fa fa-bullhorn'></span>\n" +
"															</button>\n" +
"														</div>\n" +
"													</div>\n" +
"												</div>\n" +
"											</div>\n" +
"										</div>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n" +
"						</div>");
        

        //Trend Featuring on Twitter Right now
        out.println("<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12\">\n" +
"								<div class=\"panel\">\n" +
"									<div class=\"panel-heading\">\n" +
"										<h4 class=\"panel-title\">Trending on Twitter</h4>\n" +
"									</div>\n" +
"									<div class=\"panel-body\">\n" +
"										<ul class=\"list-group no-margin\">\n");
        TrendsResources trends = twitter.trends();
        
        
        twitter4j.ResponseList<Location> locations;
        locations = twitter.getAvailableTrends();
        int woeid = 0;
        for(int i = 0; i < locations.size(); i++){
            if(locations.get(i).getName().toLowerCase().equals("rawalpindi")){
                Trend[] trend =trends.getPlaceTrends(locations.get(i).getWoeid()).getTrends();
                for(int j = 0; j< trend.length; j++){
                    String HashtagUrl = "<a href=\"timeline?q=hashtag\">#hashtag</a>";
                    HashtagUrl= HashtagUrl.replace("hashtag",(trend[j].getName().replace("#", "")));
                    out.println("<li class=\"list-group-item\""
                            + "style=\"" 
                            + "padding-top: 5px;"
                            + "padding-bottom: 5px;\">"
                            +HashtagUrl
                            +"</li>");
                }
            }
            
        }

        out.println(
"										</ul>\n" +
"									</div>\n" +
"								</div>\n" +
"							</div>\n" +
"							");
        out.println("			</div>\n" +
"			<!-- Right sidebar ends -->");
        out.println("			<!-- Footer starts -->\n" +
"			<footer>\n" +
"				Copyright Apisylux 2016.\n" +
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
"		<script src=\"js/custom.js\"></script>"
                + "<!-- Summer Note JS -->\n" +
"		<script src=\"js/summernote/summernote.js\"></script>");
        out.println("<script type=\"text/javascript\">\n" +
"			// Default\n" +
"			$(document).ready(function() {\n" +
"				$('.summernote').summernote({height: 280});\n" +
"			});\n" +
"\n" +
"			// Modal\n" +
"			$(function () {\n" +
"				$('#dropper').on('shown.bs.modal', function() {\n" +
"					$('#dropping').summernote({ height: 280, focus: true });\n" +
"				}).on('hidden.bs.modal', function () {\n" +
"					$('#dropping').destroy();\n" +
"				});\n" +
"			});\n" +
"		</script>"
                + "		"
                + "<script type=\"text/javascript\">\n" +
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