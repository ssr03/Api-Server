package framework.apiserver.core.security.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class RoleCd {
    public static final String ADMIN_CODE = "ROLE_ADMIN";
    public static final String USER_CODE = "ROLE_USER";
}
