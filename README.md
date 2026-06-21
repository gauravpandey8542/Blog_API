# Blog REST API

A secure RESTful API for a blogging platform built with **Spring Boot**, featuring full CRUD operations for Users, Posts, and Comments, JWT-based authentication, and a clean layered architecture.

## Features

- 🔐 JWT-based authentication and authorization
- 🔒 Password hashing with BCrypt
- 📝 Full CRUD for Users, Posts, and Comments
- 🔗 Relational data modeling (One-to-Many / Many-to-One) with JPA
- 🛡️ Stateless session management
- 📦 DTO pattern to prevent sensitive data exposure
- ✅ Postman-tested endpoints

## Tech Stack

- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security**
- **JWT (jjwt)**
- **MySQL**
- **Maven**

## Project Structure

```
blog-api/
├── src/main/java/com/example/blog/
│   ├── BlogApiApplication.java
│   │
│   ├── entity/
│   │   ├── User.java
│   │   ├── Post.java
│   │   └── Comment.java
│   │
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── PostRepository.java
│   │   └── CommentRepository.java
│   │
│   ├── service/
│   │   ├── UserService.java
│   │   ├── PostService.java
│   │   └── CommentService.java
│   │
│   ├── controller/
│   │   ├── UserController.java
│   │   ├── PostController.java
│   │   ├── CommentController.java
│   │   └── AuthController.java
│   │
│   ├── dto/
│   │   ├── UserDTO.java
│   │   ├── PostDTO.java
│   │   └── CommentDTO.java
│   │
│   └── security/
│       ├── JwtUtil.java
│       ├── JwtFilter.java
│       └── SecurityConfig.java
│
├── src/main/resources/
│   └── application.properties
│
└── pom.xml
```

## Entity Relationships

```
User (1) ──────< (many) Post
User (1) ──────< (many) Comment
Post (1) ──────< (many) Comment
```

- A **User** can have many **Posts** and many **Comments**.
- A **Post** belongs to one **User** and can have many **Comments**.
- A **Comment** belongs to one **User** and one **Post**.

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven
- MySQL Server

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/blog-api.git
   cd blog-api
   ```

2. Configure your database in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. Build and run
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

The API will start on `http://localhost:8080`.

## API Endpoints

### 🔑 Auth
 
| Method | Endpoint           | Description                | Auth Required |
|--------|-----------------|-------------------------------|-----|
| POST | `/api/auth/login` | Login and receive a JWT token | ❌ |

### 👤 Users

| Method | Endpoint               | Description         | Auth Required |
|--------|------------------------|---------------------|-----|
| POST   | `/api/users/register`  | Register a new user | ❌ |
| GET    | `/api/users/{id}`      | Get user by ID      | ✅ |
| PUT    | `/api/users/{id}`      | Update user         | ✅ |
| DELETE | `/api/users/{id}`      | Delete user         | ✅ |

### 📝 Posts

| Method | Endpoint                   | Description              | Auth Required |
|--------|----------------------------|--------------------------|----------------|
| POST   | `/api/posts/user/{userId}` | Create a post for a user | ✅ |
| GET    | `/api/posts`               | Get all posts            | ✅ |
| GET    | `/api/posts/{id}`          | Get post by ID           | ✅ |
| GET    | `/api/posts/user/{userId}` | Get all posts by a user  | ✅ |
| PUT    | `/api/posts/{id}`          | Update post              | ✅ |
| DELETE | `/api/posts/{id}`          | Delete post              | ✅ |

### 💬 Comments

| Method | Endpoint                                    | Description                | Auth Required |
|--------|---------------------------------------------|----------------------------|----------------|
| POST   | `/api/comments/post/{postId}/user/{userId}` | Add a comment to a post    | ✅ |
| GET    | `/api/comments/post/{postId}`               | Get all comments on a post | ✅ |
| GET    | `/api/comments/{id}`                        | Get comment by ID          | ✅ |
| PUT    | `/api/comments/{id}`                        | Update comment             | ✅ |
| DELETE | `/api/comments/{id}`                        | Delete comment             | ✅ |

## Authentication Flow

1. Register a user via `POST /api/users/register`
2. Login via `POST /api/auth/login` with email and password
3. Server responds with a JWT token:
   ```json
   { "token": "eyJhbGciOiJIUzI1NiJ9..." }
   ```
4. Include this token in the `Authorization` header for all protected requests:
   ```
   Authorization: Bearer <token>
   ```

## Sample Requests

### Register
```http
POST /api/users/register
Content-Type: application/json

{
    "name": "Gaurav Pandey",
    "email": "gaurav@example.com",
    "password": "yourpassword"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "gaurav@example.com",
    "password": "yourpassword"
}
```

### Create Post (Authenticated)
```http
POST /api/posts/user/1
Authorization: Bearer <token>
Content-Type: application/json

{
    "title": "My First Blog",
    "content": "Hello world, this is my first blog post."
}
```

### Add Comment (Authenticated)
```http
POST /api/comments/post/1/user/2
Authorization: Bearer <token>
Content-Type: application/json

{
    "content": "Great post!"
}
```

## Security Notes

- Passwords are hashed using **BCrypt** before being stored.
- JWTs are signed with HMAC-SHA256 and expire after 10 hours.
- Sessions are stateless — no server-side session storage.
- Sensitive fields (e.g. passwords) are excluded from API responses via DTOs.

## Future Improvements

- [ ] Global exception handling with `@ControllerAdvice`
- [ ] Pagination and sorting for post/comment listing
- [ ] Role-based access control (e.g. admin vs regular user)
- [ ] Refresh token support
- [ ] Move JWT secret key to environment variables

## Author

**Gaurav Pandey**
Built as a learning project to practice Spring Boot, JPA relationships, DTO patterns, and JWT authentication.

## License

This project is open source and available under the [MIT License](LICENSE).
