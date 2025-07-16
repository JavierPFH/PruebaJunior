import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from "@angular/common/http";
import { Libro } from '../models/libro';

@Injectable({
  providedIn: 'root'
})
export class LibroServiceService {

  private apiURL = 'http://localhost:8080/api/libros' // Url de Spring

  constructor(private http: HttpClient) { }

  verLibros(): Observable<Libro[]> {  //Recibe la info del back
    return this.http.get<Libro[]>(this.apiURL)
  }

  verLibrosId(id: number): Observable<Libro> {  //Busca Libros por Id
    return this.http.get<Libro>(`${this.apiURL}/${id}`)
  }

  crearLibro(nuevoLibro: Libro): Observable<Libro> {  //Crea los libros
    return this.http.post<Libro>(this.apiURL, nuevoLibro)
  }

  actualizarLibro(id: number, libro: Libro): Observable<Libro> {  //Actualiza el libro con el id correspondiente
    return this.http.put<Libro>(`${this.apiURL}/${id}`, libro)
  }

  eliminarLibro(id: number): Observable<void> {  //Elimina el libro con el id correspondiente
    return this.http.delete<void>(`${this.apiURL}/${id}`)
  }
}
