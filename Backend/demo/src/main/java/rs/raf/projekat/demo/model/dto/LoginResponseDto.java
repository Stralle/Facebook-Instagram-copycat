package rs.raf.projekat.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class LoginResponseDto {

    private UserResponseDto user;

    private String token;

}
