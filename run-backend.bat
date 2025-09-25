@echo off
echo Starting Spring Boot Application with H2 Database...
set SPRING_PROFILES_ACTIVE=h2
mvn spring-boot:run
pause
