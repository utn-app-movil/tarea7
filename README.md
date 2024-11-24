
# DogView - Aplicación Móvil con Consumo de APIs

**Universidad Técnica Nacional**  
**Curso:** ITI-623 Programación Dispositivos Móviles I  
**Profesor:** Ever Barahona Mendoza  
**Estudiante:** Bairon Yasith Vega Martínez

---

## Descripción
Este proyecto es parte del curso ITI-623 "Programación Dispositivos Móviles I" impartido por el profesor Ever Barahona Mendoza en la Universidad Técnica Nacional. Su propósito es evaluar los conocimientos adquiridos en el diseño y desarrollo de una aplicación móvil mediante la investigación la cual el objetivo es que consuma REST APIs brindada por el Profesor, integrando dos endpoints principales: uno para autenticación (POST) y otro para listar datos y mostrar galerías (GET).

---

## Enunciado

La aplicación móvil realiza las siguientes funcionalidades:

1. **Autenticación de Usuario (POST)**
   - La pantalla de login consume un endpoint que valida un usuario y su contraseña. Solo los usuarios activos pueden acceder a la aplicación.
   - **Endpoint**: `https://apicontainers.azurewebsites.net/technicians/validateAuth`
   - **Request Body**:
     ```json
     {
        "id": "TEC-5",
        "password": "gbfrt78"
     }
     ```
   - **Posibles respuestas del endpoint**:
     - Usuario activo:
       ```json
       {
          "data": {
              "id": "TEC-5",
              "name": "Marco",
              "lastName": "Elizondo Pérez",
              "isActive": true,
              "password": "gbfrt78",
              "isTemporary": false
          },
          "responseCode": 0,
          "message": ""
       }
       ```
     - Usuario inactivo:
       ```json
       {
          "data": null,
          "responseCode": -1,
          "message": "The user is inactivated."
       }
       ```
     - Credenciales incorrectas:
       ```json
       {
          "data": null,
          "responseCode": -1,
          "message": "Incorrect user or password."
       }
       ```

2. **Lista de Razas de Perros (GET)**
   - Una pantalla muestra la lista de razas consumiendo el siguiente endpoint:
     **URL**: `https://dog.ceo/api/breeds/list/all`
   - Cada raza se presenta como un elemento clickeable.

3. **Galería de Imágenes de la Raza Seleccionada (GET)**
   - Al seleccionar una raza, se muestra una nueva pantalla con una galería de imágenes de esa raza.
   - **URL**: `https://dog.ceo/api/breed/{razaperro}/images`
   - Ejemplo para razas específicas:
     - Affenpinscher: `https://dog.ceo/api/breed/affenpinscher/images`
     - Bull Terrier: `https://dog.ceo/api/breed/bullterrier/images`

---

## Estructura del Proyecto
La aplicación utiliza el patrón **MVVM (Model-View-ViewModel)** para mantener un código modular y escalable.

### Paquetes

#### **data**
- **models**: Contiene los modelos de datos para la interacción con las APIs.
  - `LoginRequest`, `LoginResponse`, `RegisterRequest`, `RegisterResponse`
  - `BreedsModel`: Mapea la lista de razas.
  - `BreedImagesModel`: Mapea la lista de URLs de imágenes.
- **network**: Configuración de Retrofit y las interfaces para los endpoints.
  - `ApiClient`: Configuración central de Retrofit.
  - `DogApi` y `AuthApi`: Interfaz para los endpoints.
- **repository**: Maneja las operaciones con las APIs y encapsula la lógica de negocios.
  - `AuthRepository`: Lógica para autenticación.
  - `BreedsRepository`: Obtiene la lista de razas y las imágenes asociadas.

#### **ui**
- **login**: Contiene la pantalla de autenticación (`LoginActivity`).
- **register**: Maneja el registro de usuarios (si aplica).
- **breeds**: Muestra la lista de razas (`BreedsActivity`).
- **gallery**: Muestra la galería de imágenes de la raza seleccionada (`BreedGalleryActivity`).

#### **viewmodel**
- Contiene los ViewModels que gestionan la lógica de negocio y comunicación entre las vistas y los repositorios.
  - `LoginViewModel`, `BreedsViewModel`, `BreedGalleryViewModel`.

---

## Configuraciones Importantes
### Permisos de Android
El archivo `AndroidManifest.xml` incluye el permiso de Internet necesario para el consumo de las APIs:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## Dependencias Clave

- **Retrofit y OkHttp** para el consumo de APIs:
  ```kotlin
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.okhttp3:okhttp:4.11.0")
  ```

- **Kotlinx Serialization** para el manejo de JSON:
  ```kotlin
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
  ```

- **Material Design** para un diseño moderno:
   ```kotlin
    implementation("com.google.android.material:material:1.9.0")
    ```

---

## Resumen de Funcionalidades Implementadas
### Autenticación
- Validación de usuario contra el endpoint `validateAuth`.
- Mensajes de error personalizados según la respuesta del servidor.

### Listado de Razas
- Uso de un `ListView` simple para mostrar las razas disponibles.
- Conexión al endpoint `breeds/list/all`.

### Galería de Imágenes
- Uso de un `GridView` para mostrar imágenes de la raza seleccionada.
- Implementación de un adaptador para manejar las imágenes.

---

## Capturas de Pantalla
(Incluye imágenes representativas de las pantallas principales del proyecto, como login, lista de razas y galería de imágenes).

---

## Instrucciones de Ejecución
### Configuración Previa
1. Clonar este repositorio.
2. Abrir el proyecto en Android Studio.
3. Asegurarse de tener una conexión a Internet activa.

### Ejecución
1. Compilar el proyecto.
2. Ejecutar en un emulador o dispositivo físico con Android 6.0 o superior.

## Conclusión
Este proyecto integra los conceptos aprendidos en clase, incluyendo la configuración de Retrofit, manejo de datos con Kotlinx Serialization y un diseño modular basado en MVVM. Además, demuestra el consumo y presentación de datos dinámicos desde APIs externas, lo cual es fundamental en el desarrollo de aplicaciones móviles modernas.

---

