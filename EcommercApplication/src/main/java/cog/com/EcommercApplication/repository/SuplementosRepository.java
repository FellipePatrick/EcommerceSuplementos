package cog.com.EcommercApplication.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import cog.com.EcommercApplication.domain.Suplementos;

public interface SuplementosRepository extends JpaRepository<Suplementos, Long> {
    @Query("select suplementos from Suplementos suplementos where suplementos.isDeleted is null ORDER BY suplementos.id")
    List<Suplementos> findAllWhereIsDeletedIsNotNull();
}