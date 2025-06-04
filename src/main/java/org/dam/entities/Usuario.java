package org.dam.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String login;
    private String password;
    private String email;
    private String nombreCompleto;

    @OneToMany(mappedBy = "autor")
    private List<Pregunta> preguntas = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Long idUsuario, String login, String password, String email, String nombreCompleto, List<Pregunta> preguntas) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.password = password;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.preguntas = preguntas;
    }

    // Getters y setters

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public List<Pregunta> getPreguntas() { return preguntas; }

    // Métodos de ayuda
    public void addPregunta(Pregunta p) {
        preguntas.add(p);
        p.setAutor(this);
    }

    public void removePregunta(Pregunta p) {
        preguntas.remove(p);
        p.setAutor(null);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", login='" + login + '\'' +
                ", password='[PROTEGIDO]'" +
                ", email='" + email + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                '}';
    }
}
