package summer.camp.security_service.web;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import summer.camp.security_service.entities.AppRole;
import summer.camp.security_service.entities.AppUser;
import summer.camp.security_service.filter.JWTUtil;
import summer.camp.security_service.filter.JwtService;
import summer.camp.security_service.service.AccountService;
import summer.camp.security_service.service.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AccountRestController {
    AccountService accountService;
    UserDetailsServiceImpl userDetailsService;
    JwtService jwtService;
    @GetMapping(path = "/users")
    public List<AppUser> appUsers()
    {
        return accountService.usersList();
    }
    @PostMapping("/users")
    public AppUser addNewUser(@RequestBody AppUser user)
    {
        return accountService.addNewUser(user);
    }
    @PostMapping("/roles")
    public AppRole addNewRole(@RequestBody AppRole role)
    {
        return accountService.addNewRole(role);
    }
    @PostMapping("/roleUser")
    public void addRoleToUser(@RequestBody RoleUser roleUser)
    {
        accountService.addRoleToUser(roleUser.getUsername(), roleUser.getRoleName());
    }
    @GetMapping("/userDetails/{username}")
    public UserDetails getUserDetails(@PathVariable String username)
    {
        return userDetailsService.loadUserByUsername(username);
    }
    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authToken=request.getHeader(JWTUtil.AUTH_HEADER);
        if (jwtService.isTokenValid(authToken))
        {
            try {
                String refreshToken=authToken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm=Algorithm.HMAC256(JWTUtil.SECRET);
                DecodedJWT decodedJWT= jwtService.verifyAnDecode(refreshToken,algorithm);
                String username=decodedJWT.getSubject();
                AppUser appUser=accountService.loadUserByUsername(username);
                List<GrantedAuthority> authorities=appUser.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
                String jwtAccessToken= jwtService.createAccessToken(appUser.getUsername(),authorities,request,algorithm);
                Map<String ,String> idToken = jwtService.generateIdToken(jwtAccessToken,refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);
            }
            catch (Exception e)
            {
                response.setHeader("error-message",e.getMessage());
                response.sendError(403);
            }
        }
        else {
            throw new RuntimeException("REFRESH TOKEN REQUIRED !!!");
        }
    }
}
@Getter@Setter
class RoleUser{
    private String username;
    private String roleName;
}
