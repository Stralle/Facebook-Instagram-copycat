package rs.raf.projekat.demo.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.CommentRequestDto;
import rs.raf.projekat.demo.model.dto.PostRequestDto;
import rs.raf.projekat.demo.model.dto.PostResponseDto;

import java.util.List;

public interface PostService {

    Page<PostResponseDto> findAll(Predicate predicate, Pageable pageable);

    PostResponseDto findById (Long id);

    PostResponseDto save (PostRequestDto postRequestDto, User user);

    PostResponseDto update (PostRequestDto postRequestDto, User user);

    PostResponseDto addCommentToPost (CommentRequestDto commentRequestDto, User user);

    PostResponseDto addLikeToPost (Long postId, User user);

    PostResponseDto dislikePost(Long postId, User user);

    PostResponseDto sharePost (Long postId, User user);

    List<PostResponseDto> findTop50ByUsers (List<Long> ids);
}
