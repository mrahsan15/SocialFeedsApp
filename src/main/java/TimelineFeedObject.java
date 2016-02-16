
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jinstagram.entity.users.feed.MediaFeedData;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.UserMentionEntity;

public class TimelineFeedObject {
    
    String Caption = "";
    String MediaUrl="";
    String VideoTags ="";
    String UserName = "";
    String UserFullName = "";
    String UserProfilePic = "";
    String UserID = "";
    String Bio = "";
    Date datetime = null;
    String source = "";
    int medianumber = 0;
    int followers = 0;
    int followings = 0;
    int favorites = 0;
    boolean liked = false;
    int likes = 0;
    int comments = 0;
    boolean retweetedbyme = false;
    boolean retweet = false;
    boolean retweeted = false;
    String statusid = "";
    int retweets = 0;
    String mediatype = "photo, video, animated_gif";
    String mediaid = "";
    String mediaurl = "";
    MediaEntity[] mediaentity ;
    UserMentionEntity[] mentions;
    List<String> hashtags = new ArrayList<String>();
    SimpleDateFormat Time = new SimpleDateFormat("hh:mm a");
    SimpleDateFormat Date = new SimpleDateFormat("MMM d, yy");
    twitter4j.Status retweetedstatus;


    public TimelineFeedObject(twitter4j.Status twitterstatus){
        try{
            UserName = twitterstatus.getUser().getScreenName();
            UserFullName = twitterstatus.getUser().getName();
            UserID = twitterstatus.getUser().getId()+"";
            UserProfilePic = twitterstatus.getUser().getOriginalProfileImageURL();
            Bio = twitterstatus.getUser().getDescription();
            medianumber = twitterstatus.getUser().getStatusesCount();
            followers = twitterstatus.getUser().getFollowersCount();
            followings = twitterstatus.getUser().getFriendsCount();
            favorites= twitterstatus.getUser().getFavouritesCount();
            try{
                Caption = twitterstatus.getText();
            }catch(Exception ex){
                Caption = "";
            }
            
            datetime = twitterstatus.getCreatedAt();
            likes = twitterstatus.getFavoriteCount();
            
            retweets = twitterstatus.getRetweetCount();
            liked = twitterstatus.isFavorited();
            retweetedbyme = twitterstatus.isRetweetedByMe();
            retweeted =  twitterstatus.isRetweeted();
            retweet = twitterstatus.isRetweet();
            statusid = twitterstatus.getId()+"";
            mentions = twitterstatus.getUserMentionEntities();
            HashtagEntity[] hashtagentity = twitterstatus.getHashtagEntities();
            if(hashtagentity.length> 0){
                for(int i = 0; i < hashtagentity.length;i++){
                    hashtags.add(hashtagentity[i].getText());
                }
            }
            mediaentity = twitterstatus.getMediaEntities();
            source = "twitter";
            retweetedstatus = twitterstatus.getRetweetedStatus();
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    public TimelineFeedObject(MediaFeedData instastatus){
        try{
            
            source = "insta";
            UserName = instastatus.getUser().getUserName();
            UserFullName = instastatus.getUser().getFullName();
            UserID = instastatus.getUser().getId();
            UserProfilePic = instastatus.getUser().getProfilePictureUrl();
            Bio = instastatus.getUser().getBio();
            
            try{
                Caption = instastatus.getCaption().getText();
            }catch(Exception ex){
                Caption = "";
            }
            
            datetime = new Date(Long.parseLong(instastatus.getCreatedTime()+"000"));
            likes = instastatus.getLikes().getCount();
            liked = instastatus.isUserHasLiked();
            statusid = instastatus.getId();
            if(instastatus.getType().equals("image")){
                mediatype = "image";
                mediaid= instastatus.getId();
                mediaurl = instastatus.getImages().getStandardResolution().getImageUrl();
            }else if(instastatus.getType().equals("video")){
                mediatype = "video";
                mediaid= instastatus.getId();
                mediaurl = instastatus.getVideos().getStandardResolution().getUrl();
            }
            comments = instastatus.getComments().getCount();
            hashtags = instastatus.getTags();
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
}