package framework.apiserver.core.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<List<Role>> getRoleList() {
        return roleService.getRoleList();
    }
}
