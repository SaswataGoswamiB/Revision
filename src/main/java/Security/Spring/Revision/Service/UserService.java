package Security.Spring.Revision.Service;

import Security.Spring.Revision.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authmanager;

   public String vertifyUser(User revisionuser){

        Authentication auth = new UsernamePasswordAuthenticationToken(revisionuser.getName(),revisionuser.getPassword());

        Authentication authenticate = authmanager.authenticate(auth);
        try{
        if(authenticate.isAuthenticated()){
            return "Logged in !!!";
        }
        }
        catch(Exception e) {
            return "Please Regitser First";
        }
       return "Hey there !";
   }
}
