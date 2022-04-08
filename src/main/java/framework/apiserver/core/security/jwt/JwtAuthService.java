package framework.apiserver.core.security.jwt;

import framework.apiserver.core.security.jwt.dto.TokenDto;
import framework.apiserver.core.security.user.dto.UserDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

public interface JwtAuthService {
    @Transactional
    @Modifying
    TokenDto login(UserDto userDto);
    @Transactional
    @Modifying
    TokenDto reissue(TokenDto tokenDto);
    @Transactional
    @Modifying
    void logout();
}
