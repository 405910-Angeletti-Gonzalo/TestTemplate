package ar.edu.utn.frc.tup.lc.iii.controlers;


import ar.edu.utn.frc.tup.lc.iii.dtos.DummyDto;
import ar.edu.utn.frc.tup.lc.iii.models.Dummy;
import ar.edu.utn.frc.tup.lc.iii.services.DummyService;
import org.springframework.web.bind.annotation.RequestBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador REST para gestionar recursos Dummy.
 *
 * Expone endpoints CRUD básicos y algunas consultas simples.
 * Utiliza {@link ModelMapper} para convertir entre el modelo de dominio {@link Dummy} y el DTO {@link DummyDto}.
 */
@RestController
@RequestMapping("/dummy")
public class DummyController {

    /** Servicio de negocio para Dummy */
    @Autowired
    private DummyService dummyService;

    /** Mapeador de objetos (modelo <-> DTO) */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los dummys.
     *
     * @return lista de DummyDto en HTTP 200
     */
    @GetMapping("/dummy")
    public ResponseEntity <List<DummyDto>> getDummy(){
        List<DummyDto> dummyDto = new ArrayList<>();
        List<Dummy> dummyModels = dummyService.getDummyList();

        // Convertimos cada modelo a DTO para no exponer internals del dominio
        for (Dummy dummyModel : dummyModels) {
            dummyDto.add(modelMapper.map(dummyModel, DummyDto.class));
        }
        return ResponseEntity.ok(dummyDto);
    }

    /**
     * Busca un Dummy a partir de los campos provistos en el cuerpo.
     * Nota: usar cuerpo en GET no es una práctica estándar; podría migrarse a POST si se desea.
     */
    @GetMapping("")
    public ResponseEntity<DummyDto> getDummyAll(@RequestBody DummyDto dummyDto){
        Dummy dummy = dummyService.getByAllDummy(modelMapper.map(dummyDto, Dummy.class));
        DummyDto dummyResponse = modelMapper.map(dummy, DummyDto.class);
        return ResponseEntity.ok(dummyResponse);
    }

    /**
     * Obtiene un Dummy por su id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DummyDto> getDummyList(@PathVariable Long id){
        DummyDto dummy = modelMapper.map(dummyService.getDummy(id), DummyDto.class);
        return ResponseEntity.ok(dummy);
    }

    /**
     * Obtiene un Dummy por su DNI.
     */
    @GetMapping("/dni/{DNI}")
    public ResponseEntity<DummyDto> getDummyByDNI(@PathVariable Long DNI){
        DummyDto dummy = modelMapper.map(dummyService.getDummyByDNI(DNI), DummyDto.class);
        return ResponseEntity.ok(dummy);
    }

    /**
     * Devuelve dummys filtrados por campos del DTO.
     * Nota: este endpoint usa GET con body, considerar POST para filtros complejos.
     */
    @GetMapping("/list")
    public ResponseEntity<DummyDto> getDummyFiltered(@RequestBody DummyDto dummyDto){
        Dummy dummy = dummyService.getByAllDummy(modelMapper.map(dummyDto, Dummy.class));
        DummyDto dummyResponse = modelMapper.map(dummy, DummyDto.class);
        return ResponseEntity.ok(dummyResponse);
    }

    /**
     * Crea un nuevo Dummy.
     */
    @PostMapping("")
    public ResponseEntity<DummyDto> createDummy(@RequestBody DummyDto dummyDto){
        Dummy dummy = modelMapper.map(dummyDto, Dummy.class);
        DummyDto saved = modelMapper.map( dummyService.createDummy(dummy), DummyDto.class);
        return ResponseEntity.ok(saved);

    }

    /**
     * Actualiza un Dummy existente (reemplazo completo de campos).
     */
    @PutMapping("")
    public ResponseEntity<DummyDto> updateDummy(@RequestBody DummyDto dummyDto){
        Dummy dummy = modelMapper.map(dummyDto, Dummy.class);
        Dummy tDummy = dummyService.updateDummy(dummy);
        DummyDto dummyDto1 = modelMapper.map(tDummy, DummyDto.class);
        return ResponseEntity.ok(dummyDto1);
    }

    /**
     * Elimina un Dummy por id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDummy(@PathVariable Long id){
        dummyService.deleteDummy(id);
        return ResponseEntity.ok("El dummy id " + id + " a sido eliminado con exito");
    }

}
