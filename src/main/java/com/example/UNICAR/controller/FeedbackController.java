package com.example.UNICAR.controller;

import com.example.UNICAR.model.Feedback;
import com.example.UNICAR.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Endpoint para listar todos os feedbacks
    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Endpoint para buscar um feedback por ID
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        // Retorna 200 OK se o feedback for encontrado, caso contrário retorna 404 Not Found
        return feedback.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar um novo feedback
    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // Endpoint para atualizar um feedback existente
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        // Verifica se o feedback existe
        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        feedback.setId(id); // Atualiza o ID do feedback
        Feedback updatedFeedback = feedbackRepository.save(feedback); // Salva o feedback atualizado
        return ResponseEntity.ok(updatedFeedback); // Retorna o feedback atualizado
    }

    // Endpoint para deletar um feedback por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        // Verifica se o feedback existe
        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        feedbackRepository.deleteById(id); // Deleta o feedback
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // Endpoint para buscar feedbacks por ID de viagem
    @GetMapping("/viagem/{viagemId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByViagemId(@PathVariable Long viagemId) {
        List<Feedback> feedbacks = feedbackRepository.findByViagemId(viagemId);
        return ResponseEntity.ok(feedbacks);
    }
}
