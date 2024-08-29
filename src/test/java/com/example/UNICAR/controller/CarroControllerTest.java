package com.example.UNICAR.controller;

import com.example.UNICAR.model.Carro;
import com.example.UNICAR.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarroController.class)
public class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarroRepository carroRepository;

    @Test
    void getAllCarsTest() throws Exception {
        Carro carro1 = new Carro();
        Carro carro2 = new Carro();
        when(carroRepository.findAll()).thenReturn(Arrays.asList(carro1, carro2));

        mockMvc.perform(get("/carros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(carroRepository, times(1)).findAll();
    }

    @Test
    void getCarByIdTest() throws Exception {
        Long id = 1L;
        Carro carro = new Carro();
        carro.setId(id); // Define o ID do carro para a resposta
        when(carroRepository.findById(id)).thenReturn(Optional.of(carro));

        mockMvc.perform(get("/carros/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(carroRepository, times(1)).findById(id);
    }

    @Test
    void createCarTest() throws Exception {
        Carro carro = new Carro();
        carro.setId(1L); // Define o ID para o carro criado
        when(carroRepository.save(any(Carro.class))).thenReturn(carro);

        mockMvc.perform(post("/carros")
                        .contentType("application/json")
                        .content("{ \"modelo\": \"modelo\", \"marca\": \"marca\", \"ano\": 2022 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L)); // Verifica se o ID do carro criado é o esperado

        verify(carroRepository, times(1)).save(any(Carro.class));
    }

    @Test
    void updateCarTest() throws Exception {
        Long id = 1L;
        Carro carro = new Carro();
        carro.setId(id);
        when(carroRepository.existsById(id)).thenReturn(true);
        when(carroRepository.save(any(Carro.class))).thenReturn(carro);

        mockMvc.perform(put("/carros/{id}", id)
                        .contentType("application/json")
                        .content("{ \"modelo\": \"modelo atualizado\", \"marca\": \"marca atualizada\", \"ano\": 2023 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id)); // Verifica se o ID do carro atualizado é o esperado

        verify(carroRepository, times(1)).save(any(Carro.class));
    }

    @Test
    void deleteCarTest() throws Exception {
        Long id = 1L;
        when(carroRepository.existsById(id)).thenReturn(true);

        mockMvc.perform(delete("/carros/{id}", id))
                .andExpect(status().isNoContent());

        verify(carroRepository, times(1)).deleteById(id);
    }
}
