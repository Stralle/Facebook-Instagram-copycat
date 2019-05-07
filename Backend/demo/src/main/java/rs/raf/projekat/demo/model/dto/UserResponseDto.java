package rs.raf.projekat.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import rs.raf.projekat.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserResponseDto {

    private Long id;
    private String email;
    private String username;
    private String profileImageUrl;
    private Boolean validated;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UserResponseDto> followers = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UserResponseDto> following = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PostResponseDto> posts = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PostResponseDto> sharedPosts = new ArrayList<>();

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.profileImageUrl = user.getProfileImageUrl();
        this.validated = user.getValidated();
    }

}