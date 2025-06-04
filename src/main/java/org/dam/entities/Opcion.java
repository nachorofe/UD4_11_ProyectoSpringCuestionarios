package org.dam.entities;

import jakarta.persistence.*;

@Entity
public class Opcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOpcion;
    private String texto;
    private boolean correcta;

    @ManyToOne
    @JoinColumn(name="idPregunta")
    private TipoTest pregunta;

    public Opcion() {
    }

    public Opcion(Long idOpcion, TipoTest pregunta, String texto, boolean correcta) {
        this.idOpcion = idOpcion;
        this.pregunta = pregunta;
        this.texto = texto;
        this.correcta = correcta;
    }

    // Getters y setters

    public Long getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Long idOpcion) {
        this.idOpcion = idOpcion;
    }

    public TipoTest getPregunta() {
        return pregunta;
    }

    public void setPregunta(TipoTest pregunta) {
        this.pregunta = pregunta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }

    public boolean toggle() {
        return correcta = !correcta;
    }

    @Override
    public String toString() {
        return "Opcion{" +
                "idOpcion=" + idOpcion +
                ", texto='" + texto + '\'' +
                ", correcta=" + correcta +
                '}';
    }
}
