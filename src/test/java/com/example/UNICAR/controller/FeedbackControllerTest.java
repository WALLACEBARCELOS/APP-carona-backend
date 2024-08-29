package com.example.UNICAR.controller;

import com.example.UNICAR.model.Feedback;
import com.example.UNICAR.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(FeedbackController.class) // Configura o teste para o controlador FeedbackController
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc é usado para simular requisições HTTP ao controlador

    @Mock
    private FeedbackRepository feedbackRepository; // Mock do repositório de Feedback

    @InjectMocks
    private FeedbackController feedbackController; // Controlador que estamos testando

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build(); // Configura o MockMvc para o controlador
    }

    // Testa o endpoint que lista todos os feedbacks
    @Test
    void getAllFeedbacksTest() throws Exception {
        Feedback feedback1 = new Feedback(); // Cria uma instância de Feedback
        Feedback feedback2 = new Feedback(); // Cria outra instância de Feedback
        when(feedbackRepository.findAll()).thenReturn(Arrays.asList(feedback1, feedback2)); // Configura o mock para retornar dois feedbacks

        // Simula uma requisição GET para /feedbacks e verifica a resposta
        mockMvc.perform(get("/feedbacks"))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(jsonPath("$[0]").isNotEmpty()) // Verifica se o primeiro item da lista não está vazio
                .andExpect(jsonPath("$[1]").isNotEmpty()); // Verifica se o segundo item da lista não está vazio

        verify(feedbackRepository, times(1)).findAll(); // Verifica se o método findAll foi chamado uma vez
    }

    // Testa o endpoint que busca um feedback por ID
    @Test
    void getFeedbackByIdTest() throws Exception {
        Long id = 1L; // ID do feedback que será buscado
        Feedback feedback = new Feedback(); // Cria uma instância de Feedback
        when(feedbackRepository.findById(id)).thenReturn(Optional.of(feedback)); // Configura o mock para retornar o feedback com o ID especificado

        // Simula uma requisição GET para /feedbacks/{id} e verifica a resposta
        mockMvc.perform(get("/feedbacks/{id}", id))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(jsonPath("$.id").value(id)); // Verifica se o ID retornado é o esperado

        verify(feedbackRepository, times(1)).findById(id); // Verifica se o método findById foi chamado uma vez
    }

    // Testa o endpoint que cria um novo feedback
    @Test
    void createFeedbackTest() throws Exception {
        Feedback feedback = new Feedback(); // Cria uma instância de Feedback
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback); // Configura o mock para retornar o feedback criado

        // Simula uma requisição POST para /feedbacks e verifica a resposta
        mockMvc.perform(post("/feedbacks")
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{ \"comentario\": \"comentário\", \"nota\": 5 }")) // Dados do novo feedback
                .andExpect(status().isOk()); // Verifica se o status HTTP é 200 OK

        verify(feedbackRepository, times(1)).save(any(Feedback.class)); // Verifica se o método save foi chamado uma vez
    }

    // Testa o endpoint que atualiza um feedback existente
    @Test
    void updateFeedbackTest() throws Exception {
        Long id = 1L; // ID do feedback que será atualizado
        Feedback feedback = new Feedback(); // Cria uma instância de Feedback
        when(feedbackRepository.existsById(id)).thenReturn(true); // Configura o mock para indicar que o feedback existe
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(feedback); // Configura o mock para retornar o feedback atualizado

        // Simula uma requisição PUT para /feedbacks/{id} e verifica a resposta
        mockMvc.perform(put("/feedbacks/{id}", id)
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{ \"comentario\": \"comentário atualizado\", \"nota\": 4 }")) // Dados atualizados
                .andExpect(status().isOk()); // Verifica se o status HTTP é 200 OK

        verify(feedbackRepository, times(1)).save(any(Feedback.class)); // Verifica se o método save foi chamado uma vez
    }

    // Testa o endpoint que deleta um feedback por ID
    @Test
    void deleteFeedbackTest() throws Exception {
        Long id = 1L; // ID do feedback que será deletado
        when(feedbackRepository.existsById(id)).thenReturn(true); // Configura o mock para indicar que o feedback existe

        // Simula uma requisição DELETE para /feedbacks/{id} e verifica a resposta
        mockMvc.perform(delete("/feedbacks/{id}", id))
                .andExpect(status().isNoContent()); // Verifica se o status HTTP é 204 No Content

        verify(feedbackRepository, times(1)).deleteById(id); // Verifica se o método deleteById foi chamado uma vez
    }

    // Testa o endpoint que busca feedbacks por ID de viagem
    @Test
    void getFeedbacksByViagemIdTest() throws Exception {
        Long viagemId = 1L; // ID da viagem para buscar os feedbacks
        Feedback feedback1 = new Feedback(); // Cria uma instância de Feedback
        Feedback feedback2 = new Feedback(); // Cria outra instância de Feedback
        when(feedbackRepository.findByViagemId(viagemId)).thenReturn(Arrays.asList(feedback1, feedback2)); // Configura o mock para retornar dois feedbacks

        // Simula uma requisição GET para /feedbacks/viagem/{viagemId} e verifica a resposta
        mockMvc.perform(get("/feedbacks/viagem/{viagemId}", viagemId))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(jsonPath("$[0]").isNotEmpty()) // Verifica se o primeiro item da lista não está vazio
                .andExpect(jsonPath("$[1]").isNotEmpty()); // Verifica se o segundo item da lista não está vazio

        verify(feedbackRepository, times(1)).findByViagemId(viagemId); // Verifica se o método findByViagemId foi chamado uma vez
    }
}
