package framework.apiserver.core.security.user;

import framework.apiserver.core.security.jwt.JwtAuthService;
import framework.apiserver.core.security.role.RoleCd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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

    @PreAuthorize("hasAuthority('"+ RoleCd.ADMIN_CODE +"')")
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userService.getUser(Long.parseLong(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        jwtAuthService.logout();
        return new ResponseEntity<>("로그아웃 되었습니다", HttpStatus.OK);
    }
}
