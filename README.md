------*Cosas Faltantes*------

-Hacer Animaciones 
  (Una al abrir una pelicula)
  (Nose donde puede ir la otra)

-Integracion de API Nativa
  (Camara)
  (Galeria)

-Diferentes roles de Usuarios
  (Normal)
  (Moderador)
  (Administrador)

-----*Cosas Terminadas*-----

-Login/Register frontend
-Main Menu
-Apartados de peliculas con respectivas fotos y descripciones
-Apartamiento de reseñas
-Base de datos que almacene las reseñas y los registros de usuario
-Menu base del usuario

- CREAR ANTES DE INICIAR LOS PROCESOS LA BASE DE DATOS CORRESPONDIENTE A CÓMO ESTÁ GUARDADA EN LOS APPLICATION PROPERTIES-

- MODIFICAR LA IP DEL REMOTE MODULE BASADA EN EL DISPOSITIVO EN CUAL SE ESTÁ UTILIZANDO-

    private const val BASE_URL_USUARIOS = "http://192.168.1.37:8081/api/" --> AQUI SE MODIFICA SEGUN LA IP DEL DISPOSITIVO
    private const val BASE_URL_RESENAS = "http://192.168.1.37:8082/" // ← sin /api
    private const val BASE_URL_FEEDBACK = "http://192.168.1.37:8083/" // Nuevo microservicio de feedback en el puerto 8083

--ENDPOINTS-- 

## Endpoints de la API de Feedback
- **Crear un nuevo feedback:**
  - **Método:** `POST`
  - **Ruta:** `http://localhost:8083/api/feedback`
  - **Descripción:** Crea un nuevo feedback.
  - **Cuerpo de la solicitud (JSON):**
    ```json
    {
      "comentario": "Texto del comentario",
      "usuarioId": 123
    }
    ```
  - **Respuesta (200 OK):**
    ```json
    {
      "id": 1,
      "comentario": "Texto del comentario",
      "usuarioId": 123,
      "fechaCreacion": "2025-11-23T00:00:00"
    }
    ```

- **Obtener todos los feedbacks:**
  - **Método:** `GET`
  - **Ruta:** `http://localhost:8083/api/feedback`
  - **Descripción:** Obtiene todos los feedbacks registrados.
  - **Respuesta (200 OK):**
    ```json
    [
      {
        "id": 1,
        "comentario": "Texto del comentario",
        "usuarioId": 123,
        "fechaCreacion": "2025-11-23T00:00:00"
      },
      {
        "id": 2,
        "comentario": "Otro comentario",
        "usuarioId": 124,
        "fechaCreacion": "2025-11-23T01:00:00"
      }
    ]
    ```

- **Obtener feedbacks de un usuario específico:**
  - **Método:** `GET`
  - **Ruta:** `http://localhost:8083/api/feedback/usuario/{usuarioId}`
  - **Descripción:** Obtiene todos los feedbacks asociados a un usuario específico por su `usuarioId`.
  - **Parámetro de ruta:**
    - `usuarioId`: ID del usuario para filtrar los feedbacks.
  - **Respuesta (200 OK):**
    ```json
    [
      {
        "id": 1,
        "comentario": "Texto del comentario",
        "usuarioId": 123,
        "fechaCreacion": "2025-11-23T00:00:00"
      }
    ]
    ```

### URL base
La API está corriendo en el puerto `8083`. Las URLs completas para los endpoints son:

- Crear feedback: `http://localhost:8083/api/feedback`
- Obtener todos los feedbacks: `http://localhost:8083/api/feedback`
- Obtener feedbacks por usuario: `http://localhost:8083/api/feedback/usuario/{usuarioId}`

## Endpoints de la API de Reseñas

- **Crear una nueva reseña:**
  - **Método:** `POST`
  - **Ruta:** `http://localhost:8082/reviews`
  - **Descripción:** Crea una nueva reseña.
  - **Cuerpo de la solicitud (JSON):**
    ```json
    {
      "comentario": "Texto de la reseña",
      "calificacion": 4,
      "movieId": 123
    }
    ```
  - **Respuesta (201 Created):**
    ```json
    {
      "id": 1,
      "comentario": "Texto de la reseña",
      "calificacion": 4,
      "movieId": 123,
      "fechaCreacion": "2025-11-23T00:00:00"
    }
    ```

- **Obtener reseñas por película:**
  - **Método:** `GET`
  - **Ruta:** `http://localhost:8082/reviews/movie/{movieId}`
  - **Descripción:** Obtiene todas las reseñas asociadas a una película específica, identificada por su `movieId`.
  - **Parámetro de ruta:**
    - `movieId`: ID de la película para filtrar las reseñas.
  - **Respuesta (200 OK):**
    ```json
    [
      {
        "id": 1,
        "comentario": "Excelente película",
        "calificacion": 5,
        "movieId": 123,
        "fechaCreacion": "2025-11-23T00:00:00"
      },
      {
        "id": 2,
        "comentario": "Buena, pero con detalles",
        "calificacion": 3,
        "movieId": 123,
        "fechaCreacion": "2025-11-23T01:00:00"
      }
    ]
    ```

