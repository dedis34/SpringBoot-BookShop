# 📚 Online Book Store API

A comprehensive backend application for managing an online bookstore, built with Java and Spring Boot. It offers full CRUD functionality for books, categories, shopping carts, and orders, along with user authentication and role-based access control.

---

## 🎯 Project Overview

This application serves as the backend for an online book store, supporting two roles:

* **Shopper (User):**

    * Sign up / Sign in
    * Browse books by category or all at once
    * View book details
    * Add/remove books to/from cart
    * Place orders and view order history

* **Manager (Admin):**

    * Add, update, delete books
    * Manage categories
    * Update order status

The system is structured around 8 core domain models:
`User`, `Role`, `Book`, `Category`, `ShoppingCart`, `CartItem`, `Order`, `OrderItem`

---

## 🚀 Technologies & Tools

| Tool                | Description                    |
| ------------------- | ------------------------------ |
| Java 21             | Core language                  |
| Spring Boot 3.4.1   | Main framework                 |
| Spring Security     | Authentication & authorization |
| Spring Data JPA     | Data persistence               |
| JWT                 | Token-based authentication     |
| MapStruct           | DTO mapping                    |
| Liquibase           | DB schema management           |
| Hibernate Validator | Input validation               |
| Testcontainers      | Integration tests with Docker  |
| MySQL / H2          | Databases (prod/test)          |
| Swagger             | API documentation              |
| Lombok              | Less boilerplate               |
| Checkstyle          | Code quality                   |

---

## 🔌 API Endpoints Overview

### ✅ Authentication

* `POST /api/auth/register` – Register a new user
* `POST /api/auth/login` – Login and receive a JWT token

### 📚 Book

* `GET /books` – List all books
* `GET /books/{id}` – View book by ID
* `POST /books` – Add a book (admin only)
* `PUT /books/{id}` – Update book
* `DELETE /books/{id}` – Delete book
* `GET /books/search` – Search books

### 🏷️ Category

* `POST /api/categories` – Add a category
* `GET /api/categories` – List categories
* `GET /api/categories/{id}` – View category
* `PUT /api/categories/{id}` – Update category
* `DELETE /api/categories/{id}` – Delete category
* `GET /api/categories/{id}/books` – View books in category

### 🛒 Shopping Cart

* `GET /api/cart` – View cart
* `POST /api/cart` – Add item to cart
* `PUT /api/cart/cart-items/{cartItemId}` – Update item
* `DELETE /api/cart/cart-items/{cartItemId}` – Remove item

### 📦 Order

* `POST /api/orders` – Place an order
* `GET /api/orders` – View order history
* `GET /api/orders/{orderId}/items` – Items in an order
* `GET /api/orders/{orderId}/items/{itemId}` – View specific item
* `PATCH /api/orders/{id}` – Update order status

---

## 🌐 Live Demo

🧪 Swagger UI (hosted on AWS):
[http://ec2-13-60-51-178.eu-north-1.compute.amazonaws.com/swagger-ui/index.html](http://ec2-13-60-51-178.eu-north-1.compute.amazonaws.com/swagger-ui/index.html)

📹 YouTube Demo *(Add your link here)*

---

## 🔐 Default Users

| Role  | Email               | Password       |
| ----- | ------------------- | -------------- |
| Admin | `admin@example.com` | `adminExample` |
| User  | `user@example.com`  | `userExample`  |

> 🛠️ These accounts are automatically created on application startup via `DataInitializer`.

---

## **⚙️ Setup Instructions**

### **✅ Prerequisites**

* Java 17+
* Docker
* Maven

### **🧪 Clone & Run Locally**

```
# Clone repo
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name

# Package app
./mvnw clean package

# Run app with Docker
docker compose up
```
