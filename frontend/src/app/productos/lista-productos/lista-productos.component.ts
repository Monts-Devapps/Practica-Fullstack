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
  paginaActual: number = 0;
  totalPaginas: number = 0;
  tamanoPagina: number = 8; 

  constructor(private productoService: ProductoService) { }

  ngOnInit(): void {
    this.cargarProductos(); // Ahora llamamos a nuestro nuevo método
  }

  // Nuevo método que carga los productos por página
  cargarProductos(): void {
    this.cargando = true;
    this.productoService.obtenerProductos(this.paginaActual, this.tamanoPagina).subscribe(
      (data) => {
        this.productos = data.content; // La lista de productos ahora viene en 'content'
        this.totalPaginas = data.totalPages;
        this.cargando = false;
      },
      (error) => {
        console.error('Error al obtener productos:', error);
        this.cargando = false;
      }
    );
  }

  // --- NUEVOS MÉTODOS PARA LOS BOTONES DE PAGINACIÓN ---
  paginaSiguiente(): void {
    if (this.paginaActual < this.totalPaginas - 1) {
      this.paginaActual++;
      this.cargarProductos();
    }
  }

  paginaAnterior(): void {
    if (this.paginaActual > 0) {
      this.paginaActual--;
      this.cargarProductos();
    }
  }

  // --- TUS MÉTODOS EXISTENTES (se quedan igual) ---
  eliminar(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
      this.productoService.eliminarProducto(id).subscribe(() => {
        // Recargamos la página actual para reflejar el cambio
        this.cargarProductos(); 
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