package rs.raf.projekat.demo.security;

import rs.raf.projekat.demo.model.User;

public interface TokenHandlerService {

    String getTokenByUser (User user);

    User getUserFromToken (String token);

}
