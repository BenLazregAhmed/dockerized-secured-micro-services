package summer.camp.security_service.service;

import summer.camp.security_service.entities.AppRole;
import summer.camp.security_service.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser user);
    AppRole addNewRole(AppRole role);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser>usersList();
}
