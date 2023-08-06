package danekerscode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http
    ) throws Exception {

        http.authorizeHttpRequests(
                        req -> {
                            req
                                    .requestMatchers(HttpMethod.POST, "api/v1/user").permitAll()
                                    .requestMatchers(HttpMethod.GET, "api/v1/user").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }
}
