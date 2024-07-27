package cog.com.EcommercApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").hasRole("ADMIN");
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/cadastro").hasRole("ADMIN");
                    auth.anyRequest().permitAll();
                })
                .formLogin(login -> login.loginPage("/login"))
                .logout(l -> {
                    l.logoutUrl("/logout");
                    l.clearAuthentication(true);
                    l.deleteCookies().invalidateHttpSession(true);
                })
                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
