package summer.camp.security_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import summer.camp.security_service.entities.AppUser;

public interface UserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
