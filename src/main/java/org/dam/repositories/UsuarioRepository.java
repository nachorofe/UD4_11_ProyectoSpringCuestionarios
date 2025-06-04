package org.dam.repositories;


import org.dam.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de login
     * @param login Nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByLogin(String login);
}
