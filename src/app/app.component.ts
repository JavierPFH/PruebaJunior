import { Component, OnInit } from '@angular/core';
import { LibroServiceService } from './services/libro-service.service';
import { Libro } from './models/libro';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FormularioLibroComponent } from "./pages/formulario-libro/formulario-libro.component";
import { EMPTY } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';


@Component({
  selector: 'app-root',
  imports: [NgFor, NgIf, FormsModule, FormularioLibroComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  listaLibros: Libro[] = [] //guarda la lista de todos los libros
  librosOriginales: Libro[] = []
  autores:string[] = []
  filtroTexto:string  = "" //parametro id para filtrar

  mostrarFormulario: boolean = false //muestra el formulario para crear/actualizar
  tituloFormulario: string = ''
  libroActualizar?: Libro;
  
  constructor(private libroService: LibroServiceService){}
  
  ngOnInit(): void {
    this.verLibros();
    
  }
  
  abrirFormularioCrear() {
      this.libroActualizar = undefined
      this.mostrarFormulario = true
      this.tituloFormulario = 'Añadir Libro'
      this.verLibros()
  }
  
  abrirFormularioActualizar(libro: Libro) {
      this.libroActualizar = {...libro} //Evita modificar el original
      this.mostrarFormulario = true
      this.tituloFormulario = 'Actualizar Libro'
      this.verLibros()
  }
  
  cerrarFormulario() {
    this.mostrarFormulario = false
    this.verLibros();
  }

  verLibros() {
    return this.libroService.verLibros().pipe(
      tap((libros) => {
        this.listaLibros = libros;
        this.librosOriginales = [...libros]
        this.autores = [...new Set(libros.map(libro => libro.autor))];
      }),
      catchError((err) => {
        alert("Error al cargar los libros");
        return EMPTY;
      })
    ).subscribe();
  }

  // verLibrosId() {
  //   if (!this.id) return
  //   console.log(this.id)

  //   this.libroService.verLibrosId(this.id).subscribe({
  //     next: (libro) => {
  //       this.listaLibros = [libro]
  //     }, error: () => {
  //       alert('Libro no encontrado')
  //       this.listaLibros = []
  //     }
  //   })
  // }

  eliminarLibro(id: number) {
    this.libroService.eliminarLibro(id).subscribe({
      next: () => {
        alert("Libro eliminado")
        this.verLibros()
      }, error: () => alert("Error al eliminar")
    })
  }

  filtro() {
    const parametro = this.filtroTexto.trim().toLowerCase()
    this.listaLibros = this.librosOriginales.filter(libro => {
      const filtroAutor = libro.autor.toLowerCase().includes(parametro)
      const fechaLectura = new Date(libro.dateRead).toISOString().split('T')[0]
      const filtroFecha = fechaLectura.includes(parametro)
      return filtroAutor || filtroFecha
    })
  }

  limpiarFiltro() {
    this.filtroTexto = ""
    this.listaLibros = [...this.librosOriginales]
  }

}
