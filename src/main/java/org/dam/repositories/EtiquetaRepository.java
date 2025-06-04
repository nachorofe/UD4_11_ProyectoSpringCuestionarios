package org.dam.repositories;

import org.dam.entities.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
    /**
     * Busca una etiqueta por su nombre
     * @param nombre Nombre de la etiqueta
     * @return Optional con la etiqueta si existe
     */
    Optional<Etiqueta> findByNombre(String nombre);
}