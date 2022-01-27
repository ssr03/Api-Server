package framework.apiserver.core.security.jwt;

import framework.apiserver.core.security.user.User;
import framework.apiserver.core.security.user.exception.UserNotFoundException;
import framework.apiserver.core.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .orElseThrow(()->new UserNotFoundException(username));
    }
}
