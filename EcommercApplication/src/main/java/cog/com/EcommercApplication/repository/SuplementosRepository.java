package cog.com.EcommercApplication.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import cog.com.EcommercApplication.domain.Suplementos;

public interface SuplementosRepository extends JpaRepository<Suplementos, String> {
    
}