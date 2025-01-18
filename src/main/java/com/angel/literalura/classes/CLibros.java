package com.angel.literalura.classes;

import com.angel.literalura.models.RLibros;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")

public class CLibros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;
//    @ManyToMany(mappedBy = "libros")
//    private List<CAutor> autores;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libros_id"),
            inverseJoinColumns = @JoinColumn(name = "autores_id")
    )
    private Set<CAutor> autores = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_idiomas",
            joinColumns = @JoinColumn(name = "libros_id"),
            inverseJoinColumns = @JoinColumn(name = "idiomas_id")
    )
    private Set<CIdioma> idiomas = new HashSet<>();
    private int numeroDescargas;

    public CLibros() {
    }

    public CLibros(RLibros libro) {
        this.titulo = libro.titulo();
        this.autores = libro.autores().stream()
                .map(a -> new CAutor(a, this))
                .collect(Collectors.toSet());
        this.idiomas = libro.idioma().stream()
                .map(s -> new CIdioma(s, this))
                .collect(Collectors.toSet());
        this.numeroDescargas = libro.numeroDescargas();
    }

    @Override
    public String toString() {
        return "CLibros{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", idiomas=" + idiomas +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<CAutor> getAutores() {
        return autores;
    }

    public void setAutores(Set<CAutor> autores) {
        this.autores = autores;
    }

    public Set<CIdioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<CIdioma> idiomas) {
        this.idiomas = idiomas;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}

