package com.example.UNICAR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carros")
@Setter
@Getter
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private User proprietario;

    private String marca;
    private String modelo;
    private String placa;

    // Getters e Setters

}
