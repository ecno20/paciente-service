package veterinaria.tienda.vet.pacienteservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import veterinaria.tienda.vet.pacienteservice.domain.Paciente;
import veterinaria.tienda.vet.pacienteservice.repository.PacienteRepository;

@Service
public class PacienteServiceImpl implements PacienteService{
	@Autowired
	private PacienteRepository pacienteRepository;
	@Override
	public Paciente actualizarPaciente(String id, Paciente paciente) {
		Optional<Paciente> pacienteExistente = pacienteRepository.findById(id);
		if (pacienteExistente.isPresent()) {
			Paciente pacienteActualizar = pacienteExistente.get();
			pacienteActualizar.setNombre(paciente.getNombre());
			pacienteRepository.save(pacienteActualizar);
			return pacienteActualizar;
		}
		return null;
		}
}
