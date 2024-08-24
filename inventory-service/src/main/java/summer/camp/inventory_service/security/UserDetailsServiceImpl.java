package summer.camp.inventory_service.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import summer.camp.inventory_service.service.SecurityRestClient;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private SecurityRestClient securityRestClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return securityRestClient.getUserDetails(username);
    }
}
