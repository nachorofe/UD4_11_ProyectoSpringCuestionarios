package org.dam.dto;

import org.dam.entities.Opcion;

/**
 * DTO para representar una opción de una pregunta de tipo test en las respuestas API
 * Contiene información básica de la opción: id, texto y si es correcta
 */
public record OpcionResponse(
        Long idOpcion,
        String texto,
        boolean correcta) {

    /**
     * Método factory para crear un OpcionResponse a partir de una entidad Opcion
     * @param opcion Entidad Opcion del modelo
     * @return DTO OpcionResponse
     */
    public static OpcionResponse of(Opcion opcion) {
        return new OpcionResponse(
                opcion.getIdOpcion(),
                opcion.getTexto(),
                opcion.isCorrecta());
    }
}
