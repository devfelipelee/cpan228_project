# Luxury Brand Management System

# Overview

A Spring Boot application for managing luxury fashion items with:

User authentication (Admin/Employee/User roles)

Distribution center integration

Item inventory management

# Projects

Brands (Main App)

Port: 8080

Manages item inventory and user interfaces

Requires Distribution Center service

Distribution Center (Microservice)

Port: 8081 (dev) / 8082 (qa)

REST API for distribution center operations

Basic authentication

# Key Features

Admin panel with distribution center management

Item request system between warehouse and distribution centers

Role-based access control

H2 database with web console

Spring profiles for dev/qa environments

# Setup

Start Distribution Center first:

cd distribution-center

mvn spring-boot:run -Dspring-boot.run.profiles=dev

Start Brands application:

cd brands

mvn spring-boot:run

# Access

Brands: http://localhost:8080

Distribution Center API: http://localhost:8081/api

H2 Consoles:

Brands: http://localhost:8080/h2-console

Distribution Center: http://localhost:8081/h2-console

# Credentials

Brands App:

Admin: admin1/adminpass

Employee: employee1/employeepass

User: user1/userpass

Distribution Center:

Dev: admin/adminpass

QA: qaadmin/qaadminpass
