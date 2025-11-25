package ar.edu.utn.frc.tup.lc.iii.services;


import ar.edu.utn.frc.tup.lc.iii.models.Dummy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de servicio para la lógica de negocio relacionada con {@link Dummy}.
 *
 * Nota: Aunque se marca con {@link Service}, normalmente las interfaces no llevan esta anotación;
 * suele colocarse en la implementación. Se mantiene para respetar el estado actual del proyecto.
 */
@Service
public interface DummyService {

    /** Obtiene un Dummy por id o lanza excepción si no existe. */
    Dummy getDummy (Long id);

    Dummy getDummyByDNI(Long DNI);

    /** Devuelve todos los Dummys. */
    List<Dummy> getDummyList();

    /** Crea un Dummy nuevo. */
    Dummy createDummy (Dummy dummy);

    /** Actualiza un Dummy existente (reemplazo completo). */
    Dummy updateDummy (Dummy dummy);

    /** Elimina un Dummy por id (404 si no existe). */
    void deleteDummy (Long id);

    /**
     * Busca un Dummy por múltiples criterios: si viene id, busca por id;
     * si no, intenta hallar por el campo 'dummy'.
     */
    Dummy getByAllDummy (Dummy dummy);

    /** Devuelve una lista filtrada por los campos del Dummy. */
    List<Dummy> getDummyFiltered (Dummy dummy);
}
