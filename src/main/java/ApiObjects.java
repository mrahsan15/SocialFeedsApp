
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;

public class ApiObjects {
    String code = "";
    String token = "";
    HttpServletRequest request;
    HttpServletResponse response;
    
    public ApiObjects(HttpServletRequest Request, HttpServletResponse Response){
        request = Request;
        response = Response;
    }
    public void InstagramObject(){
        
        
    }
    
}
