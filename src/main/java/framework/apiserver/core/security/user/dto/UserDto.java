package framework.apiserver.core.security.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = -1255150713846954145L;
    Long id;
    String loginId;
    String loginPw;
    String name;
    boolean enableFlag;
    List<String> roles;
    String role;
    String token;

    public UserDto(String loginId, String loginPw){
        this.loginId = loginId;
        this.loginPw = loginPw;
    }
}
