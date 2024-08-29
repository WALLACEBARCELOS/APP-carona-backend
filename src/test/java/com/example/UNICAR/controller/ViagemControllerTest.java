package com.example.UNICAR.controller;

import com.example.UNICAR.model.Reserva;
import com.example.UNICAR.model.Viagem;
import com.example.UNICAR.repository.ReservaRepository;
import com.example.UNICAR.repository.ViagemRepository;
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
@WebMvcTest(ViagemController.class) // Configura o teste para o controlador ViagemController
class ViagemControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc é usado para simular requisições HTTP ao controlador

    @Mock
    private ViagemRepository viagemRepository; // Mock do repositório de Viagem

    @Mock
    private ReservaRepository reservaRepository; // Mock do repositório de Reserva

    @InjectMocks
    private ViagemController viagemController; // Controlador que estamos testando

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        mockMvc = MockMvcBuilders.standaloneSetup(viagemController).build(); // Configura o MockMvc para o controlador
    }

    // Testa o endpoint que lista todas as viagens
    @Test
    void getAllViagensTest() throws Exception {
        Viagem viagem1 = new Viagem(); // Cria uma instância de Viagem
        Viagem viagem2 = new Viagem(); // Cria outra instância de Viagem
        when(viagemRepository.findAll()).thenReturn(Arrays.asList(viagem1, viagem2)); // Configura o mock para retornar duas viagens

        // Simula uma requisição GET para /viagens e verifica a resposta
        mockMvc.perform(get("/viagens"))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(jsonPath("$[0]").isNotEmpty()) // Verifica se o primeiro item da lista não está vazio
                .andExpect(jsonPath("$[1]").isNotEmpty()); // Verifica se o segundo item da lista não está vazio

        verify(viagemRepository, times(1)).findAll(); // Verifica se o método findAll foi chamado uma vez
    }

    // Testa o endpoint que busca uma viagem por ID
    @Test
    void getViagemByIdTest() throws Exception {
        Long id = 1L; // ID da viagem que será buscada
        Viagem viagem = new Viagem(); // Cria uma instância de Viagem
        when(viagemRepository.findById(id)).thenReturn(Optional.of(viagem)); // Configura o mock para retornar a viagem com o ID especificado

        // Simula uma requisição GET para /viagens/{id} e verifica a resposta
        mockMvc.perform(get("/viagens/{id}", id))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(jsonPath("$.id").value(id)); // Verifica se o ID retornado é o esperado

        verify(viagemRepository, times(1)).findById(id); // Verifica se o método findById foi chamado uma vez
    }

    // Testa o endpoint que cria uma nova viagem
    @Test
    void createViagemTest() throws Exception {
        Viagem viagem = new Viagem(); // Cria uma instância de Viagem
        when(viagemRepository.save(any(Viagem.class))).thenReturn(viagem); // Configura o mock para retornar a viagem criada

        // Simula uma requisição POST para /viagens e verifica a resposta
        mockMvc.perform(post("/viagens")
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{ \"origem\": \"origem\", \"destino\": \"destino\", \"horarioPartida\": \"horario\" }")) // Dados da nova viagem
                .andExpect(status().isOk()); // Verifica se o status HTTP é 200 OK

        verify(viagemRepository, times(1)).save(any(Viagem.class)); // Verifica se o método save foi chamado uma vez
    }

    // Testa o endpoint que atualiza uma viagem existente
    @Test
    void updateViagemTest() throws Exception {
        Long id = 1L; // ID da viagem que será atualizada
        Viagem viagem = new Viagem(); // Cria uma instância de Viagem
        when(viagemRepository.existsById(id)).thenReturn(true); // Configura o mock para indicar que a viagem existe
        when(viagemRepository.save(any(Viagem.class))).thenReturn(viagem); // Configura o mock para retornar a viagem atualizada

        // Simula uma requisição PUT para /viagens/{id} e verifica a resposta
        mockMvc.perform(put("/viagens/{id}", id)
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{ \"origem\": \"nova origem\", \"destino\": \"novo destino\", \"horarioPartida\": \"novo horario\" }")) // Dados atualizados
                .andExpect(status().isOk()); // Verifica se o status HTTP é 200 OK

        verify(viagemRepository, times(1)).save(any(Viagem.class)); // Verifica se o método save foi chamado uma vez
    }

    // Testa o endpoint que deleta uma viagem por ID
    @Test
    void deleteViagemTest() throws Exception {
        Long id = 1L; // ID da viagem que será deletada
        when(viagemRepository.existsById(id)).thenReturn(true); // Configura o mock para indicar que a viagem existe

        // Simula uma requisição DELETE para /viagens/{id} e verifica a resposta
        mockMvc.perform(delete("/viagens/{id}", id))
                .andExpect(status().isNoContent()); // Verifica se o status HTTP é 204 No Content

        verify(viagemRepository, times(1)).deleteById(id); // Verifica se o método deleteById foi chamado uma vez
    }

    // Testa o endpoint que lista todas as reservas de uma viagem específica
    @Test
    void getReservasByViagemIdTest() throws Exception {
        Long viagemId = 1L; // ID da viagem cuja reserva será listada
        Viagem viagem = new Viagem(); // Cria uma instância de Viagem
        Reserva reserva1 = new Reserva(); // Cria uma instância de Reserva
        Reserva reserva2 = new Reserva(); // Cria outra instância de Reserva
        viagem.setReservas(Arrays.asList(reserva1, reserva2)); // Define as reservas da viagem
        when(viagemRepository.findById(viagemId)).thenReturn(Optional.of(viagem)); // Configura o mock para retornar a viagem com reservas

        // Simula uma requisição GET para /viagens/{viagemId}/reservas e verifica a resposta
        mockMvc.perform(get("/viagens/{viagemId}/reservas", viagemId))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 OK
                .andExpect(jsonPath("$[0]").isNotEmpty()) // Verifica se o primeiro item da lista de reservas não está vazio
                .andExpect(jsonPath("$[1]").isNotEmpty()); // Verifica se o segundo item da lista de reservas não está vazio

        verify(viagemRepository, times(1)).findById(viagemId); // Verifica se o método findById foi chamado uma vez
    }

    // Testa o endpoint que adiciona uma reserva a uma viagem específica
    @Test
    void addReservaToViagemTest() throws Exception {
        Long viagemId = 1L; // ID da viagem à qual a reserva será adicionada
        Reserva reserva = new Reserva(); // Cria uma instância de Reserva
        Viagem viagem = new Viagem(); // Cria uma instância de Viagem
        when(viagemRepository.findById(viagemId)).thenReturn(Optional.of(viagem)); // Configura o mock para retornar a viagem
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva); // Configura o mock para retornar a reserva criada

        // Simula uma requisição POST para /viagens/{viagemId}/reservas e verifica a resposta
        mockMvc.perform(post("/viagens/{viagemId}/reservas", viagemId)
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{ \"assentosReservados\": 2 }")) // Dados da nova reserva
                .andExpect(status().isOk()); // Verifica se o status HTTP é 200 OK

        verify(reservaRepository, times(1)).save(any(Reserva.class)); // Verifica se o método save foi chamado uma vez
    }

    // Testa o endpoint que cancela uma reserva
    @Test
    void cancelReservaTest() throws Exception {
        Long viagemId = 1L; // ID da viagem cuja reserva será cancelada
        Long reservaId = 1L; // ID da reserva que será cancelada
        Reserva reserva = new Reserva(); // Cria uma instância de Reserva
        reserva.setViagem(new Viagem()); // Define a viagem da reserva
        reserva.getViagem().setId(viagemId); // Define o ID da viagem da reserva
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva)); // Configura o mock para retornar a reserva

        // Simula uma requisição DELETE para /viagens/{viagemId}/reservas/{reservaId} e verifica a resposta
        mockMvc.perform(delete("/viagens/{viagemId}/reservas/{reservaId}", viagemId, reservaId))
                .andExpect(status().isNoContent()); // Verifica se o status HTTP é 204 No Content

        verify(reservaRepository, times(1)).deleteById(reservaId); // Verifica se o método deleteById foi chamado uma vez
    }
}
