package veterinaria.tienda.vet.pacienteservice.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import veterinaria.tienda.vet.pacienteservice.domain.Paciente;

@RequestMapping(path="api/pacientes", produces="application/json")
@Tag(name="paciente", 
description="API del Recurso Paciente")
@CrossOrigin(origins="*")
public interface PacienteApi {
	@Operation(summary = "Crear Paciente")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "201", 
		description = "Paciente Creado Exitosamente", 
	    content = { 
	    	@Content(mediaType="application/json", 
	    	schema = @Schema(implementation=Paciente.class)) }),
	  @ApiResponse(responseCode = "409", 
	  	description="Ya existe un Paciente con el nombre especificado", 
	    content = @Content) })
	@PostMapping(path="registro", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	Paciente crearPaciente(@RequestBody Paciente paciente);

	// Documentar similar al método crearPaciente pero para esta operación (Revisión: Pasa prueba)
	@GetMapping("{id}")
	ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable("id") String id);
	
	// Documentar similar al método crearPaciente pero para esta operación (Revisión: Pasa prueba)
	@GetMapping
	List<Paciente> obtenerPacientes();
	
	// Documentar similar al método crearPaciente pero para esta operación (Revisión: Pasa prueba)
	@PutMapping(path="{id}", consumes="application/json")
	ResponseEntity<Paciente> actualizarPaciente(
			@PathVariable("id") String id, @RequestBody Paciente paciente);
	
	// Documentar similar al método crearPaciente pero para esta operación (Revisión: Pasa prueba)
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void eliminarPaciente(@PathVariable("id") String id);

}
