Vehicle Admin Dashboard – Portfolio Checkpoint

A production-style full-stack application with validated backend architecture, stateless authentication, and layered authorization (PBAC + OwnBC).

Core Functionality
-----------------------
Full CRUD for vehicle records via REST API
MySQL persistence with JPA/Hibernate
Frontend reflects real-time backend state
***********************************************
🔐 Security Model
------------------------
Authentication
JWT-based (/api/auth/login)
Stateless
Authorization: Bearer <token>

Authorization (Layered)
PBAC (Permissions)
@PreAuthorize("hasAuthority('VEHICLE_UPDATE')")
VEHICLE_READ
VEHICLE_CREATE
VEHICLE_UPDATE
VEHICLE_DELETE

OwnBC (Ownership)
owner OR VEHICLE_OWNERSHIP_OVERRIDE
Owner → resource owner
Override → admin.ops, superadmin
*****************************************
👥 Roles
-------------
Role	Capabilities
Auditor	Read
Clerk	Read + Create
Manager	Read + Create + Update
Admin	Full CRUD + Override
Superadmin	Full system access
****************************************
🧪 Security Validation
--------------------------
PBAC
----------------------
Role	 |GET	POST PUT DELETE
Auditor|200	403	 403 403
Clerk	 |200	201	 403 403
Manager|200	201  200 403
Admin	 |200	201	 200 204

OwnBC
Scenario	                  Result
Owner modifies own resource	200
Peer non-owner modifies	    403
Admin override	            200
Superadmin bypass	          200

✔ Ownership enforced on PUT/PATCH
✔ Override behavior validated
✔ No authorization leaks or incorrect status codes
***************************************************
⚠️ Exception Handling
---------------------------------
Scenario	Status
Auth failure	401
Access denied	403
Validation	400
Not found	404
Server error	500

✔ Centralized via GlobalExceptionHandler
✔ No incorrect 500 responses for security paths
*********************************************
🧱 Architecture
--------------------------
Feature-based structure
DTO + Mapper (MapStruct)
Service layer handles business logic + authorization
JWT filter for authentication
Centralized exception handling
********************************************
🖥️ Frontend
-----------------------
React + TypeScript + Vite
Modular structure
Functional CRUD UI
JWT-integrated API communication
***************************************
📌 Significance
---------------------------------
This project demonstrates:

Stateless authentication (JWT)
Layered authorization (PBAC + OwnBC)
Correct HTTP semantics
Clean backend architecture
Deterministic security validation
********************************************
*UPDATE*
----------------
Successful vehicle mutations are recorded in an append-only audit log capturing action, target resource, actor, and timestamp. Audit logs are accessible only to elevated roles and cannot be modified through the API
**********************************************
🚀 Next Steps
------------------------
Frontend catch-up
Dockerization
Cloud deployment
