package rs.raf.projekat.demo.model.dto;

import lombok.Data;
import rs.raf.projekat.demo.ValidationGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentRequestDto {
    @NotNull(groups = ValidationGroup.Update.class, message = "Id can't be null!")
    private Long id;

    @NotEmpty(message = "Comment's content can't be empty!")
    private String content;

}
