package com.example.UNICAR.controller;

import com.example.UNICAR.model.Carro;
import com.example.UNICAR.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    // Endpoint para listar todos os carros
    @GetMapping
    public List<Carro> getAllCars() {
        return carroRepository.findAll();
    }

    // Endpoint para buscar um carro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarById(@PathVariable Long id) {
        Optional<Carro> carro = carroRepository.findById(id);
        // Retorna 200 OK se o carro for encontrado, caso contrário retorna 404 Not Found
        return carro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar um novo carro
    @PostMapping
    public Carro createCar(@RequestBody Carro carro) {
        return carroRepository.save(carro);
    }

    // Endpoint para atualizar um carro existente
    @PutMapping("/{id}")
    public ResponseEntity<Carro> updateCar(@PathVariable Long id, @RequestBody Carro carro) {
        // Verifica se o carro existe
        if (!carroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carro.setId(id); // Atualiza o ID do carro
        Carro updatedCar = carroRepository.save(carro); // Salva o carro atualizado
        return ResponseEntity.ok(updatedCar); // Retorna o carro atualizado
    }

    // Endpoint para deletar um carro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        // Verifica se o carro existe
        if (!carroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carroRepository.deleteById(id); // Deleta o carro
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
