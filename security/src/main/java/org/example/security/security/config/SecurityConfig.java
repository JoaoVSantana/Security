package org.example.security.security.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.security.enums.Perfil;
import org.example.security.security.service.AutenticacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@AllArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authenticationProvider(AutenticacaoService autenticacaoService){
        // Configura um provedor de autenticação que utiliza o serviço de autenticação personalizado
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(autenticacaoService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;
    }

    @Bean
    public SecurityContextRepository securityContextRepository(){
        // Define o repositório de contexto de segurança como HttpSessionSecurityContextRepository
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define o codificador de senha como NoOpPasswordEncoder (sem codificação)
        return NoOpPasswordEncoder.getInstance();
        // return new BCryptPasswordEncoder();
    }

    //requestMatcher().hasRole. Roles são um conjunto de permissões que um usuário pode ter.
    //Perfis de usuário que se encaixam em uma das Roles, terão as permissões associadas a ela.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity config) throws Exception {
        // Configura a cadeia de filtros de segurança
//        config.securityContext(custom -> securityContextRepository());
        config.formLogin(AbstractHttpConfigurer::disable);
        config.logout(AbstractHttpConfigurer::disable);
//        config.authorizeHttpRequests(http -> {
//            http.requestMatchers(HttpMethod.POST, "/usuario").hasAuthority("ADMIN");
//            //Recomendação do Romario >>>
//            // http.requestMatchers(HttpMethod.POST, "/usuario").hasAuthority(Perfil.ADMIN.getAuthority());
//            // <<<
//            // Permite todas as requisições POST para /auth/login
//            http.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
//            http.anyRequest().authenticated();
//        });
        config.csrf(AbstractHttpConfigurer::disable);
        //Configuração extra para JWT >>>
        config.sessionManagement(custom -> custom.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        config.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        config.cors(custom -> custom.configurationSource(corsConfigurationSource()));
        // <<<
        return config.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://top.care","http://localhost:3000", "http://teste.top.care"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH","DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//
//    @Bean
//    public UserDetailsService authenticationService() {
//        List<Usuario> usuarios = usuarioRepository.findAll();
//        List<UserDetails> userDetails = new ArrayList<>(usuarios);
//        return new InMemoryUserDetailsManager(userDetails);
//    }
}
