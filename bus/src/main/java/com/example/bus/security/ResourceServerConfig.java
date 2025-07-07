package com.example.bus.security;

import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.bus.utils.UtilClass;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig{

	private static final String SECRET_KEY = "f28ef29ff32e48bad5f32a4b6762a45db49e9665506e7d89947d81fcb24f6ab5";
	
	@Autowired
	UtilClass utilClass;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		System.out.println("Hi");

		http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        		.requestMatchers("/auth/**").permitAll()
                .requestMatchers("/searchFilters/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/paymentCallbackUrl/**").permitAll()
                .requestMatchers("/traveller/**").authenticated()
                .requestMatchers("/userId/**").authenticated()
                .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt()
        );

    return http.build();
    }
	
	@Bean
	public JwtDecoder jwtDecoder() {
		byte[] decodedKey = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
		SecretKey key = new SecretKeySpec(decodedKey, "HmacSHA256");
		return NimbusJwtDecoder.withSecretKey(key).build();
	}
	
//	@Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("secretkey123"); // Use the same key as your auth server
//        return converter;
//    }
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
	
	
}
