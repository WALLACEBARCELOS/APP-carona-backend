package com.example.UNICAR.controller;

import com.example.UNICAR.model.Reserva; // Classe modelo para Reservas
import com.example.UNICAR.repository.ReservaRepository; // Repositório para a classe Reserva
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository; // Injeta o repositório de Reserva

    // Endpoint para listar todas as reservas
    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll(); // Retorna a lista de todas as reservas
    }

    // Endpoint para buscar uma reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id); // Busca a reserva pelo ID
        // Retorna 200 OK se a reserva for encontrada, caso contrário retorna 404 Not Found
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar uma nova reserva
    @PostMapping
    public Reserva createReserva(@RequestBody Reserva reserva) {
        return reservaRepository.save(reserva); // Salva a nova reserva no banco de dados
    }

    // Endpoint para atualizar uma reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        // Verifica se a reserva existe
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a reserva não existir
        }
        reserva.setId(id); // Define o ID da reserva a ser atualizada
        Reserva updatedReserva = reservaRepository.save(reserva); // Salva a reserva atualizada
        return ResponseEntity.ok(updatedReserva); // Retorna a reserva atualizada com 200 OK
    }

    // Endpoint para deletar uma reserva por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        // Verifica se a reserva existe
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a reserva não existir
        }
        reservaRepository.deleteById(id); // Deleta a reserva do banco de dados
        return ResponseEntity.noContent().build(); // Retorna 204 No Content indicando sucesso
    }

    // Endpoint para confirmar uma reserva
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Reserva> confirmarReserva(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se a reserva não for encontrada
        }
        Reserva reservaToUpdate = reserva.get(); // Obtém a reserva
        reservaToUpdate.setConfirmada(true); // Marca a reserva como confirmada
        Reserva updatedReserva = reservaRepository.save(reservaToUpdate); // Salva a reserva atualizada
        return ResponseEntity.ok(updatedReserva); // Retorna a reserva atualizada com 200 OK
    }
}
