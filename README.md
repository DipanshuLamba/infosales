# InfoSales AI – Sales Insight Automator

## Overview

InfoSales AI is a cloud-ready DevOps prototype that allows users to upload sales data files (.csv or .xlsx) and receive an AI-generated executive summary via email.

The application processes raw sales data, generates insights using a Large Language Model (Google Gemini), and automatically delivers the summary to a recipient email.

This project demonstrates **AI integration, containerization, CI/CD automation, API security, and cloud deployment**.

---

# Architecture

User Upload File
↓
Frontend (HTML SPA)
↓
Spring Boot API
↓
CSV/XLSX Processing
↓
Sales Data Analysis
↓
Gemini AI Summary
↓
Email Sent to Recipient

---

# Tech Stack

### Frontend

HTML
JavaScript

### Backend

Java Spring Boot

### AI Engine

Google Gemini API

### DevOps

Docker
Docker Compose
GitHub Actions CI

### Hosting

Render (Backend)
Vercel (Frontend)

---

# Running the Project Locally

### Clone the repository

git clone https://github.com/DipanshuLamba/infosales.git
cd infosales

---

### Create environment file

Copy the example file:

cp .env.example .env

Fill the environment variables with your API keys and email credentials.

---

### Run the stack

docker compose up --build

---

### Backend will run at

http://localhost:8080

---

### Swagger API documentation

http://localhost:8080/swagger-ui.html

---

# Secured Endpoints

The API includes several security measures:

• File type validation (.csv / .xlsx only)
• File size limit (5MB max upload)
• Required column validation
• Email format validation
• Centralized exception handling
• Environment variables for secrets
• CORS protection

These measures protect the API from malicious uploads and misuse.

---

# CI/CD Pipeline

GitHub Actions pipeline automatically:

1. Checks out repository
2. Installs Java environment
3. Builds the backend with Maven
4. Builds Docker image

Triggered on pushes and pull requests to the **main branch**.

---

# Environment Variables

Example `.env.example`

GEMINI_API_KEY=your_gemini_api_key

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=[your_email@gmail.com](mailto:your_email@gmail.com)
MAIL_PASSWORD=your_email_app_password

---

# Deployment

### Frontend (Vercel)

https://infosales.vercel.app/

---

### Backend (Render)

https://infosales.onrender.com/

---

### Swagger Documentation

http://localhost:8080/swagger-ui/index.html#/analysis-controller/analyzeFile

---

# Features

Upload CSV / XLSX sales file
AI-generated executive sales summary
Automated email delivery
Swagger API documentation
Docker containerization
CI/CD pipeline
Cloud deployment

---

# Future Improvements

Rate limiting
Authentication layer
Database persistence
Interactive dashboard analytics
