# Proyecto: Sistema de Gestión de Tareas (API REST)

Este proyecto es una aplicación para administrar tareas, creada como parte de un reto técnico.
El objetivo principal es gestionar el ciclo de vida de una tarea (Crear, Leer, Actualizar y Eliminar) 
asegurando que se cumplan reglas de negocio específicas.

## 🛠️ ¿Qué tecnologías usé?
* **Java 17:** El lenguaje de programación principal.
* **Spring Boot 3:** El framework que facilita la creación de la API.
- **H2 Database:** Una base de datos que funciona en memoria, ideal para probar el proyecto sin configurar servidores externos.
* **Spring Data JPA:** Para comunicarme con la base de datos de forma sencilla.
* **Maven:** Para gestionar todas las librerías del proyecto.

## 🧠 Lógica de Negocio (Lo más importante)
He programado validaciones especiales que el sistema revisa automáticamente:

1. **Validación de Tareas Vencidas (Regla A):** Si intentas marcar una tarea como "Completada" (DONE), el sistema revisa la fecha de vencimiento. Si la fecha ya pasó, no te deja cambiar el estado.
2. **Prioridad con Fecha Obligatoria (Regla B):** Si decides que una tarea es de prioridad "ALTA", el sistema te obliga a poner una fecha de vencimiento. No permite dejarla vacía.
3. **Límites de Texto:** El título debe tener entre 3 y 80 letras, y la descripción máximo 250, para mantener la base de datos limpia.

## 📊 Uso de Java Streams (Estadísticas)
Para el requisito de estadísticas, utilicé **Java Streams** en lugar de hacer muchas consultas a la base de datos.
* El sistema trae todas las tareas y las agrupa por su estado (`TODO`, `IN_PROGRESS`, `DONE`) usando una sola línea de código eficiente (`groupingBy`).

## 🚀 Cómo probar el proyecto
1. Abre el proyecto en tu IDE (IntelliJ recomendado).
2. Ejecuta la clase `TasksApplication.java`.
3. La API estará lista en: `http://localhost:8080/api/tasks`

### 📋 Lista de Endpoints
* **POST `/api/tasks`**: Para crear tareas.
* **GET `/api/tasks`**: Para ver todas las tareas.
* **GET `/api/tasks/{id}`**: Para ver una tarea específica.
* **PUT `/api/tasks/{id}`**: Para modificar una tarea (aquí se aplican las reglas de validación).
* **DELETE `/api/tasks/{id}`**: Para borrar una tarea.
* **GET `/api/tasks/stats`**: Para ver cuántas tareas hay en cada estado.

---
**Dato extra:** He incluido un archivo llamado `Reto-Tasks.postman_collection.json`. 
Puedes importarlo en Postman para probar todos los botones de forma inmediata.