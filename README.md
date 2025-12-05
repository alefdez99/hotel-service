# Hotel Service API

API REST sencilla para la gestión de hoteles, construida con **Java 21**, **Spring Boot 3**, **Spring Data JPA**, **H2** y **Spring Security**.

Permite crear, listar, buscar, actualizar y eliminar hoteles con seguridad basada en roles.


## Tecnologías utilizadas

- Java 21  
- Spring Boot (Web, Data JPA, Validation, Security)  
- H2 Database (en memoria)  
- Lombok  
- JUnit 5 + MockMvc  


## Ejecución del proyecto

### 1. Clonar el repositorio

```bash
git clone <URL-del-repo>
cd hotel-service
```

### 2. Ejecutar

```bash
mvn spring-boot:run
```

La API estará disponible en:
```bash
http://localhost:8080
```

## Seguridad

Se incluyen dos usuarios por defecto:

| Usuario | Rol   | Contraseña |
| ------- | ----- | ---------- |
| user    | USER  | password   |
| admin   | ADMIN | adminpass  |

- Todos los endpoints requieren autenticación  
- Solo el rol **ADMIN** puede eliminar hoteles

## Acceso a la consola H2

```bash
http://localhost:8080/h2-console
```

## Endpoints principales

### Crear hotel

`POST /api/hotels`

### Listar hoteles

`GET /api/hotels`

Parámetros opcionales: city, page, size, sort

### Buscar por ciudad

`GET /api/hotels?city=<ciudad>`

### Obtener hotel por ID

`GET /api/hotels/{id}`

### Actualizar la dirección

`PUT /api/hotels/{id}/address`

### Eliminar hotel (ADMIN)

`DELETE /api/hotels/{id}`


## Tests

- Tests unitarios por endpoint (MockMvc)
- Validación de errores y excepciones
- Tests de seguridad (autorización y roles)

Ejecutar tests:

```bash
mvn test
```

## Ejemplo JSON para creación de hotel

```json
{
  "name": "Hotel Gran Canaria",
  "stars": 5,
  "address": {
    "street": "Av. de Las Canteras 123",
    "city": "Las Palmas de Gran Canaria",
    "country": "España",
    "postalCode": "35010"
  }
}
```
