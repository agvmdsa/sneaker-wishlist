# Sneaker Wishlist

Track sneakers you want, watch their price, and know the moment one drops. Java/Spring Boot
backend, React/Vite frontend, Postgres, all dockerized.

Progress is tracked in [`BACKLOG.md`](./BACKLOG.md).

## Stack

- **Backend**: Java 21, Spring Boot, Spring Data JPA, Flyway, PostgreSQL
- **Frontend**: React, TypeScript, Vite
- **Infra**: Docker, docker-compose

## Quick start (Docker)

```bash
cp .env.example .env
# fill in KICKSDB_API_KEY (get one at https://kicks.dev)

docker compose up --build
```

- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Running the backend without Docker

Needs Java 21 and a running Postgres (the one from `docker compose up db` works fine).

```bash
cp .env.example .env   # if you haven't already

cd backend
./mvnw spring-boot:run
```

Flyway runs the migrations automatically on startup.

## Running the frontend without Docker

Needs Node.js. The backend must already be reachable at `http://localhost:8080` (CORS is
pre-configured to allow `http://localhost:5173`, which is what this serves on).

```bash
cd frontend
npm install
npm run dev
```

## Environment variables

See [`.env.example`](./.env.example) for the full list (KicksDB API key, Postgres credentials).
Everything else has a sensible default and only needs overriding if you change the default ports.
