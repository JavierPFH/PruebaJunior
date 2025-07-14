package com.javier.demo.service;

import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import com.javier.demo.model.Libros;
import com.javier.demo.util.ExcelHandler;

/*
 * Servicio de la informacion
 * Hecho el dia 10/04/2025
 * v1 - metodo verLibros, verLibrosId añadido
 * 12/10/2025
 * v1.1 Refactorizado los metodos verLibros y verLibrosId, añadido actualizarLibro y eliminarLibro 
 */

@Service
public class LibroServices {

    public List<Libros> verLibros() {   //Obtiene todos los libros
        try (Workbook wb = ExcelHandler.abrirExcel()) {

            Sheet sheet = wb.getSheetAt(0);
            return ExcelHandler.leerExcel(sheet);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Libros verLibrosId(int id) { //Obtiene un libro segun el id
        List<Libros> listaLibros = verLibros();
        return listaLibros.stream()
            .filter(libro -> libro.getId() == id)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    public Libros crearLibro(Libros libronuevo) {   //Crea un libro
        try (Workbook wb = ExcelHandler.abrirExcel()) {
            Sheet sheet = wb.getSheetAt(0);

            int nextIdAvailable = sheet.getLastRowNum() + 1; //Proximo Id disponible
            Row newRow = sheet.createRow(nextIdAvailable);

            CreationHelper createHelper = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM//yyyy"));

            newRow.createCell(0).setCellValue(nextIdAvailable);
            newRow.createCell(1).setCellValue(libronuevo.getTitulo());
            newRow.createCell(2).setCellValue(libronuevo.getAutor());    
            newRow.createCell(3).setCellValue(libronuevo.getAnioPublicacion());

            Cell dateCell = newRow.createCell(4);
            dateCell.setCellValue(libronuevo.getDateRead());
            dateCell.setCellStyle(dateStyle);;

            libronuevo.setId(nextIdAvailable);
            ExcelHandler.guardarExcel(wb);
            return libronuevo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Libros actualizarLibro(int id, Libros libroactualizado) {    //Actualiza un libro
        try (Workbook wb = ExcelHandler.abrirExcel()) {
            Sheet sheet = wb.getSheetAt(0);

            CreationHelper createHelper = wb.getCreationHelper();
            CellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM//yyyy"));

            for(Row row: sheet) {
                if (row.getRowNum() == 0) continue; 
                
                if (row.getCell(0).getNumericCellValue() == id) {
                    row.getCell(1).setCellValue(libroactualizado.getTitulo());
                    row.getCell(2).setCellValue(libroactualizado.getAutor());    
                    row.getCell(3).setCellValue(libroactualizado.getAnioPublicacion());

                    Cell dateCell = row.getCell(4);
                    if (dateCell == null) {
                        dateCell = row.createCell(4);
                    }

                    dateCell.setCellValue(libroactualizado.getDateRead());
                    dateCell.setCellStyle(dateStyle);

                    ExcelHandler.guardarExcel(wb);
                    return libroactualizado;
                }
            }
            throw new RuntimeException("Libro no encontrado");
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar", e);
        }
    }

    public void eliminarLibro(int id) { //Elimina un libro
        try (Workbook wb = ExcelHandler.abrirExcel()) {
            Sheet sheet = wb.getSheetAt(0);

            int filaEliminada = -1;  // guarda el indice de la fila a eliminar

            //Busca la fila para eliminar
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row.getRowNum() == 0) continue;

                if (row != null) {
                    
                    Cell idCell = row.getCell(0);
                    if (idCell != null && idCell.getCellType() == CellType.NUMERIC && idCell.getNumericCellValue() == id) {

                        filaEliminada = i;
                        break;
                    }
                }
            }

            if(filaEliminada == -1) {
                throw new RuntimeException("Libro no encontrado");
            }
            
            Row rowEliminar = sheet.getRow(filaEliminada);
            if (rowEliminar != null) {
                sheet.removeRow(rowEliminar);
            }

            if(filaEliminada < sheet.getLastRowNum()) { 
                sheet.shiftRows(filaEliminada + 1, sheet.getLastRowNum(), -1);    // Matchea el indice de la linea y la desplaza hacia arriba, evitando lineas vacias en el excel
            }

            //actualiza los id 
            for (int i = filaEliminada; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row != null) {
                    Cell idCell = row.getCell(0);

                    if (idCell != null && idCell.getCellType() == CellType.NUMERIC) {
                        idCell.setCellValue(idCell.getNumericCellValue() -1);
                    }
                }
            }
            ExcelHandler.guardarExcel(wb);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar", e);
        }
    }
}
