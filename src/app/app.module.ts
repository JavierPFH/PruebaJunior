import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';  
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { FormularioLibroComponent } from './pages/formulario-libro/formulario-libro.component';

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
    FormularioLibroComponent
  ],
  providers: [],
  bootstrap: []
})
export class AppModule {}
