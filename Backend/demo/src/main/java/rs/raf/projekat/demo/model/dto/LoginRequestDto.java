package rs.raf.projekat.demo.model.dto;

import lombok.Data;
import rs.raf.projekat.demo.ValidationGroup;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequestDto {

    @NotEmpty(groups = ValidationGroup.Login.class, message = "Email must not be empty!")
    private String email;

    @NotEmpty(groups = ValidationGroup.Login.class, message = "Password must not be empty!")
    private String password;
}
