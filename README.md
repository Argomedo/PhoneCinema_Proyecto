# 📌 Bases de Datos Utilizadas

La aplicación utiliza una base de datos independiente por microservicio:

```
resenas_db  
usuarios_db  
feedback_db  
peliculas_db
```

---


# 📌 Documentación Swagger por Microservicio

Cada microservicio expone su propia API mediante Swagger. En desarrollo local:

| Microservicio | URL Swagger |
|---------------|-------------|
| **Usuarios**  | `http://localhost:8081/swagger-ui/index.html#/` |
| **Reseñas**   | `http://localhost:8082/swagger-ui/index.html#/` |
| **Feedback**  | `http://localhost:8083/swagger-ui/index.html#/` |
| **Películas** | `http://localhost:8084/swagger-ui/index.html#/` |

---


# 📌 Creación de Roles Especiales (ADMIN y MOD)

Para acceder a funcionalidades protegidas, se deben crear los usuarios con rol **ADMIN** y **MODERADOR**.

### Moderador
```json
{
  "nombre": "Moderador",
  "email": "mod@mail.com",
  "password": "Moderador123@",
  "fotoPerfilUrl": "",
  "rol": "MODERADOR"
}
```

### Administrador
```json
{
  "nombre": "Admin",
  "email": "admin@mail.com",
  "password": "Admin123@",
  "fotoPerfilUrl": "",
  "rol": "ADMIN"
}
```

---


# 🎬 Guía para Obtener el Póster de una Película desde TMDb

## 1. Entrar al sitio
```
https://www.themoviedb.org/
```

## 2. Buscar la película
Usar la barra de búsqueda y seleccionar la película deseada.

## 3. Obtener la URL del póster
1. Abrir la ficha de la película.  
2. Clic sobre el póster para ampliarlo.  
3. Clic derecho → **Copiar dirección de la imagen**.  

Esto copia la URL original del póster.

## 4. Ajustar la URL (reemplazar tamaño por `w500`)

URL original típica:
```
https://www.themoviedb.org/t/p/w600_and_h900_face/7tiub1UB4KF9zpacEldfbWAXDi6.jpg
```

La app requiere:
```
https://www.themoviedb.org/t/p/w500/7tiub1UB4KF9zpacEldfbWAXDi6.jpg
```

### Ejemplo 1
Copiada:
```
https://www.themoviedb.org/t/p/w600_and_h900_face/7tiub1UB4KF9zpacEldfbWAXDi6.jpg
```
Corregida:
```
https://www.themoviedb.org/t/p/w500/7tiub1UB4KF9zpacEldfbWAXDi6.jpg
```

### Ejemplo 2
Copiada:
```
https://www.themoviedb.org/t/p/w600_and_h900_face/oiqKEhEfxl9knzWXvWecJKN3aj6.jpg
```
Corregida:
```
https://www.themoviedb.org/t/p/w500/oiqKEhEfxl9knzWXvWecJKN3aj6.jpg
```

Solo se reemplaza el bloque central por `w500`.

---

# 🎬 Agregar la película a la app

1. Ingresar a la sección **Agregar Película**.  
2. Pegar la URL corregida del póster.  
3. Completar los demás campos.  
4. Guardar la película.

