/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.exceptions.InstagramException;
import twitter4j.Friendship;
import twitter4j.MediaEntity;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

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
//        String user = "259220806";
//        String source = "insta";

        InstagramService servicCe = null ;
//        String code = "";
        Twitter twitter = null;
//                (Twitter) request.getAttribute("twitter");
//        try{
//            if(twitter.getScreenName().equals("null")){
//            }
//            else{
//                twitter = new TwitterFactory().getInstance();
//            }
//        }catch(Exception ex){
//            twitter = new TwitterFactory().getInstance();
//            
//        }
        try {
//            service = new InstagramAuthService()
//            .apiKey("e0bbe4b568cd453e925d7962ad2b9c7c")
//            .apiSecret("f11f9582bef947b4b28bf871ce36a06c")
//            .callback("http://192.168.1.100:8080/SocialFeedsApp/dashboard")
//            .scope("basic,public_content,comments,follower_list,relationships,likes")
//            .build();
//            
//            code = request.getParameter("code");
//            
//            Cookie[] cookies = request.getCookies();
//            String token = "";
//            for(int i = 0; i< cookies.length; i++){
//                if((cookies[i].getName()).equals("token")){
//                    token = cookies[i].getValue();
//                }
//            }
//            if(token.equals("")){
//                if(request.getParameter("code").equals("null")){
//                    response.sendRedirect(service.getAuthorizationUrl(null));
//                }
//                else{
//                    Verifier verifier = new Verifier(request.getParameter("code"));
//                    Token tokens = service.getAccessToken(null, verifier);
//                    token = tokens.getToken();
//                    Cookie tokentoken = new Cookie("token",token);
//                    response.addCookie(tokentoken);
//                    String name = new Instagram(new Token(token,"")).getCurrentUserInfo().getData().getFullName();
                    Instagram insta =null ;
//                            new Instagram(new Token(token,""));
                    PrintPage(insta,out,user,source,twitter);
//                }
//            }else{
//                Instagram insta = new Instagram(new Token(token,""));
//                PrintPage(insta, out,user,source,twitter);
//            }
        }   
        catch(Exception ex){
//            response.sendRedirect(service.getAuthorizationUrl(null));
            System.out.println(ex);
        }
    }
    
    public void PrintPage(Instagram insta, PrintWriter out, String user,String source,Twitter twitter){
        UserInfo info = null;
        CommonWidgets widget = new CommonWidgets(out);
        ArrayList commentsList = new ArrayList();
        int count = 0;
        String ProfilePic = "";
        String UserName = "";
        String Bio = "";
        String FullName= "";
        int follower =0;
        int following = 0;
        int mediacount = 0;
        int favorites = 0;
        String VideoTags = "";
        String FollowStatus = "";
        String FollowBackStatus = "";
        boolean PrivateAccount = false;
        String IncomingStatus= "followed_by";
        String OutgoingStatus = "follows";
        String PrivateUserDisplay = "";
        //Timeline Feed in an Array to Process it
        ArrayList timelinefeeds = new ArrayList();
        TimelineFeedObject timeline = null ;
            
        try{
            if(source.equals("insta") && (!user.equals("null"))){
                //Getting UserName from Screenname Passed in Parameter
                boolean isUsername = user.matches(".*[a-zA-Z]+.*");
                if(isUsername){
                    UserFeed u = insta.searchUser(user);
                    for(int z = 0;z < u.getUserList().size(); z++){
                        if(((u.getUserList().get(z)).getUserName()).equals(user)){
                            user = u.getUserList().get(z).getId();
                        }
                    }
                }
                //Checking Account Relationship Status to CurrentLoggedIn
                PrivateAccount = insta.getUserRelationship(user).getData().isTargetUserPrivate();
                IncomingStatus = insta.getUserRelationship(user).getData().getIncomingStatus();
                OutgoingStatus = insta.getUserRelationship(user).getData().getOutgoingStatus();
                //FollowStatus Button, Will be displayed next to User Profile Title
                if(OutgoingStatus.equals("follows")){
                    FollowStatus = "<button class=\"btn btn-default btn-rounded\" "
                            + "style=\"margin-left: 20px;"
                            + "margin-top: -10px;\">"
                            + "Following</button>";
                }
                //FollowBack Status. Will be displayed next to ID of User
                if(IncomingStatus.equals("followed_by")){
                    FollowBackStatus = "<span class=\"label label-default\" "
                            + "style=\"margin-left: 5px;"
                            + "font-weight:100;\">"
                            + "Follows you</span>";
                }
                //Checking if you're eligible to view the timeline
                if((PrivateAccount && (IncomingStatus.equals("followed_by"))
                        || (!PrivateAccount))){
                    //Inserting Specified UserProfile's Detail to Page Banner
                    ProfilePic = insta.getUserInfo(user).getData().getProfilePicture();
                    UserName =insta.getUserInfo(user).getData().getUsername();
                    FullName =insta.getUserInfo(user).getData().getFullName();
                    Bio = insta.getUserInfo(user).getData().getBio();
                    mediacount = insta.getUserInfo(user).getData().getCounts().getMedia();
                    follower = insta.getUserInfo(user).getData().getCounts().getFollowedBy();
                    following = insta.getUserInfo(user).getData().getCounts().getFollows();
                    try{
                        //Getting MediaFeed of User Specified
                        List<MediaFeedData> mediafeeddata = insta.getRecentMediaFeed(user).getData();
                        for(int i = 0; i < mediafeeddata.size(); i++){
                            timeline = new TimelineFeedObject(mediafeeddata.get(i));
                            timelinefeeds.add(timeline);
                        }
                        //Sort List in Ascending Order
                        Collections.sort(timelinefeeds,new Comparator(){
                            @Override
                            public int compare(Object o1, Object o2) {
                                return ((TimelineFeedObject)o1).datetime.compareTo(((TimelineFeedObject)o2).datetime);
                            }
                        });
                    }catch(Exception ex){
                        PrivateUserDisplay = "<h1>You cannot view this User!</h1>";
                        System.out.println("Bad Request! You cannot view this User!");
                    }
                }else{
                    PrivateUserDisplay = "<h1>You cannot view this User!</h1>";
                    System.out.println("Bad Request! You cannot view this User!");
                }
                
                
            }else if(source.equals("twitter") && (!user.equals("null"))){
                twitter4j.Paging paging;
                ResponseList<Status> statuses;
                //Checking if User parameter is ScreenName
                boolean isUsername = user.matches(".*[a-zA-Z]+.*");
                if(isUsername){
                    paging = new twitter4j.Paging(1, 200);
                    statuses= twitter.getUserTimeline(user, paging);
                }else{
                    paging = new twitter4j.Paging(1, 200);
                    statuses= twitter.getUserTimeline(Long.parseLong(user), paging);
                }
                ResponseList<Friendship> Relationship =twitter.lookupFriendships(new String[]{UserName});
                Friendship relation =Relationship.get(0);
                PrivateAccount = statuses.get(0).getUser().isProtected();
                if(relation.isFollowing()){
                    FollowStatus = "<button class=\"btn btn-default btn-rounded\" "
                            + "style=\"margin-left: 20px;"
                            + "margin-top: -10px;\">"
                            + "Following</button>";
                }
                if(relation.isFollowedBy()){
                    FollowBackStatus = "<span class=\"label label-default\" "
                            + "style=\"margin-left: 5px;"
                            + "font-weight:100;\">"
                            + "Follows you</span>";
                }
                if((PrivateAccount && relation.isFollowedBy()) || (!PrivateAccount)){
                    //Inserting Specified UserProfile's Detail to Page Banner
                    ProfilePic = statuses.get(0).getUser().getOriginalProfileImageURL();
                    UserName =statuses.get(0).getUser().getScreenName();
                    FullName =statuses.get(0).getUser().getName();
                    Bio = statuses.get(0).getUser().getDescription();
                    mediacount = statuses.get(0).getUser().getStatusesCount();
                    follower = statuses.get(0).getUser().getFollowersCount();
                    following = statuses.get(0).getUser().getFriendsCount();
                    favorites = statuses.get(0).getUser().getFavouritesCount();
                    //Adding Status to ArrayList
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
            }
        }catch(Exception ex){
            System.out.println("Exception Caught! No Username was found.");
        }
        
        //Page Output Starts Here
        widget.HeadRegion(out);
        out.println(
"	<body>\n" );
        out.println(
"		<!-- Header Start -->\n" +
"		<header>\n");
        widget.Logo(out);
        widget.Search(out);
        widget.RightNavBar(out);
        out.println(
"		</header>\n" +
"		<!-- Header ends -->\n" );
        out.println(
"		<!-- Left sidebar starts -->\n" +
"		<aside id=\"sidebar\">\n" );
            widget.CurrentUser(out);
            widget.Menu(out, "Profile");
            out.println("<!-- Freebies Starts -->\n"
                    + "<div class=\"freebies\">"
                    + "<!-- Sidebar Extras -->"
                    + "<div class=\"sidebar-addons\">"
                    + "<ul class=\"views\">"
                    + "<li>"
                    + "<i class=\"fa fa-circle-o text-success\"></i>"
                    + "<div class=\"details\">"
                    + "<p>Signups</p>"
                    + "</div>"
                    + "<span class=\"label label-success\">8</span>"
                    + "</li"
                    + "<li>"
                    + "<i class=\"fa fa-circle-o text-info\"></i>"
                    + "<div class=\"details\">\n" +
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
"		<!-- Left sidebar ends -->\n" );
            out.println(
"		<!-- Dashboard Wrapper starts -->\n" +
"		<div class=\"dashboard-wrapper\">\n" );
            out.println(
"			<!-- Top Bar starts -->\n" +
"			<div class=\"top-bar\">\n" +
"				<div class=\"page-title\">\n" +
"					User Profile\n"
                                        + FollowStatus +
"				</div>\n" +
"				<ul class=\"stats hidden-xs\">\n" +
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"downloads_graph\"></span>\n" +
"						</div>\n" +
"						<div class=\"stats-details\">\n" +
//"							<h4><span id=\"\">"+follower+"</span> <i class=\"fa fa-chevron-up up\"></i></h4>\n" +
//"							<h5>Followers</h5>\n" +
"						</div>\n" +
"					</li>\n" +
"					<li>\n" +
"						<div class=\"stats-block hidden-sm hidden-xs\">\n" +
//"							<span id=\"users_online_graph\"></span>\n" +
"						</div>\n" +
"                           			<div class=\"stats-details\">\n" +
//"							<h4><span id=\"\">"+following+"</span> <i class=\"fa fa-chevron-down down\"></i></h4>\n" +
//"							<h5>Followings</h5>\n" +
"						</div>\n" +
"					</li>\n" +
"				</ul>\n" +
"			</div>\n" +
"			<!-- Top Bar ends -->\n" );
            out.println(
"			<!-- Main Container starts -->\n" +
"			<div class=\"main-container\">\n" +
"				<!-- Container fluid Starts -->\n" +
"				<div class=\"container-fluid\">\n" +
"					\n" +
"					<!-- Spacer Starts -->\n" +
"					<div class=\"spacer\">\n" +
"						<!-- Row start -->\n" +
"						<div class=\"row\">\n" +
"							<div class=\"col-md-12 col-sm-12 col-sx-12\">\n" +
"								<div class=\"current-profile\" style=\"background:"+ProfilePic+";\">\n" +
"									<div class=\"user-bg\"></div>\n" +
"									<div class=\"user-pic\" style=\"background:url("+ProfilePic+") no-repeat;\">&nbsp; </div>\n" +
"									<div class=\"user-details\">\n" +
"										<h4 class=\"user-name\">"+FullName+" ("+UserName+")"+"<i>!</i>"
                                                                                        + FollowBackStatus
                                                                                        + "</h4>\n" +
"										<h5 class=\"description\">"+Bio+"</h5>\n" +
"									</div>\n" +
"									<div class=\"social-list\">\n" +
"										<div class=\"row\">\n" +
"											<div class=\"col-md-6 col-md-offset-3\">\n" +
"												<div class=\"row\">\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+mediacount+"</h3>\n" +
"														<small>Posts</small>\n" +
"													</div>\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+follower+"</h3>\n" +
"														<small>Followers</small>\n" +
"													</div>\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+following+"</h3>\n" +
"														<small>Followings</small>\n" +
"													</div>\n" +
"													<div class=\"col-md-3 col-sm-3 col-xs-3 center-align-text\">\n" +
"														<h3>"+favorites+"</h3>\n" +
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
            try{
                
            }catch(Exception ex){
                
            }
            if(source.equals("twitter")){
                for(int i = timelinefeeds.size(); i > 0 ; i--){
                    try{
                        
                    timeline = (TimelineFeedObject) timelinefeeds.get(i-1);
                    String row = "";
                    String retweetedcolor = "#AAB8C2";
                    String favoritedcolor = "#AAB8C2";
                    String Caption = timeline.Caption;
                    
                    //Setting Query HashtagsUrl
                    String HashtagUrl = "<a href=\"timeline?q=hashtag\">#hashtag</a>";
                    List<String> hashtags = timeline.hashtags;
                    if(hashtags.size()>0){
                        for(int z = 0; z < hashtags.size(); z++){
                            HashtagUrl= HashtagUrl.replace("hashtag", hashtags.get(z));
                            Caption = Caption.replace("#"+hashtags.get(z), HashtagUrl);
                        }
                    }
                    
                    //if Tweet is liked or Retweeted by Authenticated User
                    if(timeline.liked){
                        favoritedcolor = "#e81c4f";
                    }
                    if(timeline.retweetedbyme){
                        retweetedcolor = "#19cf86";
                    }
                    if(timeline.retweet){
                        retweetedcolor = "#19cf86";
                    }
                    
                    //Fetching Media Mention in Tweet
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
                        
                        //Displaying Tweet
                        out.println("<div class=\"timeline-row\">\n"
                                + "<div class=\"timeline-time\">\n"
                                + timeline.Time.format(timeline.datetime)
                                + "<small>"+timeline.Date.format(timeline.datetime)+"</small>"
                                + "</div>"
                                + "<div class=\"timeline-icon\">"
                                + "<div class=\"twitter-bg\">"
                                + "<i class=\"fa fa-twitter\"></i>"
                                + "</div>"
                                + "</div>"
                                + "<div class=\"panel timeline-content\">\n"
                                + "<div class=\"panel-body\">\n" 
                                +"<div style=\"float:left; padding:0;\">"
                                + "<a href=\"Profile?username="+timeline.UserID+"&source=twitter\">"
                                + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                                + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                                + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                                + "<br>("+timeline.UserName+")"
                                + "</a>"
                                + "<br>"
                                + "</div></div><br>"
                                + "<div class=\"row\">\n"
                                + row 
                                +"</div>"
                                + "<p>\n" 
                                +Caption
                                + "<br>"
                                + "<i class=\"fa fa-retweet fa-2x\" style=\""
                                + "padding-right: 35px;color:"+retweetedcolor+";\"></i>"
                                + "<i class=\"fa fa-heart fa-2x\" style=\""
                                + "color: "+favoritedcolor+";"
                                + "width: 50px;"
                                + "padding-top: 20px;"
                                + "\"></i>"
                                + "</p>\n"
                                + "</div>"
                                + "</div>"
                                + "</div>");
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
                                "<small>"+timeline.Date.format(timeline.datetime)+"</small>"
                                + "</div>"
                                + "<div class=\"timeline-icon\">"
                                + "<div class=\"twitter-bg\">"
                                + "<i class=\"fa fa-twitter\"></i>\n"
                                + "</div>"
                                + "</div>"
                                + "<div class=\"panel timeline-content\">"
                                + "<div class=\"panel-body\">\n" 
                                + "<div style=\"float:left; padding:0;\">"
                                + "<a href=\"Profile?username="+timeline.UserID+"&source=twitter\">"
                                + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                                + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                                + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                                + "<br>("+timeline.UserName+")"
                                + "</a>"
                                + "<br>"
                                + "</div></div><br>");
                        out.println(VideoTags
                                +"<p style=\"padding-top: 50px;\">\n"
                                + Caption
                                +"<br><i class=\"fa fa-retweet fa-2x\" style=\"\n" 
                                +"padding-right: 35px;color:"+retweetedcolor+";\"></i>"
                                + "<i class=\"fa fa-heart fa-2x\" style=\""
                                    + "color: "+favoritedcolor+";"
                                    + "width: 50px;"
                                    + "padding-top: 20px;\">"
                                + "</i>"
                                + "</p>"
                                + "</div>"
                                + "</div>"
                                + "</div>");
                        
                    }else{
                        //Displaying Tweet with No Media
                        boolean retweeted = timeline.retweet;
                        
                        if(retweeted){
                            out.println("<div class=\"timeline-row\">"
                                    + "<div class=\"timeline-time\">\n" +
                                    timeline.Time.format(timeline.retweetedstatus.getCreatedAt())
                                    + "<small>"+timeline.Date.format(timeline.retweetedstatus.getCreatedAt())+"</small>"
                                    + "</div>"
                                    + "<div class=\"timeline-icon\">"
                                    + "<div class=\"twitter-bg\">"
                                    + "<i class=\"fa fa-twitter\"></i>"
                                    + "</div>"
                                    + "</div>"
                                    + "<div class=\"panel timeline-content\">"
                                    + "<div class=\"panel-body\">"
                                    + "<i class=\"fa fa-retweet\" style=\"color: "+retweetedcolor+";\"></i>"+timeline.UserName+" Retweeted"
                                    + "<br><br>"
                                    + "<div style=\"float:left; padding:0;\">"
                                    + "<a href=\"Profile?username="+timeline.retweetedstatus.getUser().getId()+"&source=twitter\">"
                                    + "<img src=\""+timeline.retweetedstatus.getUser().getOriginalProfileImageURL()+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                                    + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                                    + "<font size=\"+1\"><Strong>"+timeline.retweetedstatus.getUser().getName()+"</strong></font>"
                                    + "<br>"+timeline.retweetedstatus.getUser().getScreenName()+""
                                    + "</a><br>"
                                    + "</div>"
                                    + "</div><br>"
                                    + "<p style=\"padding-top: 50px;\">"
                                    + "<br>\n"
                                    + timeline.retweetedstatus.getText()
                                    + "<br>"
                                    + "<i class=\"fa fa-retweet fa-2x\" style=\"padding-right: 35px;color: "+retweetedcolor+";\"></i>"
                                    + "<i class=\"fa fa-heart fa-2x\" style=\"color: "+favoritedcolor+"; width: 50px; padding-top: 20px;\"></i>"
                                    + "</p>"
                                    + "</div>"
                                    + "</div>"
                                    + "</div>");
                        }else{
                        
                        
                        out.println("<div class=\"timeline-row\">"
                                + "<div class=\"timeline-time\">\n" 
                                + timeline.Time.format(timeline.datetime)
                                + "<small>"+timeline.Date.format(timeline.datetime)+"</small>"
                                + "</div>"
                                + "<div class=\"timeline-icon\">"
                                + "<div class=\"twitter-bg\">"
                                + "<i class=\"fa fa-twitter\"></i>"
                                + "</div>"
                                + "</div>"
                                + "<div class=\"panel timeline-content\">\n"
                                + "<div class=\"panel-body\">\n" + "<div style=\"float:left; padding:0;\">"
                                + "<a href=\"Profile?username="+timeline.UserID+"&source=twitter\">"
                                + "<img src=\""+timeline.UserProfilePic+"\" height=\"42\" width=\"42\" alt=\"Current User\">"
                                + "<div style=\"padding:0; margin-left:10px; float:right;\">"
                                + "<font size=\"+1\"><Strong>"+timeline.UserFullName+"</strong></font>"
                                + "<br>("+timeline.UserName+")"
                                + "</a>"
                                + "<br>"
                                + "</div></div><br>" 
                                +"<p style=\"padding-top: 50px;\">"
                                + "");
                        out.println(
                                Caption 
                                + "</p><br><i class=\"fa fa-retweet fa-2x\" style=\""
                                + "padding-right: 35px;color:"+retweetedcolor+";\"></i>"
                                + "<i class=\"fa fa-heart fa-2x\" style=\""
                                + "color: "+favoritedcolor+";"
                                + "width: 50px;"
                                + "padding-top: 20px;\"></i>");
                        out.println("</div>\n" 
                                +"</div>\n" 
                                +"</div>");
                        }
                    }
                    }catch(Exception ex){
                        
                    }
                }
            }
            else if(source.equals("insta")){
                for(int i = timelinefeeds.size(); i > 0 ; i--){
                    try{
                    if((PrivateAccount && OutgoingStatus.equals("follows"))
                            || !PrivateAccount){
                        timeline = (TimelineFeedObject) timelinefeeds.get(i-1);
                        String Caption = timeline.Caption;
                        String HashtagUrl = "<a href=\"#\">#hashtag</a>";
                        List<String> hashtags = timeline.hashtags;
                        if(hashtags.size()>0){
                            for(int z = 0; z < hashtags.size(); z++){
                                HashtagUrl= HashtagUrl.replace("hashtag", hashtags.get(z));
                                Caption = Caption.replace("#"+hashtags.get(z), HashtagUrl);
                            }
                        }
                        try{
                            Pattern p = Pattern.compile("\\#\\w*(\\S+)");
                            Matcher m = p.matcher(Caption);
                            StringBuffer sb = new StringBuffer();
                            while(m.find()){
                                String ReplaceableElement = Caption.substring(m.start(), m.end());
                                m.appendReplacement(sb,"<a href=\"#\">#"+ReplaceableElement+"</a>");
                            }
                            m.appendTail(sb);
                        }catch(Exception ex){
                            System.out.println("HashtagParsing Error!");
                            System.out.println(ex);
                        }
                        
                        if(timeline.mediatype.equals("image")){
                            VideoTags = "<img class=\"img-responsive\" src=\""+timeline.mediaurl+"\" alt=\"User\" />\n";
                        }else if(timeline.mediatype.equals("video")){
                            VideoTags = "<video width=\"320\" height=\"320\" controls>"
                                + "<source src=\""+timeline.mediaurl+"\" type=\"video/mp4\"/></video>";
                        }

                        HashMap<String,String> map  = new HashMap();
                        String MapValue = timeline.mediaid+","+(i);
                        map.put(count+"", MapValue);
                        commentsList.add(map);
                        count++;
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
                                + "<button class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#modalMd"+(i)+"\">\n" 
                                + "Comments ("+timeline.comments+")" 
                                +"</button>"
                                + "</div>"
                                + "</div>" 
                                + "</div>\n");
                    }else{
                        out.println("<div class=\"col-lg-12 col-md-12 col-sm-12 col-sx-12\">\n" 
                                +"<div class=\"panel\">\n" 
                                +"<div class=\"panel-heading\">\n" 
                                +"<h3 class=\"panel-title\">Large Column</h3>\n" 
                                +"</div>\n" 
                                +"<div class=\"panel-body\">\n" 
                                +"<p>\n" 
                                +"It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" 
                                +"</p>\n" 
                                +"</div>\n" 
                                +"</div>\n" 
                                +"</div>");
                    }
                    }catch(Exception ex){
                        
                    }
                }
            }
                out.println("</div>\n" +
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
                for(int i = 0; i < commentsList.size();i++){
                    HashMap<String, String> map = (HashMap<String, String>) commentsList.get(i);
                    try{
                        String MEDIATEMP = map.get(i+"");
                        String MEDIAID = MEDIATEMP.split(",")[0];
                        int POSTNO = Integer.parseInt(MEDIATEMP.split(",")[1]);
                        this.GetComments(insta, MEDIAID, POSTNO, out);
                    }catch(Exception ex){

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
"				Copyright Apisylux 2016.\n" +
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
    
    }
    public void GetComments(Instagram insta,String MediaId, int PostNumber,PrintWriter out){
        
        out.println("<!-- Modal -->\n"
                    + "<div class=\"modal fade\" id=\"modalMd"+PostNumber+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel5\" aria-hidden=\"true\">\n"
                    + "<div class=\"modal-dialog\">\n"
                    + "<div class=\"modal-content\">\n"
                    + "<div class=\"modal-header\">\n"
                    + "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n"
                    +"<h4 class=\"modal-title text-info\" id=\"myModalLabel5\">Comments</h4>\n"
                    +"</div>\n"
                    +"<div class=\"modal-body\"><p>");
        
        String commentbody= "";
        try {
            Pattern p = Pattern.compile("\\@\\w*(\\S+)");
            Matcher m ;
            MediaInfoFeed Media = insta.getMediaInfo(MediaId);
            
            Comments Comments = Media.getData().getComments();
            List<CommentData> comments = Comments.getComments();
            
            for(int i = 0 ; i < comments.size(); i++){
                CommentData comment = comments.get(i);
                String CommentText = comment.getText();
                m = p.matcher(CommentText);
                StringBuffer sb = new StringBuffer();
                
                String CommentFrom = "<a href=\"Profile?username="+comment.getCommentFrom().getId()+"&source=insta\">"+comment.getCommentFrom().getUsername()+"</a>: ";
                commentbody = "<p>"+CommentFrom;
                while(m.find()){
                    String ReplaceableElement = CommentText.substring(m.start(), m.end());
                    String InsertComment = "";
                    InsertComment+= "";
                    m.appendReplacement(sb,"<a href=\"Profile?username="+ReplaceableElement.replace("@", "")+"&source=insta\">"+ReplaceableElement+"</a>");

                }
                m.appendTail(sb);
                
                commentbody = commentbody+sb.toString()+"</p>";
                out.println(commentbody);
            }
            out.println("</p></div>\n"
                    +"<div class=\"modal-footer\">\n"
                    +"<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\"><i class=\"fa fa-times\"></i> Close</button>\n"
                    +"<button type=\"button\" class=\"btn btn-success\"><i class=\"fa fa-save\"></i> Save</button>\n"
                    +"</div>\n"
                    +"</div>\n"
                    +"</div>\n"
                    +"</div>");
            
            
        } catch (InstagramException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
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
