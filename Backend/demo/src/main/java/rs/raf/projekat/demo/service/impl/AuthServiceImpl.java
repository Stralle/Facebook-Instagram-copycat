package rs.raf.projekat.demo.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.raf.projekat.demo.dao.UserDao;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.*;
import rs.raf.projekat.demo.security.TokenHandlerService;
import rs.raf.projekat.demo.service.AuthService;
import rs.raf.projekat.demo.service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenHandlerService tokenHandlerService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserDao userDao;

    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        if (userDao.existsByUsername(userRequestDto.getUsername())) {
            return null;
        }
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        String activateToken = tokenHandlerService.getTokenByUser(userService.findByUsername(userRequestDto.getUsername()));
        sendMail(userRequestDto.getEmail(), "Registration successful!", "Key: " + activateToken + "<br> Go to page: localhost:4200/activate to activate account.");
        return userResponseDto;
    }

    @Override
    public UserResponseDto authorizeUser(String token) {
        User user = tokenHandlerService.getUserFromToken(token);
        return userService.validateAccount(user);
    }

    @Override
    public boolean sendMail(String to, String title, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject(title);
            helper.setTo(to);
            helper.setText(body, true);
// TODO: na rafu ne moze da se posalje mejl.
//            javaMailSender.send(message);
            System.out.println(body);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userService.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (user == null) {
            System.out.println("Nije nadjen user sa mejlom " + loginRequestDto.getEmail() + " i sifrom " + loginRequestDto.getPassword());
        }
        String token = tokenHandlerService.getTokenByUser(user);

        System.out.println(user.getUsername() + " " + user.getEmail() + " " + user.getPassword() + " " + token);

        UserResponseDto userResponseDto = new UserResponseDto(user);
//        userResponseDto.setFollowers(user.getFollowers().stream().map(UserResponseDto::new).collect(Collectors.toList()));
//        userResponseDto.setFollowing(user.getFollowing().stream().map(UserResponseDto::new).collect(Collectors.toList()));
        userResponseDto.setPosts(user.getPosts().stream().map(PostResponseDto::new).collect(Collectors.toList()));
        userResponseDto.setSharedPosts(user.getSharedPosts().stream().map(PostResponseDto::new).collect(Collectors.toList()));

        userService.setOnlineState(user);

        return new LoginResponseDto(userResponseDto, token);
    }

    @Override
    public boolean logout(User user) {
        // TODO: implement logout
        return false;
    }
}
