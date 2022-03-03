package framework.apiserver.core.security.user;

import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface UserService {
    User getUser(Long id);
    User getUserByLoginId(String loginId);

    @Transactional
    @Modifying
    User create(User user);

    @Transactional
    @Modifying
    User update(Long id, User user);

    @Transactional
    @Modifying
    boolean delete(Long id);
}
