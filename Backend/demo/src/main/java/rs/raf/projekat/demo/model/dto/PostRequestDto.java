package rs.raf.projekat.demo.model.dto;

import lombok.Data;
import rs.raf.projekat.demo.ValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostRequestDto {
    @NotNull(groups = ValidationGroup.Update.class, message = "ID can't be null!")
    private Long id;

    private String description;

    @NotNull(groups = ValidationGroup.Update.class, message = "Image can't be null!")
    @NotEmpty(message = "Image can't be empty!")
    private String imageBase64;
}
