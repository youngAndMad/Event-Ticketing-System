//package danekerscode.ticketservice.config;
//
//import org.apache.http.impl.bootstrap.HttpServer;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.Collections;
//import java.util.Map;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(
//            HttpSecurity http
//    ) throws Exception {
//
//        http.authorizeHttpRequests(req -> {
//                    req
//                            .requestMatchers("/actuator/health/**").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/webjars/**").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
//                            .requestMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
//
//
//                            .anyRequest().authenticated();
//                }).sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .oauth2ResourceServer()
//                .jwt();
//
//        return http.build();
//    }
//
//    @Bean
//    public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(
//                "http://localhost:8080/auth/realms/fs/protocol/openid-connect/certs"
//        ).build();
//
//        jwtDecoder.setClaimSetConverter(new OrganizationSubClaimAdapter());
//        return jwtDecoder;
//    }
//
//    public class OrganizationSubClaimAdapter implements
//            Converter<Map<String, Object>, Map<String, Object>> {
//
//        private final MappedJwtClaimSetConverter delegate =
//                MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());
//
//        public Map<String, Object> convert(Map<String, Object> claims) {
//            Map<String, Object> convertedClaims = this.delegate.convert(claims);
//            String organization = convertedClaims.get("organization") != null ?
//                    (String) convertedClaims.get("organization") : "unknown";
//
//            convertedClaims.put("organization", organization.toUpperCase());
//
//            return convertedClaims;
//        }
//    }
//}
