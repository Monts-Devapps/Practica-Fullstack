import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; 
import { ReactiveFormsModule } from '@angular/forms'; 
import { FormsModule } from '@angular/forms';

import { ListaProductosComponent } from './lista-productos/lista-productos.component';
import { ProductFormComponent } from './product-form/product-form.component';

@NgModule({
  declarations: [
    ListaProductosComponent,
    ProductFormComponent
  ],
  imports: [
    CommonModule,
    RouterModule,          
    ReactiveFormsModule,   
    FormsModule
  ]
})
export class ProductosModule { }