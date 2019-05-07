package rs.raf.projekat.demo.model.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import rs.raf.projekat.demo.model.Comment;
import rs.raf.projekat.demo.model.Post;
import rs.raf.projekat.demo.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PostResponseDto {

    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private Date createdAt;

    private int totalLikes;

    private int totalComments;

    private UserResponseDto user;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentResponseDto> comments;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> likes;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedTime();
        this.comments = new ArrayList<>();
        for(Comment comm: post.getComments()) {
            this.comments.add(new CommentResponseDto(comm));
        }
        this.totalLikes = post.getLikes().size();
        this.totalComments = post.getComments().size();
        this.user = new UserResponseDto(post.getOwner());
    }

}
