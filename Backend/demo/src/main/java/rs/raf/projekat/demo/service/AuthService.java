package rs.raf.projekat.demo.service;

import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.LoginRequestDto;
import rs.raf.projekat.demo.model.dto.LoginResponseDto;
import rs.raf.projekat.demo.model.dto.UserRequestDto;
import rs.raf.projekat.demo.model.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto register (UserRequestDto userRequestDto);
    UserResponseDto authorizeUser (String token);
    boolean sendMail (String to, String title, String body);
    LoginResponseDto login (LoginRequestDto loginRequestDto);
    boolean logout (User user);

}
