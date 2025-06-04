package org.dam.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPregunta;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private String enunciado;

    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Usuario autor;

    @ManyToMany
    @JoinTable(
            name="PreguntaEtiqueta",
            joinColumns = @JoinColumn(name="idPregunta"),
            inverseJoinColumns = @JoinColumn(name="idEtiqueta")
        )
    private Set<Etiqueta> etiquetas = new HashSet<>();

    // Constructores

    public Pregunta() {
    }

    public Pregunta(Long idPregunta, LocalDateTime fechaCreacion, String enunciado, Usuario autor) {
        this.idPregunta = idPregunta;
        this.fechaCreacion = fechaCreacion;
        this.enunciado = enunciado;
        this.autor = autor;
    }


    // Getters y setters

    public Long getIdPregunta() { return idPregunta; }
    public void setIdPregunta(Long idPregunta) { this.idPregunta = idPregunta; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public Set<Etiqueta> getEtiquetas() { return etiquetas; }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Pregunta pregunta = (Pregunta) o;
        return getIdPregunta() != null && Objects.equals(getIdPregunta(), pregunta.getIdPregunta());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
