package ar.edu.utn.frc.tup.lc.iii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA que representa la tabla de persistencia para Dummy.
 *
 * Nota: el nombre "DummyEntitie" contiene un typo intencional ("Entitie");
 * se mantiene para no romper referencias, pero podría refactorizarse a "DummyEntity" en el futuro.
 *
 * - {@link Entity}: indica que la clase será gestionada por JPA/Hibernate.
 * - {@link Data}, {@link AllArgsConstructor}, {@link NoArgsConstructor}: generan boilerplate con Lombok.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DummyEntitie {
    /** Clave primaria autogenerada (IDENTITY) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** Campo de ejemplo persistido en la base de datos */
    private String dummy;

    private Double DNI;

    private String email;

    private String Tel;
}
