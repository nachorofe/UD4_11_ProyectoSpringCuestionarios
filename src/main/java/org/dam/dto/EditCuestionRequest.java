package org.dam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para la edición de una pregunta básica (Cuestion)
 * Contiene los campos editables: enunciado y descripción
 * Se usa en las operaciones de actualización (PUT/PATCH)
 */
public record EditCuestionRequest(
        @NotBlank(message = "El enunciado no puede estár vacío")
        String enunciado,
        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion) {
}
