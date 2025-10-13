import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  contrasena: string = '';
  email: string = ''; 

  constructor(private router: Router) { }

  iniciarSesion(): void {
    if (this.contrasena === '1234') {
      this.router.navigate(['/productos']);
    } else {
      alert('Contraseña incorrecta.');
    }
  }
}