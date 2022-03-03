package framework.apiserver.core.security.user;

import framework.apiserver.core.security.role.Role;
import framework.apiserver.core.security.role.RoleCd;
import framework.apiserver.core.security.user.exception.UserException;
import framework.apiserver.core.security.user.exception.UserNotFoundException;
import framework.apiserver.core.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Util util;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(String.valueOf(id)));
    }

    @Override
    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(()->new UserNotFoundException(String.valueOf(loginId)));
    }

    @Override
    public User create(User user) {
        userRepository.findByLoginId(user.getUsername()).ifPresent(u->{
            throw new UserException("username : " +u.getLoginId()+ "는 이미 등록된 계정입니다.");
        });

        user.setLoginPw(passwordEncoder.encode(user.getLoginPw()));
        user.setCreatedBy(user.getUsername());
        user.setModifiedBy(user.getUsername());

        List<Role> roles = user.getRoles();

        if(roles == null)roles = new ArrayList<>();
        if(roles.isEmpty()){
            Role role = new Role();
            role.setRoleCd(RoleCd.USER_CODE);
            roles.add(role);
            user.setRoles(roles);
        }
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        String loginId = util.getLoginId(); //로그인한 사용자
        String loginPw = user.getLoginPw();

        User responseUser = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(user.getLoginId()));

        if(StringUtils.hasText(loginPw)) responseUser.setLoginPw(passwordEncoder.encode(loginPw));
        responseUser.setName(user.getName());
        responseUser.setEnableFlag(user.isEnableFlag());
        responseUser.setAttribute1(user.getAttribute1());
        responseUser.setAttribute2(user.getAttribute2());
        responseUser.setAttribute3(user.getAttribute3());
        responseUser.setAttribute4(user.getAttribute4());
        responseUser.setAttribute5(user.getAttribute5());
        responseUser.setModifiedBy(loginId);

        if(user.getRoles()!=null)responseUser.setRoles(user.getRoles());

        userRepository.save(responseUser);

        return responseUser;
    }

    @Override
    public boolean delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(String.valueOf(id)));

        userRepository.delete(user);

        return true;
    }
}
