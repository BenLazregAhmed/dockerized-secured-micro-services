package summer.camp.security_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import summer.camp.security_service.entities.AppRole;

public interface RoleRepo extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
}
