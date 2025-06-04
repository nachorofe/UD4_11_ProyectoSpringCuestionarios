package org.dam.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * DTO para la creación de nuevas preguntas
 * Puede representar tanto preguntas básicas (Cuestion) como de tipo test (TipoTest)
 *
 * @param enunciado Texto principal de la pregunta
 * @param descripcion Descripción detallada (para Cuestion)
 * @param opciones Lista de opciones (para TipoTest)
 * @param username Nombre de usuario del creador
 */
public record PreguntaRequest(
        @NotBlank(message = "El enunciado es obligatorio")
        @Size(min = 10, message = "El enunciado debe tener al menos 10 caracteres")
        String enunciado,
        @Size(max = 255,message = "La descripción no puede superar los 255 caracteres")
        String descripcion,
        @NotEmpty(message = "Debe incluir al menos una opción")
        List<String> opciones,
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String username) {
}
