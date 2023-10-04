package veterinaria.tienda.vet.pacienteservice.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import veterinaria.tienda.vet.pacienteservice.domain.Paciente;
import veterinaria.tienda.vet.pacienteservice.repository.PacienteRepository;
import veterinaria.tienda.vet.pacienteservice.service.PacienteService;

@RestController
public class PacienteController implements PacienteApi{
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private PacienteService pacienteService;
	
	@Override
	@GetMapping
	public List<Paciente> obtenerPacientes() {
		return pacienteRepository.findAll();
	}
	
	@Override
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente crearPaciente(@RequestBody Paciente paciente) {
		return pacienteRepository.save(paciente);
	}
	
	@Override
	@GetMapping("{id}")
	public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable("id") String id) {
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		if (paciente.isPresent()) {
			return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@Override
	@PutMapping(path="{id}", consumes="application/json")
	public ResponseEntity<Paciente> actualizarPaciente(
			@PathVariable("id") String id, @RequestBody Paciente paciente) {
		Paciente pacienteActualizado = pacienteService.actualizarPaciente(id, paciente);
		if (pacienteActualizado != null) {
			return new ResponseEntity<>(pacienteActualizado, HttpStatus.OK) ;
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@Override
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarPaciente(@PathVariable("id") String id) {
		pacienteRepository.deleteById(id);
	}

}
