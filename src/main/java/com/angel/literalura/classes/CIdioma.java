package com.angel.literalura.classes;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "idiomas")

public class CIdioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String siglas;

    @ManyToMany(mappedBy = "idiomas", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CLibros> libros = new HashSet<>();

    public CIdioma() {
    }

    public CIdioma(String siglas, CLibros libro) {
        this.siglas = siglas;
        this.getLibros().add(libro);
    }

    @Override
    public String toString() {
        return "CIdioma{" +
                "Id=" + Id +
                ", siglas='" + siglas + '\'' +
                //", libros=" + libros +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public Set<CLibros> getLibros() {
        return libros;
    }

    public void setLibros(Set<CLibros> libros) {
        this.libros = libros;
    }
}
