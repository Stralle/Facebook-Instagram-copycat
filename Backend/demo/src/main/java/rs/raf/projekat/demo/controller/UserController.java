package rs.raf.projekat.demo.controller;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.raf.projekat.demo.ValidationGroup;
import rs.raf.projekat.demo.dao.UserDao;
import rs.raf.projekat.demo.model.User;
import rs.raf.projekat.demo.model.dto.UserRequestDto;
import rs.raf.projekat.demo.model.dto.UserResponseDto;
import rs.raf.projekat.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static final String USER_ATTRIBUTE_KEY = "USER";

    @Autowired
    private UserService userService;

    @GetMapping(path = "/findAll")
    public @ResponseBody Page<UserResponseDto> findAll (@QuerydslPredicate(root = User.class, bindings = UserDao.class) Predicate predicate, Pageable pageable) {
        return userService.findAll(predicate, pageable);
    }

    @GetMapping(path = "/findById")
    public @ResponseBody UserResponseDto findById (Long id) {
        System.out.println("Found by ID " + id);
        return userService.findById(id);
    }

    @PostMapping(path = "/save")
    public @ResponseBody UserResponseDto save (@Validated(ValidationGroup.Save.class) @RequestBody UserRequestDto userRequestDto) {
        return userService.save(userRequestDto);
    }

    @PutMapping(path = "/update")
    public @ResponseBody UserResponseDto update (@Validated(ValidationGroup.Update.class) @RequestBody UserRequestDto userRequestDto) {
        return userService.update(userRequestDto);
    }

    @PostMapping(path = "/follow")
    public @ResponseBody Boolean follow (@RequestParam Long userId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return userService.follow(userId, user);
    }

    @PostMapping(path = "/unfollow")
    public @ResponseBody Boolean unfollow (@RequestParam Long userId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute(USER_ATTRIBUTE_KEY);
        return userService.unfollow(userId, user);
    }

}
