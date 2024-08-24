package summer.camp.security_service.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import summer.camp.security_service.entities.AppUser;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=accountService.loadUserByUsername(username);
        if (appUser==null) throw new UsernameNotFoundException(String.format("User %s not found",username));
        //String[]roles=appUser.getRoles().stream().map(AppRole::getRole).toArray(String[]::new);
        List<SimpleGrantedAuthority>authorities=appUser.getRoles().stream().map(r->new SimpleGrantedAuthority(r.getRoleName())).toList();
        return User.withUsername(appUser.getUsername()).password(appUser.getPassword())
                .authorities(authorities).build();
                //.roles(roles).build();
    }
}
