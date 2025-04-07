<!-- LOGO DEL PROYECTO -->
<br />
<div align="center">

  <h3 align="center">Sistema de Gestión de Librería</h3>

<!-- TABLA DE CONTENIDOS -->
<details>
  <summary>Tabla de Contenidos</summary>
  <ol>
    <li>
      <a href="#acerca-del-proyecto">Acerca del Proyecto</a>
      <ul>
        <li><a href="#construido-con">Construido Con</a></li>
      </ul>
    </li>
    <li>
      <a href="#empezando">Empezando</a>
      <ul>
        <li><a href="#prerrequisitos">Prerrequisitos</a></li>
        <li><a href="#instalación">Instalación</a></li>
      </ul>
    </li>
    <li>
      <a href="#uso">Uso</a>
      <ul>
        <li><a href="#funcionalidades">Funcionalidades</a></li>
        <li><a href="#estructura-del-proyecto">Estructura del Proyecto</a></li>
      </ul>
    </li>
    <li><a href="#diagrama-de-base-de-datos">Diagrama de Base de Datos</a></li>
  </ol>
</details>

<!-- ACERCA DEL PROYECTO -->
## Acerca del Proyecto

Este proyecto es un sistema de gestión de librería desarrollado con Java Servlets y MySQL. La aplicación permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una base de datos de libros, proporcionando una interfaz web responsiva utilizando Bootstrap.

**Características principales:**
- Consulta de todos los libros disponibles en la base de datos
- Inserción de nuevos libros con información completa
- Modificación de datos de libros existentes
- Eliminación de libros con manejo de relaciones entre tablas
- Interfaz limpia y responsiva basada en Bootstrap 5
- Implementación en Java con Servlets
- Conexión segura a base de datos MySQL

Este proyecto puede utilizarse como punto de partida para estudiantes aprendiendo desarrollo web con Java, o como base para aplicaciones de gestión de biblioteca más complejas.

<p align="right"><a href="#readme-top">↑ Volver al inicio ↑</a></p>

### Construido Con

| Java | 
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) <br>
| Servlets |
![Servlets](https://img.shields.io/badge/Servlets-DD0031.svg?style=for-the-badge&logo=java&logoColor=white) <br>
| MySQL |
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) <br>
| Bootstrap |
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white) <br>
| HTML5 |
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white) <br>

<p align="right"><a href="#readme-top">↑ Volver al inicio ↑</a></p>

<!-- EMPEZANDO -->
## Empezando

Para poner en marcha esta aplicación en tu máquina local, sigue estos pasos:

### Prerrequisitos

- JDK 8 o superior
- Servidor de aplicaciones Java (Tomcat, Jetty, etc.)
- MySQL 5.7 o superior
- IDE Java (Eclipse, IntelliJ IDEA, NetBeans)
- Conector JDBC para MySQL

### Instalación

1. Configura un servidor de aplicaciones Java (Tomcat recomendado)
2. Clona el repositorio:
   ```sh
   git clone https://github.com/tuusuario/sistema-libreria.git
   ```
3. Importa el proyecto en tu IDE favorito
4. Configura la base de datos MySQL:
   ```sql
   CREATE DATABASE libreria;
   USE libreria;

   CREATE TABLE editorials (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nom VARCHAR(100) NOT NULL
   );

   CREATE TABLE autors (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nom VARCHAR(100) NOT NULL,
     nacionalitat VARCHAR(50)
   );

   CREATE TABLE generes (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nom VARCHAR(50) NOT NULL
   );

   CREATE TABLE llibres (
     id INT AUTO_INCREMENT PRIMARY KEY,
     titol VARCHAR(200) NOT NULL,
     isbn VARCHAR(20) UNIQUE,
     any_publicacio INT,
     id_editorial INT,
     FOREIGN KEY (id_editorial) REFERENCES editorials(id)
   );

   CREATE TABLE llibre_autor (
     id_llibre INT,
     id_autor INT,
     PRIMARY KEY (id_llibre, id_autor),
     FOREIGN KEY (id_llibre) REFERENCES llibres(id),
     FOREIGN KEY (id_autor) REFERENCES autors(id)
   );

   CREATE TABLE llibre_genere (
     id_llibre INT,
     id_genere INT,
     PRIMARY KEY (id_llibre, id_genere),
     FOREIGN KEY (id_llibre) REFERENCES llibres(id),
     FOREIGN KEY (id_genere) REFERENCES generes(id)
   );
   ```
5. Actualiza el archivo `Conexio.java` con tus propias credenciales de base de datos:
   ```java
   String myUrl = "jdbc:mysql://tu_servidor:3306/libreria";
   con = DriverManager.getConnection(myUrl, "tu_usuario", "tu_contraseña");
   ```
6. Asegúrate de tener el driver JDBC de MySQL en tu classpath
7. Despliega la aplicación en tu servidor Tomcat

<p align="right"><a href="#readme-top">↑ Volver al inicio ↑</a></p>

<!-- USO -->
## Uso

Una vez desplegada la aplicación, podrás acceder a ella a través de tu navegador web preferido.

### Funcionalidades

1. **Consultar libros**: Accede a `/con` para ver todos los libros registrados en la base de datos.
2. **Insertar libros**: Accede a `/insertar` para añadir un nuevo libro proporcionando título, ISBN, año de publicación e ID de editorial.
3. **Modificar libros**: Accede a `/modificar` para actualizar la información de un libro existente mediante su ID.
4. **Eliminar libros**: Accede a `/eliminar` para borrar un libro y todas sus relaciones con autores y géneros.

### Estructura del Proyecto

- **Conexio.java**: Clase encargada de gestionar la conexión con la base de datos MySQL.
- **Consulta.java**: Servlet que muestra todos los libros en una tabla HTML.
- **Insertar.java**: Servlet que maneja la inserción de nuevos libros.
- **Modificar.java**: Servlet que permite actualizar información de libros existentes.
- **Eliminar.java**: Servlet que elimina libros y mantiene la integridad referencial con otras tablas.
- **Demo.java**: Clase para pruebas independientes.

<p align="right"><a href="#readme-top">↑ Volver al inicio ↑</a></p>

<!-- DIAGRAMA DE BASE DE DATOS -->
## Diagrama de Base de Datos

El sistema utiliza una base de datos relacional con las siguientes tablas:

- **llibres**: Almacena la información principal de los libros (id, título, ISBN, año, editorial)
- **editorials**: Contiene información sobre las editoriales
- **autors**: Almacena datos de los autores
- **generes**: Registra los géneros literarios
- **llibre_autor**: Tabla de relación muchos a muchos entre libros y autores
- **llibre_genere**: Tabla de relación muchos a muchos entre libros y géneros

El sistema implementa transacciones para asegurar la integridad de datos, especialmente en operaciones de eliminación que afectan a múltiples tablas.

<p align="right"><a href="#readme-top">↑ Volver al inicio ↑</a></p>