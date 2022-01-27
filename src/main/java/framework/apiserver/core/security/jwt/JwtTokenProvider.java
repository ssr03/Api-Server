package framework.apiserver.core.security.jwt;

import framework.apiserver.core.security.jwt.dto.TokenDto;
import framework.apiserver.core.security.jwt.exception.JwtTokenUnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider{
    private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60;// 1시간 토근 유지
    private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7;//Refresh Token 7일 유지
    private static final String BEARER_TYPE = "bearer";
    private static final String AUTHORITIES_KEY = "auth";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private Key key;

    @Bean
    public void JwtTokenProvider(){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //Access Token 생성
        Date accessTokenExpireIn = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpireIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS256)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new TokenDto(BEARER_TYPE, accessToken, refreshToken, accessTokenExpireIn.getTime());
    }

    public Authentication getAuthentication(String accessToken){
        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null){
            throw new JwtTokenUnauthorizedException();
        }

        //클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities
                = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //UserDetails객체를 만들어 authentication리턴
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), authorities);
    }

    public Claims parseClaims(String accessToken){
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
