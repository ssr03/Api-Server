package framework.apiserver.core.security.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "a_role")
@Data
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "role_cd")
    private String roleCd;

    @Column(name = "role_nm")
    private String roleNm;

    @Column(name = "role_select_cd")
    private String roleSelectCd;

    @Column(name = "role_dc")
    private String roleDc;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getAuthority() {
        return roleCd;
    }
}
