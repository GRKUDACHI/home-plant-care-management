@echo off
echo Testing Database Persistence
echo ============================

echo.
echo Step 1: Starting the application...
echo This will create the H2 database file in the data/ directory
echo.

cd /d "%~dp0"
call mvn spring-boot:run

echo.
echo Application stopped. Check the data/ directory for database files.
echo You should see files like: plantcare_db.mv.db and plantcare_db.trace.db
echo.
pause
