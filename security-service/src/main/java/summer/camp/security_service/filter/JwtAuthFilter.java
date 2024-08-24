package summer.camp.security_service.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
        if (authenticationManager==null)
            throw  new RuntimeException("MANAGER IS NULL !!!");
        System.out.println("ATTEMPT !!!");
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user= (User) authResult.getPrincipal();
        Algorithm algorithm=Algorithm.HMAC256(JWTUtil.SECRET);
        String jwtAccessToken= jwtService.createAccessToken(user.getUsername(),user.getAuthorities(),request,algorithm);
        String jwtRefreshToken= jwtService.createRefreshToken(user.getUsername(),request,algorithm);
        Map<String ,String> idToken = jwtService.generateIdToken(jwtAccessToken,jwtRefreshToken);
        response.setContentType("application/json");
        System.out.println("SUCCESS !!!");
        new ObjectMapper().writeValue(response.getOutputStream(),idToken);
    }
}
