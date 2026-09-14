# Backlog — Sneaker Wishlist

This file is the single source of truth for project progress. Spec/plan/task-breakdown tooling
(Spec Kit) is used locally to design the work, but its working files are not committed (see
`.gitignore`) — this checklist is what ships instead.

Ticket convention: `SNKR-<n>: <what changed>`, one ticket per atomic commit.

## Backend — Setup & Infra

- [x] **SNKR-2** — initialize monorepo, add root `.gitignore` (`9f98ae8`)
- [x] **SNKR-3** — generate Spring Boot skeleton: web, jpa, validation, flyway, postgresql, cache, testcontainers (`7770c71`)
- [x] **SNKR-5** — add `.env.example` for KicksDB/Postgres config (`360e0d8`)
- [x] **SNKR-81** — add Lombok, use it for entity getters/setters/constructors (`cf63017`)
- [x] **SNKR-91** — pin backend build to JDK 21 via Maven Toolchains, incl. inside the Docker build stage (`08051a4`, `01cfc69`)
- [x] **SNKR-77** — add backend Dockerfile (multi-stage Maven build + JRE runtime) (`9d1866b`)
- [x] **SNKR-79** — add `docker-compose.yml` with db and backend services (`5385805`)

## Backend — Database & Persistence

- [x] **SNKR-7** — wire datasource/flyway config, `wishlist_item` migration (`744878a`)
- [x] **SNKR-8** — `tag` and `wishlist_item_tag` migration (`417a918`)
- [x] **SNKR-9** — `brand` migration, `brand_id` column (`3a001ed`)
- [x] **SNKR-10** — `price_history` migration, `price_drop_detected` column (`dd21341`)
- [x] **SNKR-11** — `WishlistItem`, `Brand`, `Tag`, `PriceHistory` JPA entities (`eb06f29`)
- [x] **SNKR-87** — `WishlistItemRepository`, `BrandRepository`, `TagRepository`, `PriceHistoryRepository` (`c6642f4`)
- [x] **SNKR-92** — unique constraint on active `(external_sneaker_id, size)`, idempotent `addItem` (`f593fb2`)

## Backend — External Catalog Integration (KicksDB)

- [x] **SNKR-13** — `SneakerCatalogClient` interface (`3909f44`)
- [x] **SNKR-14** — `KicksDbSneakerCatalogClient` using `RestTemplate`; named endpoint path constants (`a82120c`, `f9963b7`)
- [x] **SNKR-15** — `RestTemplateConfig`: KicksDB base URL + auth header (`0e10f2d`)
- [x] **SNKR-94** — `SneakerSummaryDto` (`6535205`)
- [x] **SNKR-95** — `SneakerService`: deserialize, map to DTO, filter non-sneaker results (`94b635a`)
- [x] **SNKR-16** — `GET /api/sneakers/search`, routed through `SneakerService` (`416a52c`, `94c184d`)
- [x] **SNKR-17** — `GET /api/sneakers/{id}` (`daa7c75`)
- [x] **SNKR-18** — `GET /api/sneakers/brands` with `@Cacheable` (`61e6820`)

## Backend — Wishlist CRUD

- [x] **SNKR-20** — `WishlistItemCreateDto` with Bean Validation (`a0f3ab5`)
- [x] **SNKR-21** — `addItem` use case: snapshot + resolve/create `Brand` on the fly (`f019ff5`)
- [x] **SNKR-22** — `POST /api/wishlist` + `WishlistItemDto` (`69370a9`)
- [x] **SNKR-23** — `POST /api/wishlist/check` batch dedup endpoint (`5d33c51`)
- [x] **SNKR-25** — `GET /api/wishlist` paginated, status/tag filter (`cb5c29c`)
- [x] **SNKR-26** — `PATCH /api/wishlist/{id}` edit size/notes (`96d21c5`)
- [x] **SNKR-27** — `DELETE /api/wishlist/{id}` soft delete (`a94c907`)
- [x] **SNKR-98** — replace `WishlistService`/`SneakerService` with per-operation Use Cases (`9c9dd4e`)

## Backend — Status & Business Rules

- [x] **SNKR-29** — `WishlistStatus.canTransitionTo` transition rule (`630d79f`)
- [x] **SNKR-30** — `PATCH /api/wishlist/{id}/status`, idempotent, rejects invalid transitions (`a147204`)

## Backend — Tags

- [x] **SNKR-33** — `POST /api/wishlist/{id}/tags` (`6d603d2`)
- [x] **SNKR-34** — `DELETE /api/wishlist/{id}/tags/{tagId}` (`8702e4f`)

## Backend — Stats

- [x] **SNKR-37** — `GET /api/wishlist/stats` (`249f527`)

## Backend — Error Handling

- [x] **SNKR-45** — `ErrorResponseDto` + `GlobalExceptionHandler` shell (`b473e8b`)
- [x] **SNKR-46** — `MethodArgumentNotValidException` handler (`f167bb5`)
- [x] **SNKR-47** — `ResponseStatusException` handler: not-found, invalid transition, missing `price_paid`, external API error (`0ea037d`)
- [x] **SNKR-49** — catch-all `Exception` handler (500, generic message) (`9900998`)

## Backend — Price Tracking & Scheduling

- [x] **SNKR-40** — `CheckWantedItemPricesUseCase`: walk WANT items, record price history, flag drops, isolate per-item failures (`791eccb`)
- [x] **SNKR-39** — `PriceCheckScheduler`, scheduling enabled (`b878cda`)

## Backend — API Documentation

- [x] **SNKR-51** — add springdoc-openapi (`d5a44eb`)
- [x] **SNKR-52** — Swagger UI metadata: title, description, version (`b04a79b`)
- [x] **SNKR-99** — `@Tag`/`@Operation` on controllers (`768d106`)
- [x] **SNKR-100** — `@Schema` examples on request/response DTOs (`4260ed3`)
- [x] **SNKR-101** — per-endpoint `@ApiResponses` (`78de1e3`)

## Backend — Hardening

- [ ] **SNKR-102** — add CORS configuration allowing the frontend's dev/prod origins
- [ ] **SNKR-103** — add `GET /api/wishlist/{id}/price-history` endpoint (`PriceHistoryDto`, dedicated Use Case)
- [ ] **SNKR-104** — expose a tag id alongside each tag name on `WishlistItemDto`
- [ ] **SNKR-105** — add automated backend tests: unit tests for Use Cases, Testcontainers-backed integration tests for repositories/controllers
- [ ] **SNKR-106** — add a `frontend` service to `docker-compose.yml`
- [ ] **SNKR-107** — add root `README.md` (setup + run instructions for backend and frontend)

## Frontend — Scaffolding

- [ ] **SNKR-108** — scaffold Vite + React + TypeScript project (`frontend/`), bulletproof-react feature-based structure
- [ ] **SNKR-109** — add configured `axios` client (`lib/api-client.ts`) with error-normalizing interceptor
- [ ] **SNKR-110** — add Zod schemas + inferred types mirroring backend DTOs
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
