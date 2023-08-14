package danekerscode.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwk.issuer-uri}")
    private String oidcIssuerLocation;

    @Bean
    public SecurityWebFilterChain chain(
            ServerHttpSecurity http
    ) {
        http.authorizeExchange(
                        auth -> auth
                                .pathMatchers(HttpMethod.POST, "/event/**").hasRole("admin")
                                .pathMatchers(HttpMethod.DELETE, "/event/**").hasRole("admin")
                                .pathMatchers(HttpMethod.PUT,"api/v1/event").permitAll()
                                .anyExchange().authenticated()
                )
                .cors().configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                }).and()
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2ResourceServer().jwt();


        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders
                .fromOidcIssuerLocation(oidcIssuerLocation);
    }

}
