package com.example.UNICAR.repository;

import com.example.UNICAR.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Declara um método para encontrar feedbacks por ID de viagem
    List<Feedback> findByViagemId(Long viagemId);
}
