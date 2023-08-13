package danekerscode.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String oidcIssuerLocation;

    @Bean
    public SecurityWebFilterChain chain(
            ServerHttpSecurity http
    ) {

        http.authorizeExchange(
//                auth -> auth.anyExchange().authenticated()
                       req-> req

                                .pathMatchers("/actuator/health/**").permitAll()
                                .pathMatchers(HttpMethod.GET,"/webjars/**").permitAll()
                                .pathMatchers(HttpMethod.GET,"/ticket-service/swagger-ui/index.html").permitAll()
                                .pathMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
                                .pathMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                               .anyExchange().authenticated()
                )
                .oauth2ResourceServer().jwt();

        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders
                .fromOidcIssuerLocation(oidcIssuerLocation);
    }

}
