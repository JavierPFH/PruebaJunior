package com.javier.demo.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.javier.demo.model.Libros;
/*
 * Clase para manejar las acciones en el excel
 * 11/04/2025
 * v1
 */

public class ExcelHandler {
    private static final String EXCEL_PATH = "../data/Libros.xlsx";

    // Abrir el archivo excel
    public static Workbook abrirExcel() throws IOException, InvalidFormatException {
        try(InputStream is = new FileInputStream(EXCEL_PATH)) { return WorkbookFactory.create(is); }
    }

    // Escribe la informacion
    public static void guardarExcel(Workbook wb) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(EXCEL_PATH)) { wb.write(fos); }
    }

    // Recoge la informacion
    public static List<Libros> leerExcel(Sheet sheet) {
        List<Libros> listaLibros = new ArrayList<>();

        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row =  sheet.getRow(i);
            if(row == null) continue;

            int id = (int) row.getCell(0).getNumericCellValue();
            String titulo = row.getCell(1).getStringCellValue();
            String autor = row.getCell(2).getStringCellValue();
            int anio = (int) row.getCell(3).getNumericCellValue();

            Cell dateCell = row.getCell(4);
            Date read = null;
            if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
                read = dateCell.getDateCellValue();
                
            }
            

            Libros libro = new Libros(id, titulo, autor, anio, read);
            listaLibros.add(libro);
        }

        return listaLibros;
    }
}
