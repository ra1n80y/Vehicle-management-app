# Vehicle Management System

A full‑stack portfolio application demonstrating production‑grade architecture, stateless authentication, layered authorization (PBAC + Ownership‑Based Control), and a modern React frontend.

---

## 🚀 Tech Stack

| Layer       | Technology                                           |
|-------------|------------------------------------------------------|
| Backend     | Spring Boot 3, Java 17, Spring Security, JPA/Hibernate |
| Database    | MySQL                                                |
| Auth        | JWT (stateless) with RBAC/PBAC                        |
| Frontend    | React 18, TypeScript, Vite, React Bootstrap           |
| Build Tool  | Maven                                                |

---

## ✨ Features

- **Vehicle Management** – full CRUD with ownership enforcement
- **User Management** – create, edit, patch, and delete system users; assign multiple roles
- **Role Management** – define roles with granular permissions (CRUD for roles)
- **Audit Logging** – append‑only log of successful mutations (create, update, patch, delete) visible to authorized roles; detail modal on row click
- **Permission‑Aware UI** – sidebar links and action buttons are conditionally rendered based on JWT‑decoded permissions
- **Global Error Handling** – consistent JSON error responses (400, 401, 403, 404, 409, 500)
- **Input Validation** – server‑side with Jakarta Bean Validation, client‑side with React Hook Form & Bootstrap

---

## 🔐 Security Model

### Authentication
- JWT‑based stateless auth via `/api/auth/login`
- Tokens contain username, roles, and permissions
- apiClient attaches `Authorization: Bearer <token>`

### Authorization – Two‑Layer

#### 1. Permission‑Based (PBAC)
Method‑level security with `@PreAuthorize` using the `Permissions` constants:

```
VEHICLE_READ, VEHICLE_CREATE, VEHICLE_UPDATE, VEHICLE_DELETE
VEHICLE_OWNERSHIP_OVERRIDE             
AUDIT_READ
USER_READ, USER_CREATE, USER_UPDATE, USER_DELETE
ROLE_READ, ROLE_MANAGE
```

#### 2. Ownership‑Based Control (OwnBC)
- Vehicles belong to an `owner` (User)
- Only the owner (or users with `VEHICLE_OWNERSHIP_OVERRIDE`) can update or delete a vehicle
- Service‑layer checks prevent unauthorized modifications

---

## 👥 Roles & Permissions Matrix

| Role        | Permissions                                                         |
|-------------|---------------------------------------------------------------------|
| AUDITOR     | `VEHICLE_READ`, `USER_READ`, `AUDIT_READ`                           |
| CLERK       | above + `VEHICLE_CREATE`                                            |
| MANAGER     | above + `VEHICLE_UPDATE`, `USER_READ`                               |
| ADMIN       | all of the above + `VEHICLE_DELETE`, `VEHICLE_OWNERSHIP_OVERRIDE`, `USER_CREATE`, `USER_UPDATE`, `USER_DELETE`, `ROLE_READ`, `ROLE_MANAGE` |
| SUPERADMIN  | every permission in the system                                      |

> Seeded users: `superadmin`, `admin.ops`, `fleet.manager`, `vehicle.clerk`, `auditor.readonly`, `fleet.manager2` (passwords are the same: `super123`, `admin123`, `manager123`, etc.)

---

## 🧪 Security Validation Highlights

- **RBAC/PBAC** tested: lower roles receive `403 Forbidden` on restricted endpoints; higher roles succeed
- **Ownership** enforced: non‑owner peer gets `403`; owner or admin with override succeeds
- **Audit logs** are read‑only; no API endpoint can modify them
- All exceptions mapped to correct HTTP status codes (no 500s for auth errors)
- Global exception handler ensures consistent error responses

---

## 🗄️ API Endpoints

| Method   | Endpoint              | Required Permission     | Description                     |
|----------|-----------------------|-------------------------|----------------------------------|
| POST     | `/api/auth/login`     | none                    | Authenticate & receive JWT      |
| GET      | `/api/vehicles`       | `VEHICLE_READ`          | List all vehicles               |
| POST     | `/api/vehicles`       | `VEHICLE_CREATE`        | Create a vehicle (auto‑own)     |
| PUT      | `/api/vehicles/{id}`  | `VEHICLE_UPDATE` + own  | Update vehicle                  |
| PATCH    | `/api/vehicles/{id}`  | `VEHICLE_UPDATE` + own  | Partially update vehicle        |
| DELETE   | `/api/vehicles/{id}`  | `VEHICLE_DELETE` + own  | Delete vehicle                  |
| GET      | `/api/users`          | `USER_READ`             | List all users                  |
| POST     | `/api/users`          | `USER_CREATE`           | Create a new user               |
| PUT      | `/api/users/{id}`     | `USER_UPDATE`           | Update user (roles, active)     |
| PATCH    | `/api/users/{id}`     | `USER_UPDATE`           | Patch user                      |
| DELETE   | `/api/users/{id}`     | `USER_DELETE`           | Delete user                     |
| GET      | `/api/roles`          | `ROLE_READ`             | List all roles                  |
| POST     | `/api/roles`          | `ROLE_MANAGE`           | Create a new role               |
| PUT      | `/api/roles/{id}`     | `ROLE_MANAGE`           | Update role                     |
| PATCH    | `/api/roles/{id}`     | `ROLE_MANAGE`           | Patch role                      |
| DELETE   | `/api/roles/{id}`     | `ROLE_MANAGE`           | Delete role                     |
| GET      | `/api/audit-logs`     | `AUDIT_READ`            | List all audit logs             |

---

## 🖥️ Frontend Architecture

- **Auth flow** – `AuthContext` + JWT decoding; protected routes via `ProtectedRoute`
- **Permission‑aware UI** – `hasPermission(user, permission)` drives sidebar links and action button visibility
- **Feature modules** – each domain (Vehicles, Users, Audit Logs, Dashboard) contains its own components, services, types, and CSS
- **Shared components** – `CommonModal`, `ToastNotification`, `apiClient` (fetch wrapper with auto‑401 logout)
- **UX polish** – toast notifications, loading/empty/error states, delete confirmations, detail modals for audit logs

---

## 📁 Project Structure (High‑Level)

```
backend/
├── audit/        # Audit log entity, service, controller
├── common/       # Exceptions, global handler, seeder
├── role/         # Role + Permission entities, service, controller
├── security/     # JWT filter, auth config, permissions constants
├── user/         # User entity, service, controller
├── vehicle/      # Vehicle entity, service, controller

frontend/
├── App/          # App entry, router
├── Features/
│   ├── Auth/     # Login page, context, JWT utils
│   ├── Dashboard/# AppShell, sidebar, topbar, dashboard page
│   ├── Vehicles/ # CRUD components, form, services
│   ├── Users/    # User management, role assignment
│   └── AuditLogs/# Read‑only log table + detail modal
├── Shared/       # apiClient, toast, modal, date formatter
└── Styles/       # global styles
```

---

## 🔧 Recent Improvements (Full‑Stack Polish)

- **Backend**: Refactored service layer – extracted helpers, unified role/permission resolution, added duplicate resource detection (409), improved authorization annotations, added audit logging to all mutation services, removed stubs, upgraded to Java 17 idioms.
- **Frontend**: Replaced default Vite CSS with a clean reset; extracted shared date formatter; unified service object pattern; removed leftover dev comments; cleaned up form ref handling; updated dashboard copy; applied consistent styling to login page.

---

## 🚀 Next Steps

- Dockerize backend + database + frontend
- Deploy to a cloud platform (render/railway/vercel)

---
