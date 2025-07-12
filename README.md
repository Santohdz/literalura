# Literalura 📖

Aplicación de línea de comandos para buscar y explorar libros utilizando la API de Gutenberg.

## 🚀 Características

- Búsqueda de libros por título
- Visualización de información detallada de libros
- Gestión de autores y sus obras
- Almacenamiento en base de datos PostgreSQL

## 🛠️ Requisitos

- Java 24 o superior
- PostgreSQL 13+
- Maven 3.8+

## ⚙️ Configuración

### 1. Clona el repositorio:
   ```bash
   git clone [https://github.com/tu-usuario/literalura.git](https://github.com/tu-usuario/literalura.git)
   cd literalura
   ```
   
### 2. Configura las variables de entorno:
   
#### En Linux/Mac
export DATABASE_URL=jdbc:postgresql://localhost:5432/literalura
export DATABASE_USERNAME=tu_usuario
export DATABASE_PASSWORD=tu_contraseña

#### En Windows
set DATABASE_URL=jdbc:postgresql://localhost:5432/literalura
set DATABASE_USERNAME=tu_usuario
set DATABASE_PASSWORD=tu_contraseña

### 3. Ejecuta la aplicación:
   
mvn spring-boot:run

## 📝 Licencia
Este proyecto está bajo la Licencia MIT. Ver el archivo LICENSE para más detalles.

--- 

¡Gracias por visitar este proyecto! 🎉

Por [Santohdz](https://github.com/Santohdz) 🎴
