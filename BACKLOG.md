# Backlog — Sneaker Wishlist

This file is the single source of truth for project progress. Spec/plan/task-breakdown tooling
(Spec Kit) is used locally to design the work, but its working files are not committed (see
`.gitignore`) — this checklist is what ships instead.

Ticket convention: `SNKR-<n>: <what changed>`, one ticket per atomic commit.

## Backend — Setup & Infra

- [x] **SNKR-2** — initialize monorepo, add root `.gitignore`
- [x] **SNKR-3** — generate Spring Boot skeleton: web, jpa, validation, flyway, postgresql, cache, testcontainers
- [x] **SNKR-5** — add `.env.example` for KicksDB/Postgres config
- [x] **SNKR-81** — add Lombok, use it for entity getters/setters/constructors
- [x] **SNKR-91** — pin backend build to JDK 21 via Maven Toolchains, incl. inside the Docker build stage
- [x] **SNKR-77** — add backend Dockerfile (multi-stage Maven build + JRE runtime)
- [x] **SNKR-79** — add `docker-compose.yml` with db and backend services

## Backend — Database & Persistence

- [x] **SNKR-7** — wire datasource/flyway config, `wishlist_item` migration
- [x] **SNKR-8** — `tag` and `wishlist_item_tag` migration
- [x] **SNKR-9** — `brand` migration, `brand_id` column
- [x] **SNKR-10** — `price_history` migration, `price_drop_detected` column
- [x] **SNKR-11** — `WishlistItem`, `Brand`, `Tag`, `PriceHistory` JPA entities
- [x] **SNKR-87** — `WishlistItemRepository`, `BrandRepository`, `TagRepository`, `PriceHistoryRepository`
- [x] **SNKR-92** — unique constraint on active `(external_sneaker_id, size)`, idempotent `addItem`

## Backend — External Catalog Integration (KicksDB)

- [x] **SNKR-13** — `SneakerCatalogClient` interface
- [x] **SNKR-14** — `KicksDbSneakerCatalogClient` using `RestTemplate`; named endpoint path constants
- [x] **SNKR-15** — `RestTemplateConfig`: KicksDB base URL + auth header
- [x] **SNKR-94** — `SneakerSummaryDto`
- [x] **SNKR-95** — `SneakerService`: deserialize, map to DTO, filter non-sneaker results
- [x] **SNKR-16** — `GET /api/sneakers/search`, routed through `SneakerService`
- [x] **SNKR-17** — `GET /api/sneakers/{id}`
- [x] **SNKR-18** — `GET /api/sneakers/brands` with `@Cacheable`

## Backend — Wishlist CRUD

- [x] **SNKR-20** — `WishlistItemCreateDto` with Bean Validation
- [x] **SNKR-21** — `addItem` use case: snapshot + resolve/create `Brand` on the fly
- [x] **SNKR-22** — `POST /api/wishlist` + `WishlistItemDto`
- [x] **SNKR-23** — `POST /api/wishlist/check` batch dedup endpoint
- [x] **SNKR-25** — `GET /api/wishlist` paginated, status/tag filter
- [x] **SNKR-26** — `PATCH /api/wishlist/{id}` edit size/notes
- [x] **SNKR-27** — `DELETE /api/wishlist/{id}` soft delete
- [x] **SNKR-98** — replace `WishlistService`/`SneakerService` with per-operation Use Cases

## Backend — Status & Business Rules

- [x] **SNKR-29** — `WishlistStatus.canTransitionTo` transition rule
- [x] **SNKR-30** — `PATCH /api/wishlist/{id}/status`, idempotent, rejects invalid transitions

## Backend — Tags

- [x] **SNKR-33** — `POST /api/wishlist/{id}/tags`
- [x] **SNKR-34** — `DELETE /api/wishlist/{id}/tags/{tagId}`

## Backend — Stats

- [x] **SNKR-37** — `GET /api/wishlist/stats`

## Backend — Error Handling

