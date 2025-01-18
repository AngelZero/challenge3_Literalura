package com.angel.literalura.repository;

import com.angel.literalura.classes.CLibros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CLibrosRepository extends JpaRepository<CLibros,Long> {
    Optional<CLibros> findByTitulo(String titulo);
    List<CLibros> findByIdiomasSiglas(String siglas);
}
