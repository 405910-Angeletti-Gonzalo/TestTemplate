package ar.edu.utn.frc.tup.lc.iii.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * Modelo de dominio utilizado en la capa de servicio.
 * Representa el objeto de negocio desacoplado de la persistencia (Entity) y de la capa web (DTO).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dummy {
    /** Identificador del objeto de dominio */
    private Long id;
    /** Atributo demostrativo para el ejemplo */
    private String dummy;

    private Long DNI;

    private String email;

    private Long Tel;

    private LocalDate Fecha_Nac;

}

