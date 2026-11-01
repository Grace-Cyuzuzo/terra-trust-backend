# TerraTrust Backend

**Student Name:** Gatoya Grace
**Student ID:** 27898
**Project Name:** TerraTrust

## Project Overview
TerraTrust is a plot management platform built using Spring Boot, Spring Data JPA, and PostgreSQL. This backend provides a complete REST API for managing land offices, land officers, buyers, and plot applications.

## Steps to Run the API
1. **Database Setup:** Create a PostgreSQL database named `terra_trust`.
2. **Configuration:** Open `src/main/resources/application.properties` and update the following lines with your local PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/terra_trust
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_PASSWORD