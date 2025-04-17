package bt.edu.gcit.usermicroservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Arrays;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ShopmeSecurityConfig {
    public ShopmeSecurityConfig() {
        System.out.println("ShopmeSecurityConfig created");
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        System.out.println("H2i ");
        return new ProviderManager(Arrays.asList(authProvider()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        System.out.println("Hi ");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        System.out.println("UserDetailsService: " +
                userDetailsService.getClass().getName());
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        System.out.println("AuthProvider: " + authProvider.getClass().getName());
        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .requestMatchers(HttpMethod.GET,
                        "/api/users/checkDuplicateEmail")
                .hasAuthority("Admin")
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("Admin")
                .requestMatchers(HttpMethod.DELETE,
                        "/api/users/{id}")
                .hasAuthority("Admin")
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}/enabled").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/countries/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/countries").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/countries").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/countries").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/states/**").permitAll()
                .requestMatchers(HttpMethod.POST,
                        "/api/states/{country_id}")
                .permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/states").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/states").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/customer/*").permitAll())
                .addFilterBefore(jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class);

        // disable CSRF
        http.csrf().disable();
        return http.build();
    }
}