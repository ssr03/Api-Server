package framework.apiserver.core.security.user;

public interface UserService {
    User getUser(Long id);
    User getUserByLoginId(String loginId);
}
