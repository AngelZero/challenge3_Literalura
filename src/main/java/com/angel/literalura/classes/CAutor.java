package com.angel.literalura.classes;


import com.angel.literalura.models.RAutor;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")

public class CAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private String fechaNac;
    private String fechaFall;

//    @ManyToMany(mappedBy = "autores")
//    private List<CLibros> libros;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<CLibros> libros = new HashSet<>();


    public CAutor() {
    }

    public CAutor(RAutor autor, CLibros libro) {
        this.nombre = autor.nombre();
        this.fechaNac = autor.fechaNac();
        this.fechaFall = autor.fechaFall();
        this.getLibros().add(libro);
    }

    @Override
    public String toString() {
        return "CAutor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", fechaFall='" + fechaFall + '\'' +
                //", libros=" + libros +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getFechaFall() {
        return fechaFall;
    }

    public void setFechaFall(String fechaFall) {
        this.fechaFall = fechaFall;
    }

    public Set<CLibros> getLibros() {
        return libros;
    }

    public void setLibros(Set<CLibros> libros) {
        this.libros = libros;
    }
}
