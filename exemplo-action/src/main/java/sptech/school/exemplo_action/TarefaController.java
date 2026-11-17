package sptech.school.exemplo_action;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

	private final TarefaService tarefaService;

	public TarefaController(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}

	@GetMapping
	public List<Tarefa> listar() {
		return tarefaService.listar();
	}

	@GetMapping("/{id}")
	public Tarefa buscar(@PathVariable Long id) {
		return tarefaService.buscar(id);
	}

	@PostMapping
	public ResponseEntity<Tarefa> criar(@RequestBody TarefaRequest request) {
		Tarefa tarefa = tarefaService.criar(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(tarefa.id())
				.toUri();
		return ResponseEntity.created(location).body(tarefa);
	}

	@PutMapping("/{id}")
	public Tarefa atualizar(@PathVariable Long id, @RequestBody TarefaRequest request) {
		return tarefaService.atualizar(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		tarefaService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
