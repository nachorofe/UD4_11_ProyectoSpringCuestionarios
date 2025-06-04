package org.dam.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TipoTest extends Pregunta {

    // Imprescindible definir el CascadeType ALL para que la persistencia de las opciones se propague automáticamente
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Opcion> opciones = new ArrayList<>();

    public TipoTest() {
    }

    public TipoTest(Long idPregunta, LocalDateTime fechaCreacion, String enunciado, Usuario propietario) {
        super(idPregunta, fechaCreacion, enunciado, propietario);
    }

    public List<Opcion> getOpciones() { return opciones; }

    public void addOpcion(Opcion opcion) {
        opciones.add(opcion);
        opcion.setPregunta(this);
    }

    public void removeOpcion(Opcion opcion) {
        opciones.remove(opcion);
    }

    public void removeOpcionById(Long id) {
        opciones.removeIf(o -> o.getIdOpcion().equals(id));
    }

    @Override
    public String toString() {
        return "TipoTest{" +
                "idPregunta=" + getIdPregunta() +
                ", fechaCreacion=" + getFechaCreacion() +
                ", enunciado='" + getEnunciado() + '\'' +
                ", opciones=" + opciones.size() +
                '}';
    }
}
