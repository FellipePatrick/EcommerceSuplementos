package cog.com.EcommercApplication.service;

import org.springframework.stereotype.Service;

import cog.com.EcommercApplication.domain.Usuario;
import cog.com.EcommercApplication.repository.UsuarioRepository;
@Service
public class UsuarioService{
    UsuarioRepository usuarioRepository;
    UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    public Usuario cadastreUser(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
}
