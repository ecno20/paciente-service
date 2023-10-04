package veterinaria.tienda.vet.pacienteservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import veterinaria.tienda.vet.pacienteservice.domain.Paciente;

public interface PacienteRepository 
extends MongoRepository<Paciente, String>{

}
