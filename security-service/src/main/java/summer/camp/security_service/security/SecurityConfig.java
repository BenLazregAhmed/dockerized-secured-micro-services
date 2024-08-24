package summer.camp.security_service.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import summer.camp.security_service.filter.JwtAuthFilter;
import summer.camp.security_service.filter.JwtAuthorizationFilter;
import summer.camp.security_service.filter.JwtService;
import summer.camp.security_service.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    UserDetailsServiceImpl userDetailsService;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;
    @Bean
    AuthenticationManager authenticationManager()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return  new ProviderManager(daoAuthenticationProvider);

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtAuthorizationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new JwtAuthFilter(authenticationManager(),jwtService));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**","/refreshToken","/userDetails/**","/actuator/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/users/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/**").hasAuthority("USER")
                .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.userDetailsService(userDetailsService);


        return http.build();
    }

}
