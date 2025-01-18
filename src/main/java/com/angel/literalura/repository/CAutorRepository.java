package com.angel.literalura.repository;

import com.angel.literalura.classes.CAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CAutorRepository extends JpaRepository<CAutor,Long> {
    Optional<CAutor> findByNombre(String nombre);
    Optional<CAutor> findById(Long id);
    @Query("SELECT a FROM CAutor a WHERE a.fechaFall > :fecha AND a.fechaNac <= :fecha")
    List<CAutor> findByFechaVivo(String fecha);
}
