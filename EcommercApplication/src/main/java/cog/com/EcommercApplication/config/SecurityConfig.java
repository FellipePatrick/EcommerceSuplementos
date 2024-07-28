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
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/cadastro").hasRole("ADMIN");
                    auth.requestMatchers("/salvar").hasRole("ADMIN");
                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.requestMatchers("/editar/{id}").hasRole("ADMIN");
                    auth.requestMatchers("/deletar/{id}").hasRole("ADMIN");
                    auth.requestMatchers("/salvar").hasRole("ADMIN");
                    auth.requestMatchers("/carrinho").hasRole("USER");
                    auth.requestMatchers("/finalizarCompra").hasRole("USER");
                    auth.requestMatchers("/adicionarAoCarrinho/{id}").hasRole("USER");
                    auth.requestMatchers("/removerDoCarrinho").hasRole("USER");
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
