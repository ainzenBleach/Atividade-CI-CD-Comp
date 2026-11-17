package sptech.school.exemplo_action;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TarefaService {

	private final ConcurrentHashMap<Long, Tarefa> tarefas = new ConcurrentHashMap<>();
	private final AtomicLong proximoId = new AtomicLong(1);

	public List<Tarefa> listar() {
		return tarefas.values().stream()
				.sorted(Comparator.comparing(Tarefa::id))
				.toList();
	}

	public Tarefa buscar(Long id) {
		Tarefa tarefa = tarefas.get(id);
		if (tarefa == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
		}
		return tarefa;
	}

	public Tarefa criar(TarefaRequest request) {
		validarDescricao(request.descricao());
		Long id = proximoId.getAndIncrement();
		Tarefa tarefa = new Tarefa(id, request.descricao().trim(), request.concluida());
		tarefas.put(id, tarefa);
		return tarefa;
	}

	public Tarefa atualizar(Long id, TarefaRequest request) {
		buscar(id);
		validarDescricao(request.descricao());
		Tarefa tarefa = new Tarefa(id, request.descricao().trim(), request.concluida());
		tarefas.put(id, tarefa);
		return tarefa;
	}

	public void excluir(Long id) {
		if (tarefas.remove(id) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
		}
	}

	private void validarDescricao(String descricao) {
		if (descricao == null || descricao.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A descrição é obrigatória");
		}
	}
}
