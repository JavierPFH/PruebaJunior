package com.javier.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/* 
 * Modelo de libros a recibir
 * Hecho el dia 08/04/2025
 * v.1
 */

@Entity
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se auto genera el Id del libro.
    private int id;

    @NotNull(message = "El título es obligatorio.") // Asegura que no acepta valores nulos
    private String titulo;

    @NotNull(message = "El autor el obligatorio.") // Asegura que no acepta valores nulos
    private String autor;

    @NotNull(message = "El año de publicacion es obligatorio.") // Asegura que no acepta valores nulos
    private int anioPublicacion;

    @NotNull(message = "La fecha de lectura.") // Asegura que no acepta valores nulos
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Atlantic/Canary")
    private Date dateRead;

    // Por si acaso
    public Libros() {}

    // Contructor de los campos
    public Libros(int id, String titulo, String autor, int anioPublicacion, Date dateRead) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.dateRead = dateRead;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Date getDateRead() {
        return dateRead;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

}
