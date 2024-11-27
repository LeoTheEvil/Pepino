import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Leonardo Sebastian Mancuso");
        myContact.setEmail("leomancuso8@gmail.com");

        Info information = new Info()
                .title("Kappa army Management API")
                .version("1.0")
                .description("Base de datos de las tropas del Shogun Kappa.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}