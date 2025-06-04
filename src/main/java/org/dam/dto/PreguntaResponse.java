package org.dam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.dam.entities.Cuestion;
import org.dam.entities.Pregunta;
import org.dam.entities.TipoTest;
import org.dam.entities.Etiqueta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO principal para representar preguntas en las respuestas API
 * Usa JsonInclude para omitir campos nulos en la respuesta JSON
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreguntaResponse(
        Long idPregunta,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime fechaCreacion,
        String enunciado,
        String descripcion,
        List<OpcionResponse> opciones,
        String etiquetas) {

    // Constructor alternativo para preguntas básicas (Cuestion)
    public PreguntaResponse(Long idPregunta, LocalDateTime fechaCreacion, String enunciado, String descripcion, String etiquetas) {
        this(idPregunta, fechaCreacion, enunciado, descripcion, null, etiquetas);
    }

    // Constructor alternativo para preguntas de tipo test (TipoTest)
    public PreguntaResponse(Long idPregunta, LocalDateTime fechaCreacion, String enunciado, List<OpcionResponse> opciones, String etiquetas) {
        this(idPregunta, fechaCreacion, enunciado, null, opciones, etiquetas);
    }

    /**
     * Factory method para crear respuesta a partir de una Cuestion
     * @param cuestion Pregunta básica
     * @return DTO PreguntaResponse
     */
    public static PreguntaResponse of(Cuestion cuestion) {
        return new PreguntaResponse(
                cuestion.getIdPregunta(),
                cuestion.getFechaCreacion(),
                cuestion.getEnunciado(),
                cuestion.getDescripcion(),
                cuestion.getEtiquetas().isEmpty() ? null :
                        cuestion.getEtiquetas()
                                .stream()
                                .map(Etiqueta::getNombre)
                                .collect(Collectors.joining(", "))
        );
    }

    /**
     * Factory method para crear respuesta a partir de un TipoTest
     * @param tipoTest Pregunta de tipo test
     * @return DTO PreguntaResponse
     */
    public static PreguntaResponse of(TipoTest tipoTest) {
        return new PreguntaResponse(
                tipoTest.getIdPregunta(),
                tipoTest.getFechaCreacion(),
                tipoTest.getEnunciado(),
                tipoTest.getOpciones()
                        .stream()
                        .map(OpcionResponse::of)
                        .toList(),
                tipoTest.getEtiquetas().isEmpty() ? null :
                        tipoTest.getEtiquetas()
                                .stream()
                                .map(Etiqueta::getNombre)
                                .collect(Collectors.joining(", ")));
    }

    /**
     * Factory method genérico que decide qué tipo de respuesta crear
     * según el tipo de pregunta recibida
     * @param pregunta Pregunta (puede ser Cuestion o TipoTest)
     * @return DTO PreguntaResponse adecuado
     */
    public static PreguntaResponse of(Pregunta pregunta) {
        if (pregunta instanceof Cuestion cuestion)
            return PreguntaResponse.of(cuestion);
        return PreguntaResponse.of((TipoTest) pregunta);
    }
}
