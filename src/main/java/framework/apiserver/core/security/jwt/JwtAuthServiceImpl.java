package framework.apiserver.core.security.jwt;

import framework.apiserver.core.security.jwt.dto.TokenDto;
import framework.apiserver.core.security.jwt.exception.JwtTokenIncorrectException;
import framework.apiserver.core.security.jwt.exception.JwtTokenInvalidException;
import framework.apiserver.core.security.jwt.exception.JwtTokenNotFoundException;
import framework.apiserver.core.security.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtAuthServiceImpl implements JwtAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenDto login(UserDto userDto) {
        // 1. Login Id/password기반으로 AuthenticationToken생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getLoginId(), userDto.getLoginPw());

        // 2. 검증(사용자 비밀번호 체크)
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증 정보 기반으로 JWT토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 4.RefreshToken저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        
        //5.토큰 발급
        return tokenDto;
    }

    @Override
    public TokenDto reissue(TokenDto tokenDto) {
        // 1.Refresh Token 검증
        if(!jwtTokenProvider.validateToken(tokenDto.getRefreshToken())){
            throw new JwtTokenInvalidException();
        }

        // 2. Access Token 에서 loginId 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenDto.getAccessToken());

        // 3. 저장소에서 loginId기반으로 Refresh Token값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(JwtTokenNotFoundException::new);

        // 4. Refresh Token 일치하는지 검사
        if(!refreshToken.getValue().equals(tokenDto.getRefreshToken())){
            throw new JwtTokenIncorrectException();
        }

        // 5. 새로운 토큰 생성
        TokenDto newTokenDto = jwtTokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(newTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 7.토큰 발급
        return newTokenDto;
    }

    @Override
    public void logout() {
        // Refresh Token 삭제
        Object principal = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String loginId = String.valueOf(principal);
        try {
            refreshTokenRepository.deleteById(loginId);
        }catch(EmptyResultDataAccessException e){
            throw new JwtTokenNotFoundException();
        }
    }
}
