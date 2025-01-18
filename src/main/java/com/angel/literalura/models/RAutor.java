package com.angel.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record RAutor(

        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaNac,
        @JsonAlias("death_year") String fechaFall
        //@JsonAlias("") List<Libros> libros

) {
}
