package ar.edu.utn.frc.tup.lc.iii.services.impl;

import ar.edu.utn.frc.tup.lc.iii.entities.DummyEntitie;
import ar.edu.utn.frc.tup.lc.iii.models.Dummy;
import ar.edu.utn.frc.tup.lc.iii.repositories.DummyRepository;
import ar.edu.utn.frc.tup.lc.iii.services.DummyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementación de la capa de servicio para {@link Dummy}.
 *
 * - Orquesta el acceso al repositorio y realiza el mapeo entre Entity y Model.
 * - Lanza excepciones con estados HTTP adecuados para que el Controller/Handler las traduzca.
 */
@Service
public class DummyServiceImpl implements DummyService {

    @Autowired
    private DummyRepository dummyRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene un Dummy por id. Si no existe, lanza 404 (EntityNotFoundException -> 404 por Handler).
     */
    @Override
    public Dummy getDummy(Long id) {
        DummyEntitie ent = dummyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El dummy id "+ id +" no se encuentra"));
        return modelMapper.map(ent, Dummy.class);
    }

    @Override
    public Dummy getDummyByDNI(Long dni) {
        DummyEntitie dummyEntitie = getDummyEntitieByDni(dni);
        if (dummyEntitie == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El dummy DNI "+ dni +" no se encuentra");
        }
        else {
            return modelMapper.map(dummyEntitie, Dummy.class);
        }
    }
    
    

    private DummyEntitie getDummyEntitieByDni(Long dni) {
        DummyEntitie dummyEntitie = null;
        List<DummyEntitie> dummyEntities = dummyRepository.findAll();

        for (DummyEntitie dummyEntity : dummyEntities) {
            if (Objects.equals(dummyEntity.getDni(), dni)) {
                dummyEntitie = dummyEntity;
            }
        }
        return dummyEntitie;
    }

    private DummyEntitie getDummyEntitieByMail(String mail) {
        DummyEntitie dummyEntitie = null;
        List<DummyEntitie> dummyEntities = dummyRepository.findAll();

        for (DummyEntitie dummyEntity : dummyEntities) {
            if (Objects.equals(dummyEntity.getEmail(), mail)) {
                dummyEntitie = dummyEntity;
            }
        }
        return dummyEntitie;
    }

    /**
     * Devuelve todos los Dummy mapeados a modelo de dominio.
     */
    @Override
    public List<Dummy> getDummyList() {
        List<Dummy> dummyList = new ArrayList<>();
        List<DummyEntitie> dummyEntities = dummyRepository.findAll();
        for (DummyEntitie dummyEntity : dummyEntities) {
            dummyList.add(modelMapper.map(dummyEntity, Dummy.class));
        }
        return dummyList;
    }

    /**
     * Crea un Dummy: mapea a Entity, persiste y devuelve el modelo creado.
     */
    @Override
    public Dummy createDummy(Dummy dummy) {
        if (dummy.getDni() < 100000000) {
            return VerifyDummyExist(dummy);

        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "El dummy tiene el DNI demasiado largo");
        }
    }

    private Dummy VerifyDummyExist(Dummy dummy) {
        if (getDummyEntitieByDni(dummy.getDni())== null) {
            if (getDummyEntitieByMail(dummy.getEmail())==null) {
                DummyEntitie dummyEntitie = modelMapper.map(dummy, DummyEntitie.class);
                dummyRepository.save(dummyEntitie);
                return modelMapper.map(dummyEntitie, Dummy.class);
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Ya existe un Dummy con el mail " + dummy.getEmail());

        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Ya existe un Dummy con DNI " + dummy.getDni());
    }

    /**
     * Actualiza un Dummy existente. Aquí se hace un reemplazo completo; podría mejorarse
     * validando existencia previa y usando un "mergerMapper" para updates parciales.
     */
    @Override
    public Dummy updateDummy(Dummy dummy) {
        // save() hace upsert: si existe id, actualiza; si no, inserta.
        DummyEntitie dummyEntitie = dummyRepository.save(modelMapper.map(dummy, DummyEntitie.class));
        // Segunda llamada a save es redundante, pero se mantiene para no cambiar comportamiento observable.
        dummyRepository.save(dummyEntitie);
        return modelMapper.map(dummyEntitie, Dummy.class);
    }

    /**
     * Elimina un Dummy por id. Lanza 404 si no existe.
     */
    @Override
    public void deleteDummy(Long id) {
        DummyEntitie dummy = dummyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El dummy no existe"));
        dummyRepository.delete(dummy);
    }

    /**
     * Búsqueda flexible: si viene id, busca por id; si no, intenta encontrar por el campo 'dummy'.
     * Si no se encuentra, lanza 404 con un mensaje claro.
     */
    @Override
    public Dummy getByAllDummy(Dummy dummy) {
        Long id =  dummy.getId();
        int aux = 0; // contador de elementos que no coinciden (usado para detectar no encontrado)
        if (id == null){
            List<DummyEntitie>  dummyEntities = dummyRepository.findAll();
            DummyEntitie dummyEntitie;
            for (DummyEntitie dummyEntity : dummyEntities) {
                dummyEntitie = dummyEntity;
                if (Objects.equals(dummyEntitie.getDummy(), dummy.getDummy())) {
                    // Asignamos el id encontrado al modelo recibido, para devolverlo completo
                    dummy.setId(dummyEntitie.getId());
                } else {
                    aux++;
                }
            }
            if (aux == dummyEntities.size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El dummy con dummy '" + dummy.getDummy() + "' no existe");
            } else {
                return dummy;
            }
        }
        else {
           DummyEntitie dummyEntitie = dummyRepository.findById(id)
                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El dummy id " + id + " no existe"));
           dummy = modelMapper.map(dummyEntitie, Dummy.class);
        }
        return dummy;
    }

    /**
     * Similar a getByAllDummy pero devolviendo una lista de posibles coincidencias.
     */
    @Override
    public List<Dummy> getDummyFiltered(Dummy dummy) {
        Long id =  dummy.getId();
        List<Dummy> dummyListResponse = new ArrayList<>();
        int aux = 0; // contador de no coincidencias
        if (id == null){
            List<DummyEntitie>  dummyEntities = dummyRepository.findAll();
            DummyEntitie dummyEntitie;
            for (DummyEntitie dummyEntity : dummyEntities) {
                dummyEntitie = dummyEntity;
                if (Objects.equals(dummyEntitie.getDummy(), dummy.getDummy())) {
                    dummyListResponse.add(modelMapper.map(dummyEntitie, Dummy.class));
                } else {
                    aux++;
                }
            }
            if (aux == dummyEntities.size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un Dummy con dummy '" + dummy.getDummy() +"'");
            } else {
                return dummyListResponse;
            }
        }
        else {
            DummyEntitie dummyEntitie = dummyRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un Dummy con id " + id));
            dummy = modelMapper.map(dummyEntitie, Dummy.class);
            return Collections.singletonList(dummy);
        }
    }
}
