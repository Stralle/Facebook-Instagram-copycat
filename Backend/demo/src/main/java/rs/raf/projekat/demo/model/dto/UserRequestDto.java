package rs.raf.projekat.demo.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.raf.projekat.demo.ValidationGroup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserRequestDto {

    @NotNull(groups = ValidationGroup.Update.class, message = "ID can't be null!")
    private Long id;

    @Email(message = "Invalid email format!")
    private String email;

    @NotNull(message = "Username can't be null!")
    @NotEmpty(message = "Username can't be empty!")
    private String username;

    @Length(min = 4, max = 16, message = "Length of password must contains between 4 and 16 characters!")
    @NotNull(message = "Password can't be null!")
    private String password;

    private String imageBase64;

}
