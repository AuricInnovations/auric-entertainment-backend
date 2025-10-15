
---

## Auric Entertainment BackEnd

```markdown
# Auric Entertainment Backend

## Overview
Spring Boot service powering **Auric Entertainment** web platform.  
Phase One provides public event APIs and booking persistence in PostgreSQL.  
Payments, email, and storage integrations are **disabled** in this phase.

---

## Tech Stack
- Java 17 + Spring Boot 3.x  
- Spring Web, Spring Data JPA  
- PostgreSQL ( H2 for dev )  
- Spring Validation, Lombok (optional)  
- OpenAPI / Swagger (optional)

---

## Suggested Package Layout

com.auric.entertainment
├─ config/
├─ controller/
├─ dto/
├─ entity/
├─ repository/
├─ service/
└─ AuricApplication.java


---

## Entities
java
// Event.java
@Id @GeneratedValue
private Long id;
private String title;
private String venue;
private LocalDateTime startAt;
private LocalDateTime endAt;
private String category;
private BigDecimal priceFrom;
private Boolean published;

// Booking.java
@Id @GeneratedValue
private Long id;
@NotNull private Long eventId;
@NotBlank private String fullName;
@Email private String email;
@NotBlank private String phone;
@Min(1) private Integer tickets;
private LocalDateTime createdAt;

REST Endpoints

| Method | Endpoint           | Description           |
| ------ | ------------------ | --------------------- |
| GET    | `/api/events`      | List published events |
| GET    | `/api/events/{id}` | Fetch event detail    |
| POST   | `/api/bookings`    | Create booking record |

curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"eventId":1,"fullName":"Jane Doe","email":"jane@example.com","phone":"+60...", "tickets":2}'

server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/auric
    username: auric
    password: auric
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

auric:
  integrations:
    payments-enabled: false
    email-enabled: false
    storage-enabled: false

@Bean
CorsConfigurationSource corsConfigurationSource() {
  var config = new CorsConfiguration();
  config.setAllowedOrigins(List.of("https://web.auricentertainment.com"));
  config.setAllowedMethods(List.of("GET","POST"));
  config.setAllowedHeaders(List.of("*"));
  var source = new UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", config);
  return source;
}

Run Locally
# Start PostgreSQL via Docker
docker run --name auricdb -e POSTGRES_PASSWORD=auric -e POSTGRES_USER=auric \
  -e POSTGRES_DB=auric -p 5432:5432 -d postgres:16

# Build & run app
./mvnw spring-boot:run

Testing

- Unit tests with JUnit 5 / MockMvc
- Optional integration tests with Testcontainers (Postgres)

Phase One Scope

 Public GET and POST API
 PostgreSQL persistence

Roadmap

Stripe / FPX payment integration
SendGrid email notifications
AWS S3 for uploads and gallery
Auth & roles (JWT)
CI/CD pipeline + Flyway migrations
Metrics / Observability

## License

- Internal project.
- © Auric Innovations – All rights reserved.


---

