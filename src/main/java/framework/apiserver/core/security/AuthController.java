package framework.apiserver.core.security;

import framework.apiserver.core.security.jwt.JwtAuthService;
import framework.apiserver.core.security.jwt.dto.TokenDto;
import framework.apiserver.core.security.user.User;
import framework.apiserver.core.security.user.UserService;
import framework.apiserver.core.security.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtAuthService jwtAuthService;
    private final UserService userService;

    @Autowired
    public AuthController(JwtAuthService jwtAuthService, UserService userService) {
        this.jwtAuthService = jwtAuthService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        return jwtAuthService.login(userDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto){
        return jwtAuthService.reissue(tokenDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody User user){
        User result = userService.create(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
