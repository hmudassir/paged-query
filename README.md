# Paged query

`GET /api/labels?page=0&size=2` asks the repository for one page, ordered by id. The service maps each row to a record, then wraps those records in `PageResponse`.

The HTTP body has `items`, `page`, `size`, `totalItems`, and `totalPages`. It does not contain a JPA entity or Spring's page type.

Page numbers start at 0. Size is from 1 to 20. Five seeded rows make three pages of size 2.

## Run

Java 21.

```sh
./mvnw spring-boot:run
```

```sh
curl -sS 'http://127.0.0.1:8080/api/labels?page=0&size=2'
curl -sS 'http://127.0.0.1:8080/api/labels?page=2&size=2'
```

## Tests

```sh
./mvnw test
```

## What this is

A portfolio proof of concept. The architecture is in [docs/architecture.md](docs/architecture.md).
