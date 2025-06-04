package org.dam.service;

import org.dam.dto.EditCuestionRequest;
import org.dam.dto.PreguntaRequest;
import org.dam.dto.PreguntaResponse;
import org.dam.entities.*;
import org.dam.repositories.EtiquetaRepository;
import org.dam.repositories.PreguntaRepository;
import org.dam.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EtiquetaRepository etiquetaRepository;

    public PreguntaService(PreguntaRepository preguntaRepository,
                           UsuarioRepository usuarioRepository,
                           EtiquetaRepository etiquetaRepository) {
        this.preguntaRepository = preguntaRepository;
        this.usuarioRepository = usuarioRepository;
        this.etiquetaRepository = etiquetaRepository;
    }

    public PreguntaResponse crearCuestion(PreguntaRequest request) {
        Usuario propietario = usuarioRepository.findByLogin(request.username()).orElse(null);

        Cuestion cuestion = new Cuestion();
        cuestion.setAutor(propietario);
        cuestion.setEnunciado(request.enunciado() != null ? request.enunciado() : "Sin enunciado");
        cuestion.setDescripcion(request.descripcion());

        cuestion = preguntaRepository.save(cuestion);
        return PreguntaResponse.of(cuestion);
    }

    public PreguntaResponse crearTipoTest(PreguntaRequest request) {
        Usuario propietario = usuarioRepository.findByLogin(request.username()).orElse(null);

        TipoTest tipoTest = new TipoTest();
        tipoTest.setAutor(propietario);
        tipoTest.setEnunciado(request.enunciado() != null ? request.enunciado() : "Sin enunciado");
        tipoTest.setFechaCreacion(LocalDateTime.now());

        request.opciones().stream()
                .map(texto -> {
                    Opcion opcion = new Opcion();
                    opcion.setTexto(texto);
                    return opcion;
                })
                .forEach(tipoTest::addOpcion);

        tipoTest = preguntaRepository.save(tipoTest);
        return PreguntaResponse.of(tipoTest);
    }

    public Page<PreguntaResponse> mostrarPaginado(Pageable pageable) {
        Page<Pregunta> result = preguntaRepository.findAllWithOpcionesAndEtiquetas(pageable);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron preguntas");
        }

        return result.map(PreguntaResponse::of);
    }

    public PreguntaResponse obtenerPorId(Long id) {
        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(id)
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada".formatted(id)));
    }

    public PreguntaResponse editarCuestion(EditCuestionRequest editCuestionRequest, Long id) {
        if (!preguntaRepository.existsByIdAndPreguntaType(Cuestion.class, id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pregunta con ID %d no encontrada".formatted(id));
        }

        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(id)
                .map(Cuestion.class::cast)
                .map(cuestion -> {
                    cuestion.setEnunciado(editCuestionRequest.enunciado());
                    cuestion.setDescripcion(editCuestionRequest.descripcion());
                    return preguntaRepository.save(cuestion);
                })
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada al intentar editarla".formatted(id)
                ));
    }

    public PreguntaResponse anadirOpcionATipoTest(Long id, String textoOpcion) {
        if (!preguntaRepository.existsByIdAndPreguntaType(TipoTest.class, id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pregunta con ID %d no encontrada".formatted(id));
        }

        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(id)
                .map(TipoTest.class::cast)
                .map(tipoTest -> {
                    Opcion nuevaOpcion = new Opcion();
                    nuevaOpcion.setTexto(textoOpcion);
                    tipoTest.addOpcion(nuevaOpcion);
                    return preguntaRepository.save(tipoTest);
                })
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada al intentar añadir opción".formatted(id)));
    }

    public PreguntaResponse eliminarOpcionDeTipoTest(Long idPregunta, Long idOpcion) {
        if (!preguntaRepository.existsByIdAndPreguntaType(TipoTest.class, idPregunta)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pregunta con ID %d no encontrada".formatted(idPregunta));
        }

        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(idPregunta)
                .map(TipoTest.class::cast)
                .map(tipoTest -> {
                    tipoTest.removeOpcionById(idOpcion);
                    return preguntaRepository.save(tipoTest);
                })
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada al intentar eliminar opción".formatted(idPregunta)));
    }

    public PreguntaResponse toggleOpcionCorrecta(Long idPregunta, Long idOpcion) {
        if (!preguntaRepository.existsByIdAndPreguntaType(TipoTest.class, idPregunta)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pregunta con ID %d no encontrada".formatted(idPregunta));
        }

        int actualizados = preguntaRepository.toggleOpcionCorrecta(idPregunta, idOpcion);
        if (actualizados == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontró la opción %d en la pregunta %d".formatted(idOpcion, idPregunta));
        }

        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(idPregunta)
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada después de actualizar".formatted(idPregunta)));
    }

    public void eliminarPregunta(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pregunta con ID %d no encontrada".formatted(id));
        }

        preguntaRepository.deleteById(id);
    }

    @PutMapping("/{id}/etiqueta/add/{etiqueta}")
    public ResponseEntity<PreguntaResponse> addEtiqueta(
            @PathVariable Long id,
            @PathVariable String etiqueta) {

        Etiqueta nuevaEtiqueta = etiquetaRepository.findByNombre(etiqueta)
                .orElseGet(() -> {
                    Etiqueta e = new Etiqueta();
                    e.setNombre(etiqueta);
                    return etiquetaRepository.save(e);
                });

        return ResponseEntity.of(preguntaRepository.findByIdWithOpcionesAndEtiquetas(id)
                .map(pregunta -> {
                    pregunta.getEtiquetas().add(nuevaEtiqueta);
                    return preguntaRepository.save(pregunta);
                })
                .map(PreguntaResponse::of));
    }

    public PreguntaResponse anadirEtiqueta(Long idPregunta, String nombreEtiqueta) {
        Etiqueta etiqueta = etiquetaRepository.findByNombre(nombreEtiqueta)
                .orElseGet(() -> {
                    Etiqueta nueva = new Etiqueta();
                    nueva.setNombre(nombreEtiqueta);
                    return etiquetaRepository.save(nueva);
                });

        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(idPregunta)
                .map(pregunta -> {
                    pregunta.getEtiquetas().add(etiqueta);
                    return preguntaRepository.save(pregunta);
                })
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada".formatted(idPregunta)));
    }

    public PreguntaResponse eliminarEtiqueta(Long idPregunta, String nombreEtiqueta) {
        Optional<Etiqueta> etiquetaExistente = etiquetaRepository.findByNombre(nombreEtiqueta);

        if (etiquetaExistente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etiqueta no encontrada");
        }

        return preguntaRepository.findByIdWithOpcionesAndEtiquetas(idPregunta)
                .map(pregunta -> {
                    pregunta.getEtiquetas().removeIf(e -> e.getNombre().equalsIgnoreCase(nombreEtiqueta));
                    return preguntaRepository.save(pregunta);
                })
                .map(PreguntaResponse::of)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pregunta con ID %d no encontrada".formatted(idPregunta)));
    }

// Thymeleaf
    public List<PreguntaResponse> obtenerTodas() {
        return preguntaRepository.findAll()
            .stream()
            .map(PreguntaResponse::of)
            .toList();
}
}

