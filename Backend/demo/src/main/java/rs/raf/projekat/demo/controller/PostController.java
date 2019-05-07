package rs.raf.projekat.demo.controller;


import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.raf.projekat.demo.ValidationGroup;
import rs.raf.projekat.demo.dao.PostDao;
import rs.raf.projekat.demo.model.Post;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.CommentRequestDto;
import rs.raf.projekat.demo.model.dto.PostRequestDto;
import rs.raf.projekat.demo.model.dto.PostResponseDto;
import rs.raf.projekat.demo.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    private static final String USER_ATTRIBUTE_KEY = "USER";

    @Autowired
    private PostService postService;

    @GetMapping(path = "/findAll")
    public Page<PostResponseDto> findAll (@QuerydslPredicate(root = Post.class, bindings = PostDao.class) Predicate predicate, Pageable pageable) {
        return postService.findAll(predicate, pageable);

    }

    @GetMapping(path = "/findById")
    public PostResponseDto findById (Long id) {
        return postService.findById(id);
    }

    @PostMapping(path = "/save")
    public PostResponseDto save (@Validated(ValidationGroup.Save.class) @RequestBody PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return postService.save(postRequestDto, user);
    }

    @PutMapping(path = "/update")
    public PostResponseDto update (@Validated(ValidationGroup.Update.class) @RequestBody PostRequestDto postRequestDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return postService.update(postRequestDto, user);
    }

    @PostMapping(path = "/addCommmentToPost")
    public PostResponseDto addCommentToPost (@Validated(ValidationGroup.Save.class) @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return postService.addCommentToPost(commentRequestDto, user);
    }

    @PostMapping(path = "/addLikeToPost")
    public PostResponseDto addLikeToPost (Long postId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return postService.addLikeToPost(postId, user);
    }

    @PostMapping(path = "/dislikePost")
    public PostResponseDto dislikePost(Long postId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return postService.dislikePost(postId, user);
    }

    @PostMapping(path = "/sharePost")
    public PostResponseDto sharePost (Long postId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return postService.sharePost(postId, user);
    }

    @GetMapping(path = "/findFollowingPosts")
    public @ResponseBody List<PostResponseDto> findTop50 (HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        List<Long> ids = user.getFollowing().stream().map(User::getId).collect(Collectors.toList());
        System.out.println("Size of people that user is following: " + ids.size());
        return postService.findTop50ByUsers(ids);
    }

}

