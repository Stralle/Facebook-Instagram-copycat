package rs.raf.projekat.demo.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.UserRequestDto;
import rs.raf.projekat.demo.model.dto.UserResponseDto;

public interface UserService {
    Page<UserResponseDto> findAll (Predicate predicate, Pageable pageable);

    UserResponseDto findById (Long id);

    UserResponseDto save (UserRequestDto userRequestDto);

    UserResponseDto update (UserRequestDto userRequestDto);

    User findByEmailAndPassword (String email, String password);

    User findByUsername (String username);

    UserResponseDto validateAccount (User user);

    boolean setOnlineState (User user);

    boolean follow (Long userId, User user);

    boolean unfollow (Long userId, User user);
}
