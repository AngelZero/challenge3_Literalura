package com.angel.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<RAutor> autores,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") int numeroDescargas

) {


}
