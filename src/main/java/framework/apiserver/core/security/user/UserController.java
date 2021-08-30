package framework.apiserver.core.security.user;

import framework.apiserver.core.security.jwt.JwtAuthService;
import framework.apiserver.core.security.user.exception.UserException;
import framework.apiserver.core.util.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtAuthService jwtAuthService;

    @Autowired
    public UserController(UserService userService, JwtAuthService jwtAuthService) {
        this.userService = userService;
        this.jwtAuthService = jwtAuthService;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Error> userNotFound(UserException e) {
        Error error = new Error(2001, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        return userService.getUser(Long.parseLong(id));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        jwtAuthService.logout();
        return new ResponseEntity<>("로그아웃 되었습니다", HttpStatus.OK);
    }
}
