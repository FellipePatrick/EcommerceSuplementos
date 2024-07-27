package cog.com.EcommercApplication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cog.com.EcommercApplication.domain.Usuario;
import cog.com.EcommercApplication.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
@Service
public class UsuarioService implements UserDetailsService{
    UsuarioRepository usuarioRepository;
    UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    public Usuario cadastreUser(Usuario usuario){
        PasswordEncoder e = new BCryptPasswordEncoder();
        usuario.setSenha(e.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> optional = usuarioRepository.findByEmail(email);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

}
