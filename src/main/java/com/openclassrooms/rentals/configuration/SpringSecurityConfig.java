package com.openclassrooms.rentals.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final RsaKeyProperties rsaKeys;
    
    public SpringSecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }
    
    
    /**
     * Configure la chaîne de filtrage de sécurité de l'application.
     * 
     * @param http : Objet HttpSecurity à configurer
     * @return : SecurityFilterChain configuré
     * @throws Exception si une erreur survient lors de la configuration.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) 
                .authorizeHttpRequests(auth -> 
                auth.antMatchers(
                "/api/**",
                "/api/auth/register/**",
                "/api/auth/login/**",
                "/v2/api-docs/**", // Endpoint OpenAPI
                "/swagger-ui/**",  // Swagger UI
                "/swagger-resources/**"
                
                )
                .permitAll()
                .anyRequest()
                .authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) //Enable Jwt-encoded bearer token support
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .build();
    }
    
    /**
     * Configure le bean JwtDecoder pour le décodage du jeton JWT
     *
     * @return Le JwtDecoder configuré.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }
    
    /**
     * Configure le bean JwtEncoder pour l'encodage du jeton JWT.
     *
     * @return Le JwtEncoder configuré.
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    
    /**
     * Configure le bean BCryptPasswordEncoder pour l'encodage des mots de passe.
     *
     * @return Le BCryptPasswordEncoder configuré.
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}