package ar.edu.utn.frc.tup.lc.iii.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Modelo de dominio utilizado en la capa de servicio.
 * Representa el objeto de negocio desacoplado de la persistencia (Entity) y de la capa web (DTO).
 * Campos:
 * String dummy* /
 * Long DNI *
 * -String email
 * -Long tel
 * -LocalDate fecha_Nac
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dummy {
    /** Identificador del objeto de dominio */
    private Long id;
    /** Atributo demostrativo para el ejemplo */
    private String dummy;

    private Long dni;

    private String email;

    private Long tel;

    private LocalDate fecha_Nac;

}

