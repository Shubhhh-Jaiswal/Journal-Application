package net.engineeringdigest.journalApp.Utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtility {

    @Autowired
    private UserRepository userRepository;
    private final static String SECRET_KEY = "a-string-secret-at-least-256-bits-long";

    public String getToken(String username){
        Map<String, Object> claims= new HashMap<>();
        return createToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ", "JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean  validateToken(String token, String username){
//       User user = userRepository.findByUserName(getUsername(token));
//        if(user!=null && !isExpired(token)){
//            return true;
//        }else{
//            return false;
//        }
        String user = extractDetail(token).getSubject();
        return (username.equals(user) && !isExpired(token));
    }


    public String getUsername(String token){
        String username = extractDetail(token).getSubject();
        return username;
    }

    private Claims extractDetail(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims( token)
                .getPayload();
    }

    private boolean isExpired(String token){
        Claims claim = extractDetail(token);
        return claim.getExpiration().before(new Date());
    }


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


}
