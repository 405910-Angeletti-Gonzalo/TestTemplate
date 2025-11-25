package ar.edu.utn.frc.tup.lc.iii.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de beans de mapeo de objetos y JSON.
 *
 * - ModelMapper: facilita convertir entre entidades, modelos y DTOs.
 * - mergerMapper: igual a ModelMapper, pero ignora propiedades nulas al mapear (útil para updates parciales).
 * - ObjectMapper: registra el módulo de fechas de Java 8 para serializar/deserializar correctamente.
 */
@Configuration
public class MappersConfig {

    /**
     * ModelMapper estándar para conversiones completas entre objetos.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Variante que solo copia propiedades NO nulas desde el origen al destino.
     * Ideal para operaciones tipo PATCH/Merge.
     */
    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull()); // ignora nulls
        return mapper;
    }

    /**
     * Configura Jackson para soportar tipos java.time (LocalDate, LocalDateTime, etc.).
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
