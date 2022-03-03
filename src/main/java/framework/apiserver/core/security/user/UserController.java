package framework.apiserver.core.security.user;

import framework.apiserver.core.security.jwt.JwtAuthService;
import framework.apiserver.core.security.role.RoleCd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        jwtAuthService.logout();
        return new ResponseEntity<>("로그아웃 되었습니다", HttpStatus.OK);
    }

    @PostAuthorize("returnObject.body.loginId == principal or hasRole('"+RoleCd.ADMIN_CODE+"')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostAuthorize("returnObject.body.loginId == principal or hasRole('"+RoleCd.ADMIN_CODE+"')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User result = userService.update(id, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured(RoleCd.ADMIN_CODE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        boolean result = userService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
