## Vehicle Admin Dashboard – Portfolio Checkpoint

At this stage, the Vehicle Admin Dashboard represents a **secure, production-style full-stack application** with validated backend architecture and access control.

### Core Functionality

The system supports full vehicle lifecycle management:

* Create, read, update, and delete vehicle records via REST API
* Persistent storage using MySQL with JPA/Hibernate
* Frontend reflects real-time backend state through structured API integration

---

## 🔐 Security Model (JWT + PBAC)

The backend implements **stateless authentication and permission-based access control (PBAC)**.

### Authentication

* JWT-based authentication (`/api/auth/login`)
* Stateless session management
* Tokens sent via:

```http
Authorization: Bearer <token>
```

---

### Authorization (PBAC)

Access is enforced at the **service layer** using:

```java
@PreAuthorize("hasAuthority('VEHICLE_CREATE')")
```

Permission hierarchy:

```
User → Role → Permission
```

#### Permissions

* VEHICLE_READ
* VEHICLE_CREATE
* VEHICLE_UPDATE
* VEHICLE_DELETE

---

## 👥 Seeded Users (Test Matrix)

| User             | Role       | Access Level           |
| ---------------- | ---------- | ---------------------- |
| auditor.readonly | Auditor    | Read only              |
| vehicle.clerk    | Clerk      | Read + Create          |
| fleet.manager    | Manager    | Read + Create + Update |
| admin.ops        | Admin      | Full CRUD              |
| superadmin       | Superadmin | Full system access     |

---

## 🧪 PBAC Verification (Completed)

The permission model has been **fully validated using a structured test matrix**.

### Expected Behavior

| Role       | GET | POST | PUT | DELETE |
| ---------- | --: | ---: | --: | -----: |
| Auditor    | 200 |  403 | 403 |    403 |
| Clerk      | 200 |  201 | 403 |    403 |
| Manager    | 200 |  201 | 200 |    403 |
| Admin      | 200 |  201 | 200 |    204 |
| Superadmin | 200 |  201 | 200 |    204 |

### Verified Outcomes

* Least-privilege access enforced correctly
* All unauthorized actions return **403 Forbidden**
* No incorrect permission escalation observed
* No security failures return 500

---

## ⚠️ Exception Handling (Standardized)

The API now returns **correct and consistent HTTP status codes**:

| Scenario               | Status                    |
| ---------------------- | ------------------------- |
| Authentication failure | 401 Unauthorized          |
| Authorization failure  | 403 Forbidden             |
| Validation errors      | 400 Bad Request           |
| Resource not found     | 404 Not Found             |
| Unexpected errors      | 500 Internal Server Error |

### Key Improvements

* Fixed incorrect `500` responses for auth failures
* Mapped `AuthenticationException` → 401
* Mapped `AuthorizationDeniedException` → 403
* Centralized error handling via `GlobalExceptionHandler`

---

## 🧱 Architecture Highlights

* Feature-based package structure
* DTO + Mapper separation (MapStruct)
* Service layer owns business logic and authorization
* Spring Security handles authentication (JWT filter)
* Global exception handling ensures consistent API responses

---

## 🖥️ Frontend State

* React + TypeScript + Vite
* Modular feature-based structure
* Functional modals (create/edit/delete)
* Toast notifications and state management
* Integrated with secured backend via JWT

---

## 📌 Portfolio Significance

This checkpoint represents a transition from a functional CRUD application to a **secure, production-aligned backend system**.

It demonstrates:

* Real-world authentication and authorization patterns
* Correct HTTP semantics under failure conditions
* Clean architectural separation
* Deterministic test validation (PBAC matrix)

The system is now stable and ready for:

* Audit trail (repudiation)
* Ownership-based access control (OwnBC)
* Dockerization and cloud deployment

---

## Optional refinement (small but valuable)

If you want to push it one level higher, you can add:

```markdown
## 🔍 Next Steps

- Implement audit logging (who did what, when)
- Introduce ownership-based access control
- Dockerize backend and frontend
- Deploy to AWS / Azure
```

---

This version positions your project not as “a CRUD app” but as **a secured backend system with verified access control**, which is a materially stronger signal to anyone reviewing your portfolio.
