package com.example.UNICAR.repository;

import com.example.UNICAR.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    // Métodos padrão de CRUD (Create, Read, Update, Delete) são fornecidos por JpaRepository.
    // Você pode adicionar métodos personalizados se necessário, por exemplo:
    // List<Car> findByMarca(String marca);
}
