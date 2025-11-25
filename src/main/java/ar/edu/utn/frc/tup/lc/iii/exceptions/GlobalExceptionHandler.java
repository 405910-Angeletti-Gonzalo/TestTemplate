package ar.edu.utn.frc.tup.lc.iii.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones de la API.
 *
 * - @RestControllerAdvice: convierte cualquier excepción capturada en una respuesta JSON apropiada
 *   y aplica a todos los controladores REST del proyecto.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las ResponseStatusException arrojadas desde servicios/controladores.
     *
     * Ejemplo: new ResponseStatusException(HttpStatus.NOT_FOUND, "El dummy no existe")
     *
     * @param ex Excepción con el status y el reason (mensaje) que definiste.
     * @param request Información de la petición para completar el campo "path".
     * @return ResponseEntity con el status de la excepción y un cuerpo estandarizado.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex, WebRequest request) {
        HttpStatusCode status = ex.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now().toString()) // fecha/hora ISO de cuando ocurrió el error
                .status(status.value())                     // código HTTP (ej. 404)
                .error(status.toString())                   // descripción (ej. 404 NOT_FOUND)
                .message(ex.getReason())                    // tu mensaje personalizado
                .path(getPath(request))                     // path solicitado (ej. /api/dummys/999)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Traduce EntityNotFoundException a 404 Not Found con el mensaje de la excepción.
     * Útil si en tu servicio haces: throw new EntityNotFoundException("Dummy 1 no encontrado")
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now().toString())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(getPath(request))
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Maneja errores de validación provenientes de @Valid en parámetros/DTOs.
     * Responde 400 Bad Request con un mapa de errores por campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Agrupa los mensajes por nombre de campo: campo -> [lista de mensajes]
        Map<String, List<String>> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now().toString())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message("Errores de validación")
                .path(getPath(request))
                .details(validationErrors) // detalles específicos de validación
                .build();

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Fallback general: captura cualquier excepción no contemplada arriba.
     * Evita 500 sin formato y te permite controlar el cuerpo de la respuesta.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now().toString())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage()) // en prod puedes reemplazar por un mensaje genérico
                .path(getPath(request))
                .build();
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Extrae la URI del request en formato simple (por ejemplo: "/api/dummys/999").
     */
    private String getPath(WebRequest request) {
        // request.getDescription(false) devuelve algo como "uri=/api/dummys/1".
        String desc = request.getDescription(false);
        int idx = desc.indexOf("uri=");
        return idx >= 0 ? desc.substring(idx + 4) : desc;
    }

    /**
     * DTO para estandarizar la respuesta de error.
     * Incluye un builder sencillo y getters para compatibilidad con Jackson.
     */
    public static class ErrorResponse {
        private String timestamp;      // Cuándo ocurrió el error
        private int status;            // Código HTTP
        private String error;          // Descripción del HTTP status
        private String message;        // Mensaje legible para el cliente
        private String path;           // Endpoint solicitado
        private Map<String, ?> details; // Detalles adicionales (p.ej., validaciones)

        public ErrorResponse() {}

        // Builder manual para crear instancias de forma fluida
        public static Builder builder() { return new Builder(); }
        public static class Builder {
            private final ErrorResponse r = new ErrorResponse();
            public Builder timestamp(String v) { r.timestamp = v; return this; }
            public Builder status(int v) { r.status = v; return this; }
            public Builder error(String v) { r.error = v; return this; }
            public Builder message(String v) { r.message = v; return this; }
            public Builder path(String v) { r.path = v; return this; }
            public Builder details(Map<String, ?> v) { r.details = v; return this; }
            public ErrorResponse build() { return r; }
        }

        // Getters necesarios para que Jackson serialice el objeto a JSON
        public String getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
        public Map<String, ?> getDetails() { return details; }
    }
}
