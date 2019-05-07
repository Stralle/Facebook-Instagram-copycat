package rs.raf.projekat.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.projekat.demo.dao.UserDao;
import rs.raf.projekat.demo.model.User;

@Service
public class TokenHandlerServiceImpl implements TokenHandlerService {

    private static final String SECURITY_KEY = "pluskoski";

    @Autowired
    private UserDao userDao;

    @Override
    public String getTokenByUser(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY).compact();
    }

    @Override
    public User getUserFromToken(String token) {
        try {
            if(token == null || token.equals("")) return null;
            Claims tokenClaims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
            String email = tokenClaims.getSubject();
            return userDao.findByEmail(email).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
