package ar.edu.utn.frc.tup.lc.iii.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    private Long dni;

    private String email;

    private Long tel;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_Nac;
}
