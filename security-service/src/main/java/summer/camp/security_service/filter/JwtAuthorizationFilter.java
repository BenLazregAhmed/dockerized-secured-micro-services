package summer.camp.security_service.filter;

import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthorizationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/refreshToken"))
        {
            filterChain.doFilter(request,response);
        }
        else {
            String jwtAuthorizationToken=request.getHeader(JWTUtil.AUTH_HEADER);
            if(jwtService.isTokenValid(jwtAuthorizationToken))
            {
                try {
                    String jwt=jwtAuthorizationToken.substring(JWTUtil.PREFIX.length());
                    Algorithm algorithm=Algorithm.HMAC256(JWTUtil.SECRET);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=jwtService.generateUsernamePasswordAuthenticationToken(jwt,algorithm);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request,response);
                }
                catch (Exception e)
                {
                    response.setHeader("error-message",e.getMessage());
                    response.sendError(403);
                }
            }
            else {
                filterChain.doFilter(request,response);
            }
        }
    }
}
