package summer.camp.security_service.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    public boolean isTokenValid(String jwtAuthorizationToken)
    {
        return jwtAuthorizationToken!=null&&jwtAuthorizationToken.startsWith(JWTUtil.PREFIX);
    }

    public String createAccessToken(String username, Collection<GrantedAuthority> authorities, HttpServletRequest request, Algorithm algorithm)
    {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.ofEpochSecond(JWTUtil.EXPIRES_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String createRefreshToken(String username, HttpServletRequest request,Algorithm algorithm)
    {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.ofEpochSecond(JWTUtil.EXPIRES_REFRESH_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }

    public Map<String ,String> generateIdToken(String jwtAccessToken,String jwtRefreshToken)
    {
        Map<String ,String> idToken = new HashMap<>();
        idToken.put("AccessToken",jwtAccessToken);
        idToken.put("RefreshToken",jwtRefreshToken);
        return idToken;
    }
    public UsernamePasswordAuthenticationToken generateUsernamePasswordAuthenticationToken(String jwt,Algorithm algorithm)
    {
        DecodedJWT decodedJWT= verifyAnDecode(jwt, algorithm);
        String username=decodedJWT.getSubject();
        String []roles=decodedJWT.getClaim("roles").asArray(String.class);
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        for (String role:roles)
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }
         return new UsernamePasswordAuthenticationToken(username,null,authorities);
    }
    public DecodedJWT verifyAnDecode(String token,Algorithm algorithm)
    {
        JWTVerifier jwtVerifier= JWT.require(algorithm).build();
        return jwtVerifier.verify(token);
    }

}
