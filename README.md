Bases de Datos Utilizadas

La aplicación trabaja con cuatro bases de datos separadas, una por microservicio:

resenas_db

usuarios_db

feedback_db

peliculas_db

Documentación Swagger por Microservicio

Cada microservicio expone su propia documentación Swagger. Para acceder:

Usuarios
http://localhost:8081/swagger-ui/index.html#/

Reseñas
http://localhost:8082/swagger-ui/index.html#/

Feedback
http://localhost:8083/swagger-ui/index.html#/

Películas
http://localhost:8084/swagger-ui/index.html#/

Creación de Roles Especiales (ADMIN y MOD)

Para probar correctamente permisos y flujos protegidos, se deben crear manualmente los usuarios con roles ADMIN y MODERADOR mediante Swagger o Postman.

Usuario Moderador

{
  "nombre": "Moderador",
  "email": "mod@mail.com",
  "password": "Moderador123@",
  "fotoPerfilUrl": "",
  "rol": "MODERADOR"
}


Usuario Administrador

{
  "nombre": "Admin",
  "email": "admin@mail.com",
  "password": "Admin123@",
  "fotoPerfilUrl": "",
  "rol": "ADMIN"
}

Procedimiento para Obtener y Usar el Póster de una Película desde TMDb
1. Ingresar al sitio de películas

Entrar a:

https://www.themoviedb.org/

2. Buscar la película

Usar la barra de búsqueda y seleccionar la película deseada.

3. Obtener la dirección del póster

Dentro de la ficha de la película:

Abrir el póster haciendo clic sobre él.

Cuando se muestre ampliado, usar clic derecho → Copiar dirección de la imagen.
Con eso queda copiado el enlace original del póster.

4. Ajustar la URL al formato requerido (tamaño w500)

TMDb entrega un enlace con un tamaño específico, por ejemplo:

https://www.themoviedb.org/t/p/w600_and_h900_face/7tiub1UB4KF9zpacEldfbWAXDi6.jpg


La aplicación usa tamaño w500, por lo que solo se reemplaza el bloque del tamaño:

Original: w600_and_h900_face

Requerido: w500

Ejemplo 1

Copiada:

https://www.themoviedb.org/t/p/w600_and_h900_face/7tiub1UB4KF9zpacEldfbWAXDi6.jpg


Corregida:

https://www.themoviedb.org/t/p/w500/7tiub1UB4KF9zpacEldfbWAXDi6.jpg


Ejemplo 2

Copiada:

https://www.themoviedb.org/t/p/w600_and_h900_face/oiqKEhEfxl9knzWXvWecJKN3aj6.jpg


Corregida:

https://www.themoviedb.org/t/p/w500/oiqKEhEfxl9knzWXvWecJKN3aj6.jpg


Solo se reemplaza el bloque central por w500. El resto del enlace permanece igual.

5. Ingresar el enlace en la aplicación

En la pantalla Agregar Película:

Pegar la URL corregida en el campo del póster.

Completar los demás datos requeridos.

Guardar la película.