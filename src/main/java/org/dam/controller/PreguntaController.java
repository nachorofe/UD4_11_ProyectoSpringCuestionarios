package org.dam.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.dam.dto.EditCuestionRequest;
import org.dam.dto.PreguntaRequest;
import org.dam.dto.PreguntaResponse;
import org.dam.entities.*;
import org.dam.repositories.EtiquetaRepository;
import org.dam.repositories.PreguntaRepository;
import org.dam.repositories.UsuarioRepository;

import org.dam.service.PreguntaService;
import org.hibernate.engine.internal.ImmutableEntityEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pregunta/")
@Validated
public class PreguntaController {

    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaservice) {
        this.preguntaService = preguntaservice;
    }

    @PostMapping("/new/cuestion")
    public ResponseEntity<PreguntaResponse> newCuestion(@Valid @RequestBody PreguntaRequest preguntaRequest) {
        PreguntaResponse response = preguntaService.crearCuestion(preguntaRequest);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/pregunta/{id}")
                .build(response.idPregunta());

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/new/tipotest")
    public ResponseEntity<PreguntaResponse> newTipoTest(@RequestBody PreguntaRequest request) {
        PreguntaResponse response = preguntaService.crearTipoTest(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/pregunta/{id}")
                .build(response.idPregunta());  // 👈 Usa el getter correcto según tu record

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/")
    public Page<PreguntaResponse> getAll(@PageableDefault(page=0, size=5, sort = "fechaCreacion") Pageable pageable) {
        return preguntaService.mostrarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public PreguntaResponse getById(@PathVariable Long id) {
        return preguntaService.obtenerPorId(id);
    }

    @PutMapping("/cuestion/{id}")
    public ResponseEntity<PreguntaResponse> editCuestion(
            @Valid @RequestBody EditCuestionRequest editCuestionRequest,
            @PathVariable Long id) {

        PreguntaResponse response = preguntaService.editarCuestion(editCuestionRequest, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/tipotest/{id}/add/{opcion}")
    public ResponseEntity<PreguntaResponse> addOpcionToTipoTest(
            @PathVariable
            @NotBlank(message = "La opción no puede estar vacía")
            String opcion,
            @PathVariable Long id) {
        PreguntaResponse response = preguntaService.anadirOpcionATipoTest(id, opcion);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tipotest/{id}/del/{opcion_id}")
    public ResponseEntity<PreguntaResponse> deleteOpcionFromTipoTest(
            @PathVariable("opcion_id") @Positive(message = "El ID debe ser mayor que 0") Long opcionId,
            @PathVariable @Positive(message = "El ID de la pregunta debe ser mayor que 0") Long id) {
        PreguntaResponse response = preguntaService.eliminarOpcionDeTipoTest(id, opcionId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/tipotest/{id}/toggle/{opcion_id}")
    public ResponseEntity<PreguntaResponse> toggleOpcionCorrecta(
            @PathVariable("opcion_id") @Positive(message = "El ID de la opción debe ser mayor que cero") Long opcionId,
            @PathVariable @Positive(message = "El ID de la pregunta debe ser mayor que cero") Long id) {
        PreguntaResponse response = preguntaService.toggleOpcionCorrecta(id, opcionId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePregunta(
            @PathVariable @Positive(message = "El ID de la pregunta debe ser mayor que 0") Long id) {
        preguntaService.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/etiqueta/add/{etiqueta}")
    public ResponseEntity<PreguntaResponse> addEtiqueta(
            @PathVariable @Positive(message = "El ID de la pregunta debe ser mayor que 0") Long id,
            @PathVariable @NotBlank(message = "La etiqueta no puede estar vacía")
                    @Size(max = 30,message = "La etiqueta debe tener más de 30 caracteres")
            String etiqueta) {
        PreguntaResponse response = preguntaService.anadirEtiqueta(id, etiqueta);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/etiqueta/del/{etiqueta}")
    public ResponseEntity<PreguntaResponse> deleteEtiqueta(
            @PathVariable @Positive(message = "El id de la pregunta debe ser mayor que 0") Long id,
            @PathVariable @NotBlank(message = "La etiqueta no puede estar vacía")
            @Size(max = 30,message = "La etiqueta debe tener más de 30 caracteres")
            String etiqueta) {
        PreguntaResponse response = preguntaService.eliminarEtiqueta(id, etiqueta);
        return ResponseEntity.ok(response);
    }

}
