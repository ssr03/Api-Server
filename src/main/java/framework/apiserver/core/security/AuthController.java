package framework.apiserver.core.security;

import framework.apiserver.core.security.jwt.JwtAuthService;
import framework.apiserver.core.security.jwt.dto.TokenDto;
import framework.apiserver.core.security.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtAuthService jwtAuthService;

    @Autowired
    public AuthController(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        return jwtAuthService.login(userDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenDto tokenDto){
        return jwtAuthService.reissue(tokenDto);
    }
}
