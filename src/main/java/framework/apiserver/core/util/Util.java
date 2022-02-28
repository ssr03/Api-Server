package framework.apiserver.core.util;


import framework.apiserver.core.security.user.User;
import framework.apiserver.core.security.user.UserRepository;
import framework.apiserver.core.security.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Util {
    private final UserRepository userRepository;

    @Autowired
    public Util(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getLoginId() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();

        String loginId = "";
        if (principal instanceof User) {
            loginId = ((User) principal).getUsername();
        } else {
            loginId = String.valueOf(principal);
        }
        return loginId;
    }

    public User getLoginUser(){
        String loginId = getLoginId();
        return userRepository.findByLoginId(loginId).orElseThrow(()->new UserNotFoundException(loginId));
    }
}
