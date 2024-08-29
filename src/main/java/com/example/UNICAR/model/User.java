package com.example.UNICAR.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @OneToMany(mappedBy = "proprietario")
    private List<Carro> carros;

    @OneToMany(mappedBy = "usuario")
    private List<Viagem> viagens;

    // Campos específicos para motoristas
    //private String numeroCarteira; // Número da carteira de motorista
    //private Boolean ehMotorista = false; // Indica se o usuário é um motorista

    // Campos específicos para passageiros
    //private String assentoPreferido; // Assento preferido (se aplicável)
    //private Boolean ehPassageiro = false; // Indica se o usuário é um passageiro

}
