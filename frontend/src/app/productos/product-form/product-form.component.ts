import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from '../producto.service';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  isEditMode = false;
  currentProductId: number | null = null;
  pageTitle = 'Crear Producto';

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productForm = this.fb.group({
      nombre: ['', Validators.required],
      marca: [''],
      categoria: [''],
      precio: [0, [Validators.required, Validators.min(0.01)]],
      existencias: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.currentProductId = +id;
      this.pageTitle = 'Editar Producto';
      this.productoService.obtenerProductoPorId(+id).subscribe(producto => {
        this.productForm.patchValue(producto);
      });
    }
  }

  onSubmit(): void {
    if (this.productForm.invalid) {
      alert('Por favor, completa los campos requeridos.');
      return;
    }

    const action = this.isEditMode
      ? this.productoService.actualizarProducto(this.currentProductId!, this.productForm.value)
      : this.productoService.crearProducto(this.productForm.value);

    action.subscribe(() => {
      alert(`Producto ${this.isEditMode ? 'actualizado' : 'creado'} exitosamente.`);
      this.router.navigate(['/productos']);
    });
  }

  cancelar(): void {
    this.router.navigate(['/productos']);
  }
}