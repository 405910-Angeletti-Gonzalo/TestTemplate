package ar.edu.utn.frc.tup.lc.iii.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para documentar la API.
 *
 * - Lee propiedades desde application.properties para poblar los metadatos.
 * - Expone un bean OpenAPI que SpringDoc usa para generar la UI de Swagger.
 */
@Configuration
public class SpringDocConfig {

    @Value("${app.url}") private String url;
    @Value("${app.dev-name}")private String devName;
    @Value("${app.dev-email}")private String devEmail;

    /**
     * Construye el objeto OpenAPI con Info y Server a partir de propiedades.
     */
    @Bean
    public OpenAPI openApi (
            @Value("${app.name}") String appName,
            @Value("${app.desc}") String appDescription,
            @Value("${app.version}") String appVersion){

        Info info = new Info()
                .title(appName)
                .version(appVersion)
                .description(appDescription)
                .contact(
                        new Contact()
                                .name(devName)
                                .email(devEmail));

        Server server = new Server()
                .url(url)
                .description(appDescription);

        return new OpenAPI()
                .components(new Components())
                .info(info)
                .addServersItem(server);
    }

    /**
     * Integra el ObjectMapper de Jackson para que SpringDoc entienda correctamente los modelos.
     */
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
