package veterinaria.tienda.vet.pacienteservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
			version = "v1",
			title = "DogTor Veterinaria Microservice Endpoints", 
			description = "Definición de los Endpoints de "
					+ "Veterinaria Service para el sistema DogTor", 
			contact = @Contact(
				name = "UNAM", 
				url = "https://www.unam.mx/", 
			email = "jdsmatemaster@gmail.com.mx")))

public class OpenApiConfig {

}
