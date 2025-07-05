# FealtyX Assignment – Java + Spring Boot + Ollama Integration

This project is a submission for the **FealtyX Backend Assignment**.  
Although the assignment allowed implementation in Go, Java, or Python, this version is implemented in **Java using Spring Boot**.

[![Java 17+](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org/)

## ✅ Features

- ✅ Full CRUD operations for managing students
- 🤖 AI-based profile summary using **Ollama** (`mistral` / `llama3`)
- 🧠 In-memory storage (no DB required)
- 🔒 Input validation using annotations
- 🛡️ Global exception handling
- 🔀 Thread-safe with `ConcurrentHashMap`
- 🧪 Tested with Postman and curl

## 🧑‍🎓 Student Entity

Each student has the following attributes:

| Field | Type | Validation |
|-------|------|------------|
| `id` | Integer | Auto-generated |
| `name` | String | Not blank |
| `age` | Integer | Not null |
| `email` | String | Must be valid email format |

## 📦 Project Structure

### `controllers/`
Contains the REST API endpoints for handling HTTP requests.

### `exceptions/`
Contains global exception handling for the API.

### `model/`
Contains the data models used for request and response bodies.

### `services/`
Contains the business logic of the application.

### `FealtyXAssignmentApplication.java`
The main entry point of the application.

### `resources/`
Contains configuration files and other resources.

## 🔗 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/students` | Create a new student |
| `GET` | `/students` | Fetch all students |
| `GET` | `/students/{id}` | Fetch student by ID |
| `PUT` | `/students/{id}` | Update student by ID |
| `DELETE` | `/students/{id}` | Delete student by ID |
| `GET` | `/students/{id}/summary` | 🔥 Get AI-generated summary via **Ollama** |

## 🧠 Ollama AI Integration

This project integrates with [Ollama](https://www.ollama.com/) to generate smart summaries of student profiles using language models such as `mistral` or `llama3`.

### 🛠️ How it works

- Prompt is constructed using student details (name, age, email)
- Sent to: `http://localhost:11434/api/generate`
- Model: `"mistral:instruct-q4_0"` (lightweight, memory-efficient)
- Response is returned as summary

### 🔧 Sample Payload

```json
{
  "model": "mistral:instruct-q4_0",
  "prompt": "Summarize the following student:\nName: Alice\nAge: 22\nEmail: alice@example.com"
}
```
# 🚀 Getting Started

## 📋 Prerequisites

- **Java 17+**  
  Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use OpenJDK

- **Maven 3.6+**  
  Install from [Apache Maven](https://maven.apache.org/install.html)

- **Ollama**  
  Install with a lightweight model:
  ```bash
  curl -fsSL https://ollama.com/install.sh | sh
  ollama pull mistral:instruct-q4_0
  ```
  ## 🧪 Running the App

  
### 🧠 Start Ollama Server

```bash
ollama serve
```
## 🧪 Test Endpoints

Once the application is running, you can test the API using **Postman** or **curl**.

### 🧪 Sample curl Commands

#### Create a Student:

```bash
`curl -X POST http://localhost:8080/students \   -H "Content-Type: application/json" \   -d '{"name": "Alice", "age": 22, "email": "alice@example.com"}'`
```
#### Get All Students:

```bash
CopyEdit

`curl http://localhost:8080/students`
```
#### Get Summary of a Student (e.g., student with ID 1):

```bash

CopyEdit

`curl http://localhost:8080/students/1/summary`
```
* * *

## ⚠️ Common Errors

### 🧠 Memory Error (Ollama)

If you encounter a memory error, like:

```json

CopyEdit

`"error": "model requires more system memory (5.5 GiB) than is available (3.6 GiB)"`
```
#### 🔧 Solution:

Use a lighter model with less memory usage:

```bash

CopyEdit

`ollama pull mistral:instruct-q4_0 ollama run mistral:instruct-q4_0`
```
* * *

**Spring Boot** for building Java-based web applications.
**Ollama** for AI-based services.
**OpenAI** for the inspiration behind this project.
    

* * *
