package rs.raf.projekat.demo.service.impl;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.projekat.demo.dao.UserDao;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.PostResponseDto;
import rs.raf.projekat.demo.model.dto.UserRequestDto;
import rs.raf.projekat.demo.model.dto.UserResponseDto;
import rs.raf.projekat.demo.service.UserService;
import rs.raf.projekat.demo.util.ResourceHandler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResourceHandler resourceHandler;

    @Override
    public Page<UserResponseDto> findAll(Predicate predicate, Pageable pageable) {
        Page<User> users = userDao.findAll(predicate, pageable);
        return new PageImpl<>(users.stream().map(UserResponseDto::new).collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    @Override
    public UserResponseDto findById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if(!optionalUser.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        User user = optionalUser.get();
        UserResponseDto userResponseDto = new UserResponseDto(user);
        userResponseDto.setPosts(user.getPosts().stream().map(PostResponseDto::new).collect(Collectors.toList()));
        userResponseDto.setSharedPosts(user.getSharedPosts().stream().map(PostResponseDto::new).collect(Collectors.toList()));
        userResponseDto.setFollowers(user.getFollowers().stream().map(UserResponseDto::new).collect(Collectors.toList()));
        userResponseDto.setFollowing(user.getFollowing().stream().map(UserResponseDto::new).collect(Collectors.toList()));

        return userResponseDto;
    }

    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .username(userRequestDto.getUsername())
                .validated(false)
                .followers(new ArrayList<>())
                .following(new ArrayList<>())
                .posts(new ArrayList<>())
                .sharedPosts(new ArrayList<>())
                .password(userRequestDto.getPassword()).build();
        if(userRequestDto.getImageBase64() != null) {
            String profileImageUrl = resourceHandler.uploadImage(userRequestDto.getImageBase64(), user.getUsername());
            user.setProfileImageUrl(profileImageUrl);
        }
        userDao.save(user);
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto update(UserRequestDto userRequestDto) {
        Optional<User> optionalUser = userDao.findById(userRequestDto.getId());
        if(!optionalUser.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        User user = optionalUser.get();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());

        if(userRequestDto.getImageBase64() != null) {
            String profileImageUrl = resourceHandler.uploadImage(userRequestDto.getImageBase64(), user.getUsername());
            user.setProfileImageUrl(profileImageUrl);
        }
        userDao.save(user);
        return new UserResponseDto(user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> optionalUser = userDao.findByEmail(email);
        if(!optionalUser.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        User user = optionalUser.get();
        if(!password.equals(user.getPassword()) || !user.getValidated()) {
            // TODO: Handle exception
            System.out.println("Ne podudaraju se sifre ili korisnik nije validiran");
            return null;
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> optionalUser = userDao.findByUsername(username);
        if(!optionalUser.isPresent()) {
            // TODO: Handle exception
            return null;
        }
        return optionalUser.get();
    }

    @Override
    public UserResponseDto validateAccount(User user) {
        if(user.getValidated()) {
            // TODO: Handle exception
            return null;
        }
        user.setValidated(true);
        userDao.save(user);
        return new UserResponseDto(user);
    }

    @Override
    public boolean setOnlineState(User user) {
        userDao.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean follow(Long userId, User follower) {
        Optional<User> optionalUser = userDao.findById(userId);
        if(!optionalUser.isPresent()) {
            // TODO: Handle exception
            return false;
        }
        User user = optionalUser.get();
        follower.getFollowing().add(user);
        userDao.save(follower);

//        user.getFollowers().add(follower);
//        TODO: Create follower relation.
//        userDao.save(user);

        return true;
    }

    @Override
    public boolean unfollow(Long userId, User follower) {
        Optional<User> optionalUser = userDao.findById(userId);
        if(!optionalUser.isPresent()) {
            // TODO: Handle exception
            return false;
        }
        User user = optionalUser.get();
        user.getFollowers().remove(follower);
        follower.getFollowing().remove(user);
        // TODO: remove follower relation
        userDao.save(user);
        userDao.save(follower);

        System.out.println("User " + follower.getId() + " vise ne prati usera " + user.getId());

        return true;
    }

}
