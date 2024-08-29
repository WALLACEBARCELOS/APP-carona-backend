package com.example.UNICAR.controller;

import com.example.UNICAR.model.Reserva;
import com.example.UNICAR.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservaControllerTests {

    private MockMvc mockMvc; // Instância do MockMvc para simular requisições HTTP

    @Mock
    private ReservaRepository reservaRepository; // Mock do repositório de Reserva

    @InjectMocks
    private ReservaController reservaController; // Controlador que será testado

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build(); // Configura o MockMvc para o controlador
    }

    @Test
    void testGetAllReservas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservas")) // Simula uma requisição GET para /reservas
                .andExpect(status().isOk()); // Verifica se a resposta tem status 200 OK
    }

    @Test
    void testGetReservaByIdFound() throws Exception {
        Reserva reserva = new Reserva(); // Cria uma instância de Reserva
        reserva.setId(1L); // Define o ID da reserva
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva)); // Configura o mock para retornar a reserva quando buscada pelo ID

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas/1")) // Simula uma requisição GET para /reservas/1
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Verifica se o ID da reserva na resposta é 1
    }

    @Test
    void testGetReservaByIdNotFound() throws Exception {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty()); // Configura o mock para retornar vazio quando buscado pelo ID

        mockMvc.perform(MockMvcRequestBuilders.get("/reservas/1")) // Simula uma requisição GET para /reservas/1
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }

    @Test
    void testCreateReserva() throws Exception {
        Reserva reserva = new Reserva(); // Cria uma instância de Reserva
        reserva.setId(1L); // Define o ID da reserva
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva); // Configura o mock para salvar e retornar a reserva

        mockMvc.perform(MockMvcRequestBuilders.post("/reservas") // Simula uma requisição POST para /reservas
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{\"id\":1}")) // Define o corpo da requisição com o ID 1
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Verifica se o ID da reserva na resposta é 1
    }

    @Test
    void testUpdateReservaFound() throws Exception {
        Reserva reserva = new Reserva(); // Cria uma instância de Reserva
        reserva.setId(1L); // Define o ID da reserva
        when(reservaRepository.existsById(1L)).thenReturn(true); // Configura o mock para indicar que a reserva existe
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva); // Configura o mock para salvar e retornar a reserva

        mockMvc.perform(MockMvcRequestBuilders.put("/reservas/1") // Simula uma requisição PUT para /reservas/1
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{\"id\":1}")) // Define o corpo da requisição com o ID 1
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Verifica se o ID da reserva na resposta é 1
    }

    @Test
    void testUpdateReservaNotFound() throws Exception {
        when(reservaRepository.existsById(1L)).thenReturn(false); // Configura o mock para indicar que a reserva não existe

        mockMvc.perform(MockMvcRequestBuilders.put("/reservas/1") // Simula uma requisição PUT para /reservas/1
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{\"id\":1}")) // Define o corpo da requisição com o ID 1
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }

    @Test
    void testDeleteReservaFound() throws Exception {
        when(reservaRepository.existsById(1L)).thenReturn(true); // Configura o mock para indicar que a reserva existe

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservas/1")) // Simula uma requisição DELETE para /reservas/1
                .andExpect(status().isNoContent()); // Verifica se a resposta tem status 204 No Content
    }

    @Test
    void testDeleteReservaNotFound() throws Exception {
        when(reservaRepository.existsById(1L)).thenReturn(false); // Configura o mock para indicar que a reserva não existe

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservas/1")) // Simula uma requisição DELETE para /reservas/1
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }

    @Test
    void testConfirmarReservaFound() throws Exception {
        Reserva reserva = new Reserva(); // Cria uma instância de Reserva
        reserva.setId(1L); // Define o ID da reserva
        reserva.setConfirmada(false); // Define o status de confirmação como falso
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva)); // Configura o mock para retornar a reserva quando buscada pelo ID
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva); // Configura o mock para salvar e retornar a reserva

        mockMvc.perform(MockMvcRequestBuilders.put("/reservas/1/confirmar")) // Simula uma requisição PUT para /reservas/1/confirmar
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.confirmada").value(true)); // Verifica se o status de confirmação da reserva na resposta é verdadeiro
    }

    @Test
    void testConfirmarReservaNotFound() throws Exception {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty()); // Configura o mock para retornar vazio quando buscado pelo ID

        mockMvc.perform(MockMvcRequestBuilders.put("/reservas/1/confirmar")) // Simula uma requisição PUT para /reservas/1/confirmar
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }
}
