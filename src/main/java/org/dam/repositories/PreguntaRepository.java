package org.dam.repositories;

import jakarta.transaction.Transactional;
import org.dam.entities.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    /**
     * findAllWithOpcionesAndEtiquetas
     * Busca todas las preguntas con sus relaciones (opciones y etiquetas) cargadas
     * @param pageable Configuración de paginación
     * @return Página de preguntas con relaciones cargadas
     */
    @Query("SELECT p FROM Pregunta p LEFT JOIN FETCH p.opciones LEFT JOIN FETCH p.etiquetas")
    Page<Pregunta> findAllWithOpcionesAndEtiquetas(Pageable pageable);

    /**
     * findByIdWithOpcionesAndEtiquetas
     * Busca una pregunta por ID con sus relaciones (opciones y etiquetas) cargadas
     * @param id ID de la pregunta
     * @return Optional con la pregunta si existe
     */
    @Query("SELECT DISTINCT p FROM Pregunta p LEFT JOIN FETCH p.opciones LEFT JOIN FETCH p.etiquetas WHERE p.idPregunta = :id")
    Optional<Pregunta> findByIdWithOpcionesAndEtiquetas(@Param("id") Long id);

    /**
     * existsByIdAndPreguntaType
     * Verifica si existe una pregunta de un tipo específico por ID
     * @param preguntaType Tipo de pregunta (Cuestion.class o TipoTest.class)
     * @param id ID de la pregunta
     * @return true si existe, false en caso contrario
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Pregunta p " +
            "WHERE TYPE(p) = :preguntaType AND p.idPregunta = :id")
    boolean existsByIdAndPreguntaType(
            @Param("preguntaType") Class<?> preguntaType,
            @Param("id") Long id
    );

    /**
     * toggleOpcionCorrecta
     * Emplea un query nativo para cambiar el estado de "correcta" de una opción (toggle)
     * Cambia el estado de "correcta" de una opción (toggle)
     * @param idPregunta ID de la pregunta de tipo test
     * @param idOpcion ID de la opción a modificar
     * @return Número de registros afectados
     */
    @Modifying
    @Transactional
    @Query(value = """
            UPDATE "Opcion" SET correcta = NOT correcta WHERE "idOpcion" = :idOpcion AND "idPregunta" = :idPregunta 
            """, nativeQuery = true)
    int toggleOpcionCorrecta(@Param("idPregunta") Long idPregunta, @Param("idOpcion") Long idOpcion);
}
