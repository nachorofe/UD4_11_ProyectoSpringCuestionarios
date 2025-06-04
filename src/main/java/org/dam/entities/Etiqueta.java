package org.dam.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;
import java.util.Objects;
import java.util.Set;

@Entity
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtiqueta;
    private String nombre;

    @ManyToMany(mappedBy = "etiquetas")
    private Set<Pregunta> preguntas;

    public Etiqueta() {
    }

    public Etiqueta(Long idEtiqueta, String nombre) {
        this.idEtiqueta = idEtiqueta;
        this.nombre = nombre;
    }

    // Getters y setters

    public Long getIdEtiqueta() { return idEtiqueta; }
    public void setIdEtiqueta(Long idEtiqueta) { this.idEtiqueta = idEtiqueta; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return getIdEtiqueta() != null && Objects.equals(getIdEtiqueta(), etiqueta.getIdEtiqueta());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Etiqueta{" +
                "idEtiqueta=" + idEtiqueta +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
