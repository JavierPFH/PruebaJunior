import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-formulario-libro',
  imports: [],
  templateUrl: './formulario-libro.component.html',
  styleUrl: './formulario-libro.component.css'
})
export class FormularioLibroComponent {
  @Output() cerrar:EventEmitter<void> = new EventEmitter()

  guardarLibro() {}
  cerrarFormulario() {
    this.cerrar.emit()
  }
}
