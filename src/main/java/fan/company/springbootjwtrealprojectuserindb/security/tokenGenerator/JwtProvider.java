package fan.company.springbootjwtrealprojectuserindb.security.tokenGenerator;

import fan.company.springbootjwtrealprojectuserindb.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    private String keyForToken = "StrongPassword";
    private long expireTime = 1000 * 60 * 60 * 24;

    public String generateToken(String username, Role roles) {

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, keyForToken)
                .compact();
        return token;
    }

    public String getUsernameFromToken(String token) {
        try {

            String username = Jwts
                    .parser()
                    .setSigningKey(keyForToken)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
