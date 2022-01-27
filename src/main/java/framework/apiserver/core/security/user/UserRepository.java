package framework.apiserver.core.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.attribute1 =:token WHERE u.loginId = :loginId ")
    int updatePushToken(@Param("token") String token, @Param("loginId") String loginId);
}
