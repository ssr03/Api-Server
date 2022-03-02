package framework.apiserver.core.security.role;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseEntity<List<Role>> getRoleList();
}
