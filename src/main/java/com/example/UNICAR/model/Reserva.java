package com.example.UNICAR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Setter @Getter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "User_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "viagem_id")
    private Viagem viagem;

    private Integer assentosReservados;

    private Boolean confirmada; // Campo para indicar se a reserva está confirmada

}
