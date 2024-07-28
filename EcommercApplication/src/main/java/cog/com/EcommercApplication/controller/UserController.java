package cog.com.EcommercApplication.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import cog.com.EcommercApplication.domain.Usuario;
import cog.com.EcommercApplication.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class UserController {

    UsuarioService usuarioService;
    public UserController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }
    @ModelAttribute
    public void addAttributes(Model model, @AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) {
        if (userDetails != null) {
            String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());

            String hora = DateTimeFormatter.ofPattern("HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
            
            hora = hora.replace(":", "");
                
            dateTime = dateTime.replace("-", "");

            dateTime = "D" + dateTime+ "H" + hora;
            String name = userDetails.getUsername();
            if (name != null) { 
                name = "cookie"+name.replace(".", "").replace("@", "");
            }
            Cookie lastAccessCookie = new Cookie(name, dateTime);
            lastAccessCookie.setMaxAge(86400);   
            lastAccessCookie.setPath("/"); 
            String role = userDetails.getAuthorities().toString();

            response.addCookie(lastAccessCookie);

            if (role.contains("ROLE_ADMIN")) {
                model.addAttribute("role", "ADMIN");
            } else {
                model.addAttribute("role", "USER");
            }
            Optional<Usuario> user = usuarioService.findByEmail(userDetails.getUsername());
            model.addAttribute("username", user.get().getNome());
        } else {
            model.addAttribute("role", "Guest");
            model.addAttribute("username", "Guest");
        }
    }
    
}