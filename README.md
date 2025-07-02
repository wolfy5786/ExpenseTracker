<h1 align="center">💰 Expense Tracker</h1>
<p align="center">A full-stack expense tracking application built with Java, Spring Boot, React, PostgreSQL, and Docker.</p>

<p align="center">
  <img src="https://img.shields.io/badge/backend-SpringBoot-green?style=flat-square" />
  <img src="https://img.shields.io/badge/frontend-React-blue?style=flat-square" />
  <img src="https://img.shields.io/badge/database-PostgreSQL-blue?style=flat-square" />
  <img src="https://img.shields.io/badge/containerized-Docker-blue?style=flat-square" />
</p>

---

## 🚀 Features

- 🔐 **User Authentication** using **JWT**
- ➕ Add, 🗑️ Delete, and ✏️ Update expenses
- 🔎 Filter expenses by:
  - 📅 **Date Range**
  - 🏷️ **Category**
  - 💸 **Highest & Lowest Spend**
- 📊 Sort expenses by amount or date

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker

### Frontend
- React
- Vite
- Axios
- Tailwind CSS 

---




## 🛠 Prerequisites

- Docker installed: https://www.docker.com/products/docker-desktop
- Git installed

---

## 🚀 Getting Started

### 1. Clone the Repository
in bash run
git clone https://github.com/wolfy5786/ExpenseTracker.git
cd ExpenseTracker

### 2. Set up environment variables in docker-compose.yml 

POSTGRES_DB=expensetracker

POSTGRES_USER=postgres

POSTGRES_PASSWORD=password

SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/expensetracker

SPRING_DATASOURCE_USERNAME=postgres

SPRING_DATASOURCE_PASSWORD=password

JWT_SECRET=U9jM3LrT5pQsAxV8NnBxWz2KyReGvHdQ

### 3.In bash run 
docker-compose up --build

🌐 Access the App
Frontend (React) → http://localhost:3000

Backend (Spring Boot) → http://localhost:8080 

demo username : cake
demo password : chef
it has some mock data

To Stop run on bash
docker-compose down


Sample outputs
![image](https://github.com/user-attachments/assets/da9b6d26-890e-47eb-95ae-cb6a9372370f)
![image](https://github.com/user-attachments/assets/fefa6c63-2639-4cb0-9a71-dcd67b7e0209)
![image](https://github.com/user-attachments/assets/3f385ca4-f804-480e-8be8-54be9b618160)
![image](https://github.com/user-attachments/assets/e5158e89-a31c-4160-9700-27ab23105d95)
![image](https://github.com/user-attachments/assets/fd731eec-8ce4-4a5e-9d4c-1d9a21507a47)
![image](https://github.com/user-attachments/assets/e7c90e72-e40a-4fd8-8b94-31b4132a113e)


