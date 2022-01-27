package framework.apiserver.core.security.user;

import java.util.Optional;

public interface UserService {
    User getUser(Long id);
    User getUserByLoginId(String loginId);
}
