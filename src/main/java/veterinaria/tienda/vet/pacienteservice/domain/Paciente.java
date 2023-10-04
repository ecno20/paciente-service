package veterinaria.tienda.vet.pacienteservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document (collection="paciente")
public class Paciente {
	@Id
	private String id;
	private String nombre;

}
