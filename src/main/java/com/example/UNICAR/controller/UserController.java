package com.example.UNICAR.controller;

import com.example.UNICAR.model.User;
import com.example.UNICAR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gerenciar as operações relacionadas a Usuários.
 */
@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Lista todos os usuários.
     *
     * @return Lista de usuários.
     */
    @GetMapping
    public List<User> listar() {
        return userRepository.findAll();
    }

    /**
     * Obtém um usuário pelo ID.
     *
     * @param id ID do usuário.
     * @return Usuário encontrado ou uma resposta de não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> obter(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo usuário.
     *
     * @param user Dados do usuário a ser criado.
     * @return Usuário criado.
     */
    @PostMapping
    public User criar(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id   ID do usuário a ser atualizado.
     * @param user Dados atualizados do usuário.
     * @return Usuário atualizado ou uma resposta de não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizar(@PathVariable Long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        return ResponseEntity.ok(userRepository.save(user));
    }

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id ID do usuário a ser deletado.
     * @return Resposta de sucesso ou não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

    /**
     * Lista todos os motoristas.
     *
     * @return Lista de motoristas.
     */
