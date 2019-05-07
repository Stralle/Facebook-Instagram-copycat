package rs.raf.projekat.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.raf.projekat.demo.ValidationGroup;
import rs.raf.projekat.demo.dao.UserDao;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.LoginRequestDto;
import rs.raf.projekat.demo.model.dto.LoginResponseDto;
import rs.raf.projekat.demo.model.dto.UserRequestDto;
import rs.raf.projekat.demo.model.dto.UserResponseDto;
import rs.raf.projekat.demo.security.TokenHandlerService;
import rs.raf.projekat.demo.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private static final String USER_KEY = "USER";

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenHandlerService tokenHandlerService;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/login")
    public @ResponseBody
    LoginResponseDto login (@Validated(ValidationGroup.Login.class) @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        if (response != null) {
            System.out.println("Nije null!");
        }
        else {
            System.out.println("Null je !");
        }
        return response;
    }

    @PostMapping(path = "/register")
    public @ResponseBody
    UserResponseDto register (@Validated(ValidationGroup.Save.class) @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto response = authService.register(userRequestDto);
        if (response != null) {
            System.out.println("Nije null!");
        }
        else {
            System.out.println("Null je !");
        }
        return response;
    }

    @PostMapping(path = "/logout")
    public Boolean logout (HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_KEY);
        return authService.logout(user);
    }

    @PostMapping(path = "/activateAccount")
    public UserResponseDto activateAccount(@RequestParam(name = "key") String secKey) {
        return authService.authorizeUser(secKey);
    }

    @GetMapping(path = "/token")
    public @ResponseBody String createToken (Long userId) {
        User user = userDao.findById(userId).get();
        return tokenHandlerService.getTokenByUser(user);
    }

}
