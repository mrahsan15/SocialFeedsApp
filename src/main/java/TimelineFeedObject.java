
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
    Date datetime = null;
    String source = "";
    int medianumber = 0;
    boolean liked = false;
    int likes = 0;
    int comments = 0;
    boolean retweeted = false;
    boolean retweet = false;
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


    public TimelineFeedObject(twitter4j.Status twitterstatus){
        UserName = twitterstatus.getUser().getScreenName();
        UserFullName = twitterstatus.getUser().getName();
        UserID = twitterstatus.getUser().getId()+"";
        UserProfilePic = twitterstatus.getUser().getOriginalProfileImageURL();
        Caption = twitterstatus.getText();
        datetime = twitterstatus.getCreatedAt();
        likes = twitterstatus.getFavoriteCount();
        
        retweets = twitterstatus.getRetweetCount();
        liked = twitterstatus.isFavorited();
        retweeted = twitterstatus.isRetweetedByMe();
        retweet =  twitterstatus.isRetweeted();
        statusid = twitterstatus.getId()+"";
        mediaid =  twitterstatus.getMediaEntities()[0].getId()+"";
        mediaurl = twitterstatus.getMediaEntities()[0].getMediaURL();
        mentions = twitterstatus.getUserMentionEntities();
        HashtagEntity[] hashtagentity = twitterstatus.getHashtagEntities();
        if(hashtagentity.length> 0){
            for(int i = 0; i < hashtagentity.length;i++){
                hashtags.add(hashtagentity[i].getText());
            }
        }
        mediaentity = twitterstatus.getMediaEntities();
        source = "twitter";
        
    }
    public TimelineFeedObject(MediaFeedData instastatus){
        source = "insta";
        UserName = instastatus.getUser().getUserName();
        UserFullName = instastatus.getUser().getFullName();
        UserID = instastatus.getUser().getId();
        UserProfilePic = instastatus.getUser().getProfilePictureUrl();
        Caption = instastatus.getCaption().getText();
        datetime = new Date(Long.parseLong(instastatus.getCreatedTime()));
        likes = instastatus.getLikes().getCount();
        liked = instastatus.isUserHasLiked();
        statusid = instastatus.getId();
        if(instastatus.getType().equals("image")){
            mediatype = "image";
            mediaurl = instastatus.getImages().getStandardResolution().getImageUrl();
        }else if(instastatus.getType().equals("image")){
            mediatype = "video";
            mediaurl = instastatus.getVideos().getStandardResolution().getUrl();
        }
        comments = instastatus.getComments().getCount();
        hashtags = instastatus.getTags();
        
    }
}