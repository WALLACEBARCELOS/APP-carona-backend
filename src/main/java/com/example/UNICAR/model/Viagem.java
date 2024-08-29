package com.example.UNICAR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "viagens")
@Setter @Getter
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origem;
    private String destino;
    private String horarioPartida;
    private Double preco;


    @ManyToOne
    @JoinColumn(name = "User_id")
    private User  usuario;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @OneToMany(mappedBy = "viagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;

}
