package ar.edu.utn.frc.tup.lc.iii.repositories;


import ar.edu.utn.frc.tup.lc.iii.entities.DummyEntitie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para acceder a la persistencia de {@link DummyEntitie}.
 * Extiende {@link JpaRepository} para obtener operaciones CRUD básicas.
 */
@Repository
public interface DummyRepository extends JpaRepository<DummyEntitie, Long> {
    /**
     * Busca por id devolviendo Optional. Sobrescribimos para documentar.
     */
    @Override
    Optional<DummyEntitie> findById(Long Long);
}
