package ar.edu.utn.frc.tup.lc.iii.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para exponer datos de Dummy en la capa web.
 *
 * - Anotaciones Lombok:
 *   - {@link Data}: genera getters, setters, equals/hashCode y toString.
 *   - {@link AllArgsConstructor}: genera constructor con todos los campos.
 *   - {@link NoArgsConstructor}: genera constructor vacío (requerido por Jackson).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummyDto {

    /** Identificador del recurso Dummy */
    private Long id;

    /** Campo ejemplo para demostrar el flujo de datos */
    private String dummy;
}
