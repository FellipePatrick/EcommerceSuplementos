package cog.com.EcommercApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cog.com.EcommercApplication.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}