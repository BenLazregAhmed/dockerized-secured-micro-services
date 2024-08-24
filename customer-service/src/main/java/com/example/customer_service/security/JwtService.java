package com.example.customer_service.security;

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