- [x] **SNKR-45** — `ErrorResponseDto` + `GlobalExceptionHandler` shell
- [x] **SNKR-46** — `MethodArgumentNotValidException` handler
- [x] **SNKR-47** — `ResponseStatusException` handler: not-found, invalid transition, missing `price_paid`, external API error
- [x] **SNKR-49** — catch-all `Exception` handler (500, generic message)

## Backend — Price Tracking & Scheduling

- [x] **SNKR-40** — `CheckWantedItemPricesUseCase`: walk WANT items, record price history, flag drops, isolate per-item failures
- [x] **SNKR-39** — `PriceCheckScheduler`, scheduling enabled

## Backend — API Documentation

- [x] **SNKR-51** — add springdoc-openapi
- [x] **SNKR-52** — Swagger UI metadata: title, description, version
- [x] **SNKR-99** — `@Tag`/`@Operation` on controllers
- [x] **SNKR-100** — `@Schema` examples on request/response DTOs
- [x] **SNKR-101** — per-endpoint `@ApiResponses`

## Backend — Hardening

- [x] **SNKR-102** — add CORS configuration allowing the frontend's dev/prod origins
- [x] **SNKR-103** — add `GET /api/wishlist/{id}/price-history` endpoint (`PriceHistoryDto`, dedicated Use Case)
- [x] **SNKR-104** — expose a tag id alongside each tag name on `WishlistItemDto`
- [x] **SNKR-105** — add automated backend unit tests for Use Cases (Mockito)

## Frontend — Scaffolding

- [x] **SNKR-106** — scaffold Vite + React + TypeScript project (`frontend/`), bulletproof-react feature-based structure
- [x] **SNKR-107** — add frontend Dockerfile and wire a `frontend` service into `docker-compose.yml`
- [x] **SNKR-108** — add root `README.md` (setup + run instructions for backend and frontend)
- [x] **SNKR-109** — add configured `axios` client (`lib/api-client.ts`) with error-normalizing interceptor
- [x] **SNKR-110** — add Zod schemas + inferred types mirroring backend DTOs
- [ ] **SNKR-111** — add React Router setup: `/`, `/wishlist/:id`, `/search`, `/stats`
- [ ] **SNKR-112** — add shared loading/error/empty state components

## Frontend — Wishlist List & Filters

- [ ] **SNKR-113** — `useWishlistItems` hook + paginated wishlist list view
- [ ] **SNKR-114** — status and tag filters on the wishlist list

## Frontend — Add to Wishlist

- [ ] **SNKR-115** — catalog search view + `useSneakerSearch` hook
- [ ] **SNKR-116** — add-to-wishlist flow: size selection, dedup check via `/wishlist/check`, submit

## Frontend — Item Lifecycle

- [ ] **SNKR-117** — item detail view, edit size/notes
- [ ] **SNKR-118** — status-change control with transition guard + conditional `pricePaid` field
- [ ] **SNKR-119** — remove-item action

## Frontend — Tags

- [ ] **SNKR-120** — tag management UI (add/remove) on item detail

## Frontend — Price History & Stats

- [ ] **SNKR-121** — price history view on item detail
- [ ] **SNKR-122** — stats view

## Frontend — Quality

- [ ] **SNKR-123** — automated frontend tests (Vitest + React Testing Library) for hooks and key components

## Stretch Goals (end of project, only if time remains)

- [ ] **SNKR-124** — define `Command`/`Query`/`CommandHandler`/`QueryHandler` marker interfaces
- [ ] **SNKR-125** — move wishlist write Use Cases into `usecase.wishlist.command`, implement `CommandHandler`
- [ ] **SNKR-126** — move wishlist read Use Cases into `usecase.wishlist.query`, implement `QueryHandler`
- [ ] **SNKR-127** — move sneaker catalog Use Cases into `usecase.sneaker.query`, implement `QueryHandler`
- [ ] **SNKR-128** — update the constitution's Principle I to name the Command/Query convention
- [ ] **SNKR-129** — upgrade `spring-boot-starter-parent` 3.5.3 → 4.1.1
- [ ] **SNKR-130** — add Testcontainers-backed integration tests for repositories and controllers
