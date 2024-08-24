package summer.camp.security_service.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import summer.camp.security_service.entities.AppRole;
import summer.camp.security_service.entities.AppUser;
import summer.camp.security_service.repositories.RoleRepo;
import summer.camp.security_service.repositories.UserRepo;

import java.util.List;
@AllArgsConstructor
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    UserRepo userRepo;
    RoleRepo roleRepo;
    PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public AppRole addNewRole(AppRole role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user=userRepo.findByUsername(username);
        AppRole role=roleRepo.findByRoleName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> usersList() {
        return userRepo.findAll();
    }
}
