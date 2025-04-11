package com.javier.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javier.demo.model.Libros;
import com.javier.demo.service.LibroServices;


/*
 * Controlador de datos
 * Hecho el dia 09/04/2025
 * v1
 * 10/04/2025
 * v1.1 añadido endpoints restantes (POST, PUT, DELETE)
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Permite la conexion con el puerto 4200

public class LibrosController {

    @Autowired
    private LibroServices librosService;

    // Obtiene todos los libros
    @GetMapping("/api/libros")
    public List<Libros> verLibros() {
        return librosService.verLibros();
    }

    @GetMapping("/api/libros/{id}")
    public Libros verLibrosId(@PathVariable int id) {
        return librosService.verLibrosId(id);
    }

    @GetMapping("/api/libros/")
    public Libros crearLibro(@RequestBody Libros libronuevo) {
        return librosService.crearLibro(libronuevo);
    }

    // @GetMapping("/api/libros/{id}")
    // public Libros actuaLibro(@PathVariable int id) {
    //     return librosService.actualizarLibro(id);
    // }

    // @GetMapping("/api/libros/{id}")
    // public String eliminarLibro(@PathVariable int id) {
    //     librosService.eliminarLibro(id);
    //     return "";
    // }

}
