package com.fln.apiflnmonitor.repository;
import com.fln.apiflnmonitor.model.Noticia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface NoticiasRepository extends JpaRepository<Noticia, Long> {

    boolean existsBylink(String link);

    List<Noticia> findTop100ByOrderByDataDesc();

    List<Noticia> findTop5ByFonteOrderByIdDesc(String fonte);

    List<Noticia> findTop100ByFonteOrderByIdDesc(String fonte);

    long countByFonte(String fonte);

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM Noticia n
        WHERE n.data < :limite
    """)
    int deletarNoticiasAntigas(@Param("limite") LocalDateTime limite);
}
