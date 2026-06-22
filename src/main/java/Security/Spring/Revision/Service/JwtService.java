package Security.Spring.Revision.Service;

import Security.Spring.Revision.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    @Autowired
    Environment env;

    String generateToken(User user){

        String jwttoken = "";

        HashMap<String, Object> claims = new HashMap<String, Object>();

        claims.put("email","saswatagl123@gmail.com");
        claims.put("Roles", List.of("ROLE_USER","ROLE_ADMIN"));

        jwttoken = Jwts.
                builder().
                claims().
                add(claims).
                subject(user.getName()).
                issuer("Saswata").
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis()+60*10*1000)).
                and().
                signWith(generateKey()).
                compact();

        return jwttoken;
    }

//    Key generateKey(){
//        String key =  env.getProperty("jwt.secret.key");
//        byte[] decode = Decoders.BASE64.decode(key);
//        SecretKey secretKey = Keys.hmacShaKeyFor(decode);
//        return secretKey;
//    }
//
//
   public  String getUsername(String jwt){
        Jws<Claims> claimsJws =
                Jwts.
                parser().
                verifyWith((SecretKey) generateKey()).
                build().
                parseSignedClaims(jwt);

        Claims payload = claimsJws.getPayload();
        String subject = payload.getSubject();

        return subject;

    }


    Key generateKey(){

        String property = env.getProperty("jwt.secret.key");
        byte[] decode = Decoders.BASE64.decode(property);
        SecretKey secretKey = Keys.hmacShaKeyFor(decode);
        return secretKey;
    }

    public boolean isvalidToken(String jwt){
        Date expiration = Jwts.parser().verifyWith((SecretKey) generateKey()).
                build().
                parseSignedClaims(jwt).
                getPayload().
                getExpiration();

        boolean isvalid =  expiration.after(new Date());

        return isvalid;
    }

}
