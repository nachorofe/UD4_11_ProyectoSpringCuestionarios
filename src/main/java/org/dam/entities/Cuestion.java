package org.dam.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

@Entity
public class Cuestion extends Pregunta {

    @Column(columnDefinition="text")
    private String descripcion; // Debe contener un texto largo!!!!

    public Cuestion() {
    }

    public Cuestion(Long idPregunta, LocalDateTime fechaCreacion, String enunciado, Usuario propietario, String descripcion) {
        super(idPregunta, fechaCreacion, enunciado, propietario);
        this.descripcion = descripcion;
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Cuestion{" +
                "idPregunta=" + getIdPregunta() +
                ", fechaCreacion=" + getFechaCreacion() +
                ", enunciado='" + getEnunciado() + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
