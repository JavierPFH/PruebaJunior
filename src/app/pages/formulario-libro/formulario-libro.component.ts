import { Component, Output, EventEmitter, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LibroServiceService } from '../../services/libro-service.service';
import { Libro } from '../../models/libro';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-formulario-libro',
  imports: [FormsModule, CommonModule],
  templateUrl: './formulario-libro.component.html',
  styleUrl: './formulario-libro.component.css'
})

export class FormularioLibroComponent implements OnInit{
  @Input() tituloFormulario: string = 'titulo'
  @Input() libroActualizar?: Libro;
  @Output() cerrar:EventEmitter<void> = new EventEmitter()
  @Input() autores:string[] =  []

  constructor(private libroService: LibroServiceService) {}
  
  titulo!: string
  autor!: string
  publicacion!: number
  fecha!:Date


  ngOnInit(): void {
    if(this.libroActualizar) {
      this.titulo = this.libroActualizar.titulo
      this.autor = this.libroActualizar.autor
      this.publicacion = this.libroActualizar.anioPublicacion
      this.fecha = this.libroActualizar.dateRead
    }
  }


  guardarLibro() {
    const dataLibro: Libro = {
      titulo:this.titulo,
      autor:this.autor,
      anioPublicacion:this.publicacion,
      dateRead:this.fecha
    }

    if(this.libroActualizar && this.libroActualizar.id !== undefined) { //Controla que se vaya a actualizar un libro
      this.libroService.actualizarLibro(this.libroActualizar.id, dataLibro).subscribe({
        next: () => {
          alert("Libro Actualizado")
          this.cerrarFormulario()
        }, error: () => alert("Error al actualizar el libro")
      })
    } else {  //si NO actualiza guarda

      this.libroService.crearLibro(dataLibro).subscribe({
        next: () => {
            alert("Libro guardado")
            this.cerrarFormulario()
        }, error: () => alert("Error al crear libro")
      })
    }
  }

  cerrarFormulario() {
    this.cerrar.emit()
  }
}
