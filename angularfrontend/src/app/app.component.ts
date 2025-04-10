import { Component, OnInit } from '@angular/core';
import { LibroServiceService } from './services/libro-service.service';
import { Libro } from './models/libro';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [NgFor, NgIf, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  listaLibros: Libro[] = []
  id!:number
  constructor(private libroService: LibroServiceService){}

  ngOnInit(): void {
    this.verLibros();
  }

  verLibros() {
    this.libroService.verLibros().subscribe(
      (data) => {
        this.listaLibros = data;
      }, (error) => {
        console.error('Error al obtener los libros', error)
      }
    )
  }

  verLibrosId() {
    if (!this.id) return
    console.log(this.id)

    this.libroService.verLibrosId(this.id).subscribe({
      next: (libro) => {
        this.listaLibros = [libro]
      }, error: (error) => {
        console.error('Libro no encontrado', error)
        this.listaLibros = []
      }
    })
  }

  crearLibro() {
  }
  actualizarLibro() {
    console.log("Boton pulsado")
  }
  eliminarLibro() {
    console.log("Boton pulsado")
  }

  prueba() {
    alert("Boton pulsado")
    console.log("Boton pulsado")
  }
}
