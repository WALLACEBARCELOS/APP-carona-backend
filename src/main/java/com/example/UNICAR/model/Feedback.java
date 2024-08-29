package com.example.UNICAR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "feedbacks")
@Setter @Getter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "viagem_id", nullable = false)
    private Viagem viagem;
    @ManyToOne
    @JoinColumn(name = "User_id", nullable = false)
    private User usuario;
    private String comentario;
    private int nota;

}
