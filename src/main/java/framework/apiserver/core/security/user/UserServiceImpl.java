package framework.apiserver.core.security.user;

import framework.apiserver.core.security.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.valueOf(id)));
    }

    @Override
    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(()->new UserNotFoundException(String.valueOf(loginId)));
    }
}
