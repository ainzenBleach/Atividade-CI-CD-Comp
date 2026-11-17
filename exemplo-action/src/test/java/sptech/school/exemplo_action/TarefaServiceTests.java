package sptech.school.exemplo_action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class TarefaServiceTests {

	private final TarefaService tarefaService = new TarefaService();

	@Test
	void deveCriarListarAtualizarEBuscarTarefa() {
		Tarefa criada = tarefaService.criar(new TarefaRequest("Estudar Spring", false));

		assertEquals(1L, criada.id());
		assertEquals("Estudar Spring", criada.descricao());
		assertFalse(criada.concluida());
		assertEquals(criada, tarefaService.buscar(criada.id()));
		assertEquals(1, tarefaService.listar().size());

		Tarefa atualizada = tarefaService.atualizar(
				criada.id(),
				new TarefaRequest("Estudar Spring Boot", true));

		assertEquals("Estudar Spring Boot", atualizada.descricao());
		assertTrue(atualizada.concluida());
	}

	@Test
	void deveExcluirTarefaEInformarQuandoNaoEncontrada() {
		Tarefa criada = tarefaService.criar(new TarefaRequest("Finalizar atividade", false));

		tarefaService.excluir(criada.id());

		assertTrue(tarefaService.listar().isEmpty());
		assertThrows(ResponseStatusException.class, () -> tarefaService.buscar(criada.id()));
	}

	@Test
	void deveRejeitarDescricaoVazia() {
		ResponseStatusException exception = assertThrows(
				ResponseStatusException.class,
				() -> tarefaService.criar(new TarefaRequest("  ", false)));

		assertEquals(400, exception.getStatusCode().value());
	}
}
