package rs.raf.projekat.demo.service.impl;


import com.querydsl.core.types.Predicate;
import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.projekat.demo.dao.PostDao;
import rs.raf.projekat.demo.dao.UserDao;
import rs.raf.projekat.demo.model.Comment;
import rs.raf.projekat.demo.model.Post;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.CommentRequestDto;
import rs.raf.projekat.demo.model.dto.PostRequestDto;
import rs.raf.projekat.demo.model.dto.PostResponseDto;
import rs.raf.projekat.demo.service.PostService;
import rs.raf.projekat.demo.util.ResourceHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResourceHandler resourceHandler;

    @Override
    public Page<PostResponseDto> findAll(Predicate predicate, Pageable pageable) {
        Page<Post> posts = postDao.findAll(predicate, pageable);
        return new PageImpl<>(posts.stream().map(PostResponseDto::new).collect(Collectors.toList()), pageable, posts.getTotalElements());
    }

    @Override
    public PostResponseDto findById(Long id) {
        Optional<Post> optionalPost = postDao.findById(id);
        if(!optionalPost.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        return new PostResponseDto(optionalPost.get());
    }

    @Override
    public PostResponseDto save(PostRequestDto postRequestDto, User user) {
        String imageUrl = null;
        if(postRequestDto.getImageBase64() != null) {
            imageUrl = resourceHandler.uploadImage(postRequestDto.getImageBase64(),  user.getUsername());
        }
        Post post = Post.builder()
                .description(postRequestDto.getDescription())
                .createdTime(new Date())
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .owner(user)
                .sharedUsers(new ArrayList<>())
                .imageUrl(imageUrl).build();
        postDao.save(post);
        return new PostResponseDto(post);
    }

    @Override
    public PostResponseDto update(PostRequestDto postRequestDto, User user) {
        Optional<Post> optionalPost = postDao.findById(postRequestDto.getId());
        if(!optionalPost.isPresent() || !optionalPost.get().getOwner().getId().equals(user.getId())) {
            // TODO: Handle exception
            return null;
        }
        Post post = optionalPost.get();
        post.setDescription(postRequestDto.getDescription());
        postDao.save(post);
        return new PostResponseDto(post);
    }

    @Override
    public PostResponseDto addCommentToPost(CommentRequestDto commentRequestDto, User user) {
        Optional<Post> optionalPost = postDao.findById(commentRequestDto.getId());
        if(!optionalPost.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        Post post = optionalPost.get();
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .post(post)
                .user(user)
                .createdTime(new Date()).build();
        System.out.println("Komentar: " + comment.getContent());

        post.getComments().add(comment);
        postDao.save(post);
        return new PostResponseDto(post);
    }

    @Override
    public PostResponseDto addLikeToPost(Long postId, User user) {
        Optional<Post> optionalPost = postDao.findById(postId);
        if(!optionalPost.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        Post post = optionalPost.get();
        post.getLikes().add(user);
        postDao.save(post);
        return new PostResponseDto(post);
    }

    @Override
    public PostResponseDto dislikePost(Long postId, User user) {
        Optional<Post> optionalPost = postDao.findById(postId);
        if(!optionalPost.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        Post post = optionalPost.get();
        post.getLikes().remove(user.getUsername());
        postDao.save(post);
        return new PostResponseDto(post);
    }

    @Override
    public PostResponseDto sharePost(Long postId, User user) {
        Optional<Post> optionalPost = postDao.findById(postId);
        if(!optionalPost.isPresent()) {
            // TODO: Handle exception
        }
        Post post = optionalPost.get();
        user.getSharedPosts().add(post);
        userDao.save(user);
        return new PostResponseDto(post);
    }

    @Override
    public List<PostResponseDto> findTop50ByUsers(List<Long> ids) {
        List<PostResponseDto> posts = postDao.findTop50ByOwner_IdInOrderByCreatedTime(ids).stream().map(PostResponseDto::new).collect(Collectors.toList());
        for (PostResponseDto post: posts) {
            System.out.println("POST: " + post.getId() + " url: " + post.getImageUrl());
        }
        return posts;
    }

}