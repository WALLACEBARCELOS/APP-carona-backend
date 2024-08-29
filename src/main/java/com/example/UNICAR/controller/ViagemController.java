package com.example.UNICAR.controller;

import com.example.UNICAR.model.Reserva;
import com.example.UNICAR.model.Viagem;
import com.example.UNICAR.repository.ViagemRepository;
import com.example.UNICAR.repository.ReservaRepository; // Importação do repositório de Reserva
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

    @Autowired
    private ViagemRepository viagemRepository; // Injeção do repositório de Viagem

    @Autowired
    private ReservaRepository reservaRepository; // Injeção do repositório de Reserva

    // Endpoint para listar todas as viagens
    @GetMapping
    public List<Viagem> getAllViagens() {
        return viagemRepository.findAll();
    }

    // Endpoint para buscar uma viagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Viagem> getViagemById(@PathVariable Long id) {
        Optional<Viagem> viagem = viagemRepository.findById(id);
        // Retorna 200 OK se a viagem for encontrada, caso contrário retorna 404 Not Found
        return viagem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar uma nova viagem
    @PostMapping
    public Viagem createViagem(@RequestBody Viagem viagem) {
        return viagemRepository.save(viagem);
    }

    // Endpoint para atualizar uma viagem existente
    @PutMapping("/{id}")
    public ResponseEntity<Viagem> updateViagem(@PathVariable Long id, @RequestBody Viagem viagem) {
        // Verifica se a viagem existe
        if (!viagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        viagem.setId(id); // Atualiza o ID da viagem
        Viagem updatedViagem = viagemRepository.save(viagem); // Salva a viagem atualizada
        return ResponseEntity.ok(updatedViagem); // Retorna a viagem atualizada
    }

    // Endpoint para deletar uma viagem por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViagem(@PathVariable Long id) {
        // Verifica se a viagem existe
        if (!viagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        viagemRepository.deleteById(id); // Deleta a viagem
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // Endpoint para listar todas as reservas de uma viagem específica
    @GetMapping("/{viagemId}/reservas")
    public ResponseEntity<List<Reserva>> getReservasByViagemId(@PathVariable Long viagemId) {
        Optional<Viagem> viagem = viagemRepository.findById(viagemId);
        if (viagem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Agora, como o relacionamento está mapeado, você pode acessar as reservas diretamente
        return ResponseEntity.ok(viagem.get().getReservas());
    }


    // Endpoint para adicionar uma reserva a uma viagem específica
    @PostMapping("/{viagemId}/reservas")
    public ResponseEntity<Reserva> addReservaToViagem(@PathVariable Long viagemId, @RequestBody Reserva reserva) {
        Optional<Viagem> viagem = viagemRepository.findById(viagemId);
        if (viagem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reserva.setViagem(viagem.get());
        Reserva savedReserva = reservaRepository.save(reserva);
        return ResponseEntity.ok(savedReserva);
    }

    // Endpoint para cancelar uma reserva dentro de uma viagem
    @DeleteMapping("/{viagemId}/reservas/{reservaId}")
    public ResponseEntity<Void> cancelReserva(@PathVariable Long viagemId, @PathVariable Long reservaId) {
        Optional<Reserva> reserva = reservaRepository.findById(reservaId);
        if (reserva.isEmpty() || !reserva.get().getViagem().getId().equals(viagemId)) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(reservaId);
        return ResponseEntity.noContent().build();
    }
}
