package co.edu.usco.pw.springSecurityDB.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestión de Asignaturas API")
                        .version("1.0")
                        .description("Documentación de la API para la gestión de asignaturas con roles de usuario (Rector, Docente, Estudiante)."));
    }
}