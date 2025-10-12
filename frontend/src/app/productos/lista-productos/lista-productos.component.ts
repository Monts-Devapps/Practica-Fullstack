import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../producto.service';

@Component({
  selector: 'app-lista-productos',
  templateUrl: './lista-productos.component.html',
  styleUrls: ['./lista-productos.component.css']
})
export class ListaProductosComponent implements OnInit {

  productos: any[] = [];
  cargando: boolean = true;

  constructor(private productoService: ProductoService) { }

  ngOnInit(): void {
    this.cargando = true;
    this.productoService.obtenerProductos().subscribe(data => {
      this.productos = data;
      this.cargando = false;
    });
  }

  // boton eliminar
  eliminar(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
      this.productoService.eliminarProducto(id).subscribe(() => {
       
        this.productos = this.productos.filter(p => p.id !== id);
        alert('Producto eliminado exitosamente.');
      });
    }
  }

  
  toggleActivo(producto: any): void {
    this.productoService.cambiarEstado(producto.id).subscribe(productoActualizado => {
      
      producto.activo = productoActualizado.activo;
    });
  }
}