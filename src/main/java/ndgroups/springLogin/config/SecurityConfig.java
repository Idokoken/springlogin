package ndgroups.springLogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.cors()
//                .and().csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/register")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/users")
//                .hasAnyAuthority("USER", "ADMIN")
//                .anyRequest().and().formlogin().and().build();


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/users/**").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers("/users/**").hasRole("USER")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().permitAll()

                )
                .formLogin((form) -> form.permitAll())
			.logout((logout) -> logout.permitAll());

        return http.build();
    }
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return userDetailsService;
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        return authenticationProvider;
//    }


}
