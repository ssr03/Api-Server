package framework.apiserver.core.security.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<List<Role>> getRoleList() {
        List<Role> list = roleRepository.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
