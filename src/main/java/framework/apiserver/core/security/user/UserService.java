package framework.apiserver.core.security.user;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(Long id);
    Optional<User> getUserByLoginId(String loginId);
}
