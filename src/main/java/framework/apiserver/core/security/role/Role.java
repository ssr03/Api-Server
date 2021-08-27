package framework.apiserver.core.security.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "a_role")
@Data
public class Role {
    @Id
    @Column(name = "role_cd")
    private String roleCd;

    @Column(name = "role_nm")
    private String roleNm;

    @Column(name = "role_select_cd")
    private String roleSelectCd;

    @Column(name = "role_dc")
    @JsonIgnore
    private String roleDc;
}
