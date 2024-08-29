package com.example.UNICAR.controller;

import com.example.UNICAR.model.User;
import com.example.UNICAR.repository.UserRepository;
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

public class UserControllerTests {

    private MockMvc mockMvc; // Instância do MockMvc para simular requisições HTTP

    @Mock
    private UserRepository userRepository; // Mock do repositório de User

    @InjectMocks
    private UserController userController; // Controlador que será testado

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Configura o MockMvc para o controlador
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")) // Simula uma requisição GET para /usuarios
                .andExpect(status().isOk()); // Verifica se a resposta tem status 200 OK
    }

    @Test
    void testGetUserByIdFound() throws Exception {
        User user = new User(); // Cria uma instância de User
        user.setId(1L); // Define o ID do usuário
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Configura o mock para retornar o usuário quando buscado pelo ID

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1")) // Simula uma requisição GET para /usuarios/1
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Verifica se o ID do usuário na resposta é 1
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty()); // Configura o mock para retornar vazio quando buscado pelo ID

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1")) // Simula uma requisição GET para /usuarios/1
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }

    @Test
    void testCreateUser() throws Exception {
        User user = new User(); // Cria uma instância de User
        user.setId(1L); // Define o ID do usuário
        when(userRepository.save(any(User.class))).thenReturn(user); // Configura o mock para salvar e retornar o usuário

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios") // Simula uma requisição POST para /usuarios
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{\"id\":1}")) // Define o corpo da requisição com o ID 1
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Verifica se o ID do usuário na resposta é 1
    }

    @Test
    void testUpdateUserFound() throws Exception {
        User user = new User(); // Cria uma instância de User
        user.setId(1L); // Define o ID do usuário
        when(userRepository.existsById(1L)).thenReturn(true); // Configura o mock para indicar que o usuário existe
        when(userRepository.save(any(User.class))).thenReturn(user); // Configura o mock para salvar e retornar o usuário

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1") // Simula uma requisição PUT para /usuarios/1
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{\"id\":1}")) // Define o corpo da requisição com o ID 1
                .andExpect(status().isOk()) // Verifica se a resposta tem status 200 OK
                .andExpect(jsonPath("$.id").value(1)); // Verifica se o ID do usuário na resposta é 1
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        when(userRepository.existsById(1L)).thenReturn(false); // Configura o mock para indicar que o usuário não existe

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1") // Simula uma requisição PUT para /usuarios/1
                        .contentType("application/json") // Define o tipo de conteúdo como JSON
                        .content("{\"id\":1}")) // Define o corpo da requisição com o ID 1
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }

    @Test
    void testDeleteUserFound() throws Exception {
        when(userRepository.existsById(1L)).thenReturn(true); // Configura o mock para indicar que o usuário existe

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/1")) // Simula uma requisição DELETE para /usuarios/1
                .andExpect(status().isNoContent()); // Verifica se a resposta tem status 204 No Content
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        when(userRepository.existsById(1L)).thenReturn(false); // Configura o mock para indicar que o usuário não existe

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/1")) // Simula uma requisição DELETE para /usuarios/1
                .andExpect(status().isNotFound()); // Verifica se a resposta tem status 404 Not Found
    }
}
