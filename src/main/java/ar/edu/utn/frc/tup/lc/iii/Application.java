package ar.edu.utn.frc.tup.lc.iii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación Spring Boot.
 *
 * - La anotación {@link SpringBootApplication} habilita la configuración automática,
 *   el escaneo de componentes y la configuración de Spring Boot.
 * - El método {@code main} arranca el contexto de Spring y el servidor embebido.
 */
@SpringBootApplication
public class Application {

    /**
     * Arranque de la aplicación.
     *
     * @param args argumentos de línea de comandos (opcionales)
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
