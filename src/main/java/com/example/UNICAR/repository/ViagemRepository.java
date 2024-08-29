package com.example.UNICAR.repository;

import com.example.UNICAR.model.Viagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    // Métodos padrão de CRUD (Create, Read, Update, Delete) são fornecidos por JpaRepository.
    // Você pode adicionar métodos personalizados se necessário, por exemplo:
    // List<Ride> findByOrigemAndDestino(String origem, String destino);
}
