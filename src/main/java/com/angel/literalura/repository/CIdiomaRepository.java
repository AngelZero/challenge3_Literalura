package com.angel.literalura.repository;

import com.angel.literalura.classes.CIdioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CIdiomaRepository extends JpaRepository <CIdioma,Long> {
    Optional<CIdioma> findBySiglas(String siglas);
}