- **Obtener todas las reseñas:**
  - **Método:** `GET`
  - **Ruta:** `http://localhost:8082/reviews`
  - **Descripción:** Obtiene todas las reseñas registradas.
  - **Respuesta (200 OK):**
    ```json
    [
      {
        "id": 1,
        "comentario": "Excelente película",
        "calificacion": 5,
        "movieId": 123,
        "fechaCreacion": "2025-11-23T00:00:00"
      },
      {
        "id": 2,
        "comentario": "Buena, pero con detalles",
        "calificacion": 3,
        "movieId": 123,
        "fechaCreacion": "2025-11-23T01:00:00"
      }
    ]
    ```

- **Eliminar una reseña:**
  - **Método:** `DELETE`
  - **Ruta:** `http://localhost:8082/reviews/{id}`
  - **Descripción:** Elimina una reseña específica por su `id`.
  - **Parámetro de ruta:**
    - `id`: ID de la reseña a eliminar.
  - **Respuesta (204 No Content):** No retorna contenido.

### URL base
La API está corriendo en el puerto `8082`. Las URLs completas para los endpoints son:

- Crear reseña: `http://localhost:8082/reviews`
- Obtener reseñas por película: `http://localhost:8082/reviews/movie/{movieId}`
- Obtener todas las reseñas: `http://localhost:8082/reviews`
- Eliminar reseña: `http://localhost:8082/reviews/{id}`


## Endpoints de la API de Usuarios

- **Registrar un nuevo usuario:**
  - **Método:** `POST`
  - **Ruta:** `http://localhost:8081/api/usuarios/registrar`
  - **Descripción:** Registra un nuevo usuario.
  - **Cuerpo de la solicitud (JSON):**
    ```json
    {
      "nombre": "Juan Pérez",
      "email": "juan.perez@example.com",
      "contrasena": "contraseña123"
    }
    ```
  - **Respuesta (200 OK):**
    ```json
    {
      "id": 1,
      "nombre": "Juan Pérez",
      "email": "juan.perez@example.com",
      "fechaCreacion": "2025-11-23T00:00:00"
    }
    ```

- **Iniciar sesión (login):**
  - **Método:** `POST`
  - **Ruta:** `http://localhost:8081/api/usuarios/login`
  - **Descripción:** Inicia sesión con las credenciales de un usuario.
  - **Cuerpo de la solicitud (JSON):**
    ```json
    {
      "email": "juan.perez@example.com",
      "contrasena": "contraseña123"
    }
    ```
  - **Respuesta (200 OK):**
    ```json
    {
      "token": "jwt_token_aqui",
      "mensaje": "Inicio de sesión exitoso"
    }
    ```
  - **Respuesta (401 Unauthorized):**
    ```json
    {
      "token": null,
      "mensaje": "Credenciales inválidas"
    }
    ```

- **Obtener todos los usuarios:**
  - **Método:** `GET`
  - **Ruta:** `http://localhost:8081/api/usuarios`
  - **Descripción:** Obtiene una lista de todos los usuarios registrados.
  - **Respuesta (200 OK):**
    ```json
    [
      {
        "id": 1,
        "nombre": "Juan Pérez",
        "email": "juan.perez@example.com",
        "fechaCreacion": "2025-11-23T00:00:00"
      },
      {
        "id": 2,
        "nombre": "Ana Gómez",
        "email": "ana.gomez@example.com",
        "fechaCreacion": "2025-11-22T12:00:00"
      }
    ]
    ```

- **Eliminar un usuario:**
  - **Método:** `DELETE`
  - **Ruta:** `http://localhost:8081/api/usuarios/{id}`
  - **Descripción:** Elimina un usuario específico por su `id`.
  - **Parámetro de ruta:**
    - `id`: ID del usuario a eliminar.
  - **Respuesta (204 No Content):** No retorna contenido.

- **Actualizar rol de un usuario:**
  - **Método:** `PUT`
  - **Ruta:** `http://localhost:8081/api/usuarios/{id}`
  - **Descripción:** Actualiza el rol de un usuario específico.
  - **Parámetro de ruta:**
    - `id`: ID del usuario cuyo rol se desea actualizar.
  - **Parámetro de consulta:**
    - `rol`: Nuevo rol para el usuario (por ejemplo, `ADMIN`, `USER`).
  - **Respuesta (200 OK):**
    ```json
    {
      "id": 1,
      "nombre": "Juan Pérez",
      "email": "juan.perez@example.com",
      "rol": "ADMIN",
      "fechaCreacion": "2025-11-23T00:00:00"
    }
    ```

### URL base
La API está corriendo en el puerto `8081`. Las URLs completas para los endpoints son:

- Registrar usuario: `http://localhost:8081/api/usuarios/registrar`
- Iniciar sesión: `http://localhost:8081/api/usuarios/login`
- Obtener todos los usuarios: `http://localhost:8081/api/usuarios`
- Eliminar usuario: `http://localhost:8081/api/usuarios/{id}`
- Actualizar rol de usuario: `http://localhost:8081/api/usuarios/{id}`
