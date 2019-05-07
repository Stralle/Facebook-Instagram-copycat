package rs.raf.projekat.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import rs.raf.projekat.demo.model.Comment;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CommentResponseDto {
    private Long id;
    private String username;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
    }
}
