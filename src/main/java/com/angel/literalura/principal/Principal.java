package com.angel.literalura.principal;

import com.angel.literalura.classes.CAutor;
import com.angel.literalura.classes.CIdioma;
import com.angel.literalura.classes.CLibros;
import com.angel.literalura.classes.CSearch;
import com.angel.literalura.models.RSearch;
import com.angel.literalura.repository.CAutorRepository;
import com.angel.literalura.repository.CIdiomaRepository;
import com.angel.literalura.repository.CLibrosRepository;
import com.angel.literalura.service.ConsumoAPI;
import com.angel.literalura.service.ConversorDatos;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Principal {
        Scanner scanner = new Scanner(System.in);
        ConversorDatos conversor = new ConversorDatos();
        ConsumoAPI consumoAPI = new ConsumoAPI();
        String urlAPI = "http://gutendex.com/books/?search=";

        private final CLibrosRepository repository;
        private final CIdiomaRepository idiomaRepository;
        private final CAutorRepository autorRepository;


    public Principal(CLibrosRepository repository,
                     CIdiomaRepository idiomaRepository,
                     CAutorRepository autorRepository) {
        this.repository = repository;
        this.idiomaRepository = idiomaRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu(){
        String opcion;
        do{
            System.out.println("""
                    \nBienvenido a Literalura!
                    1.- Buscar por título y agregar a la base de datos los resultados
                    2.- Listar libros registrados
                    3.- Listar autores registrados
                    4.- Listar autores vivos en un determinado año
                    5.- Listar libros por idioma
                    0.- salir
                    Elija la opción a realizar:""");
            opcion = scanner.nextLine();

            switch (opcion){
                case "1":
                    buscarLibroAddBDD();
                    break;
                case "2":
                    mostrarLibrosGuardados();
                    break;
                case "3":
                    mostrarAutoresGuardados();
                    break;
                case "4":
                    mostrarAutoresVivosEn();
                    break;
                case "5":
                    mostrarLibrosPorIdioma();
                    break;

            }


        }while (!opcion.equals("0"));

    }

    @Transactional
    public void buscarLibroAddBDD(){
        System.out.println("\n\nIngresa el titulo del libro a buscar:");
        String libroSearch = scanner.nextLine();
        String libroSearchSin = libroSearch.replace(" ", "%20");

        System.out.println("Url consultada: " + urlAPI+libroSearchSin);

        var response = consumoAPI.obtenerDatos(urlAPI + libroSearchSin);

        var search = conversor.obtenerDatos(response, RSearch.class);

        var searchClass = new CSearch(search);

        for (CLibros l : searchClass.getLibros()) {
            System.out.println("Intento guardar el libro: " + l);

            String titulo = l.getTitulo();
            if (repository.findByTitulo(titulo).isPresent()) {
                System.out.println("El libro: " + titulo + " ya existe en la bdd!");
            } else {
                // Reutilizar idiomas existentes
                Set<CIdioma> idiomasProcesados = l.getIdiomas().stream()
                        .map(idioma -> idiomaRepository.findBySiglas(idioma.getSiglas())
                                .orElseGet(() -> idiomaRepository.save(idioma)))
                        .collect(Collectors.toSet());
                idiomasProcesados.forEach(idioma -> idioma.getLibros().add(l));
                l.setIdiomas(idiomasProcesados);

                // Reutilizar autores existentes
                Set<CAutor> autoresProcesados = l.getAutores().stream()
                        .map(autor -> autorRepository.findByNombre(autor.getNombre())
                                .orElseGet(() -> autorRepository.save(autor)))
                        .collect(Collectors.toSet());
                autoresProcesados.forEach(autor -> autor.getLibros().add(l));
                l.setAutores(autoresProcesados);

//                System.out.println("Datos del libro antes de guardar-----------");
//                System.out.println(l);
                try {
                    repository.save(l);
                    System.out.println("Se guardó el libro: "+ l);
                } catch (DataIntegrityViolationException e) {
                    System.out.println("No se pudo guardar, registro duplicado!");
                }

            }


        }

        if (searchClass.getCount() != 0) {
            System.out.println("Los resultados son: " + searchClass);

        } else {
            System.out.println("No hay resultados");
        }

    }

    public void mostrarLibrosGuardados (){
        List<CLibros> libros = repository.findAll();

        System.out.println("\n\nLibros guardados en la Base de Datos");
        libros.stream()
                .forEach(System.out::println);
    }
    public void mostrarAutoresGuardados (){
        List<CAutor> autores = autorRepository.findAll();
        System.out.println("\n\nAutores guardados en la Base de Datos");


        autores.stream()
                .forEach(System.out::println);
    }
    public void mostrarAutoresVivosEn (){
        System.out.println("\nPor favor, indica año en el que quieres consultar: ");
        var ano = scanner.nextLine();

        List<CAutor> autores = autorRepository.findByFechaVivo(ano);
        System.out.println("\n\nAutores vivos en: "+ano);

        autores.stream()
                .forEach(System.out::println);
    }
    public void mostrarLibrosPorIdioma (){

        System.out.println("\nPor favor, indica las siglas del idioma que quieres consultar: ");
        var siglas = scanner.nextLine();

        List<CLibros> libros = repository.findByIdiomasSiglas(siglas);
        System.out.println("\n\nLibros en: "+siglas);


        libros.stream()
                .forEach(System.out::println);
    }


}
