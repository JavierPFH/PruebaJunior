package com.javier.demo.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import com.javier.demo.model.Libros;
/*
 * Servicio de la indormacion
 * Hecho el dia 10/04/2025
 * v1 - metodo verLibros añadido
 */
@Service
public class LibroServices {
    private List<Libros> leerExcel() {
        List<Libros> listaLibros = new ArrayList<>();
        Libros libro = new Libros();

        try (InputStream is = getClass().getResourceAsStream("/Libros.xlsx")) {   // Abre la pipe al excel
            Workbook wb = WorkbookFactory.create(is);   // guarda el excel completo
            Sheet sheet = wb.getSheetAt(0);     // Obtiene la hoja 1

            for (int i = 1; i < sheet.getLastRowNum(); i++) {   //Skipea la linea de encabezado del excel ("titulo", "id", "autor", "publicacion")
                Row row = sheet.getRow(i);
                if(row == null) continue;   //Salta las lineas vacias

                //Recoge la informacion de la linea
                int id = (int) row.getCell(0).getNumericCellValue();
                String titulo = row.getCell(1).getStringCellValue();
                String autor = row.getCell(2).getStringCellValue();
                int anio = (int) row.getCell(3).getNumericCellValue();

                libro = new Libros(id, titulo, autor, anio);
                listaLibros.add(libro);
            }

            wb.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaLibros;

    }
    public List<Libros> verLibros() {   //Obtiene todos los libros
        return leerExcel();
    }

    public Libros verLibrosId(int id) {
        return leerExcel().stream()
            .filter(libro -> libro.getId() == id)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    public Libros crearLibro() {
        return ;
    }
    // public Libros actualizarLibro(int id) {}

    // public void eliminarLibro(int id) {}
}
