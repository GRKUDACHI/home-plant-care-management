@echo off
echo ========================================
echo Home Care Plants Documentation Generator
echo ========================================
echo.

echo Checking Python installation...
python --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Python is not installed or not in PATH
    echo Please install Python 3.7+ from https://python.org
    pause
    exit /b 1
)

echo Checking required packages...
python -c "import markdown, pdfkit" >nul 2>&1
if %errorlevel% neq 0 (
    echo Installing required packages...
    pip install markdown pdfkit
    if %errorlevel% neq 0 (
        echo ERROR: Failed to install required packages
        pause
        exit /b 1
    )
)

echo.
echo Generating PDF documentation...
python generate_pdf.py

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo SUCCESS: PDF documentation generated!
    echo ========================================
    echo.
    echo Files created:
    echo - COMPLETE_PROJECT_DOCUMENTATION.md
    echo - Home_Care_Plants_Complete_Documentation.pdf
    echo.
    echo Opening PDF...
    start Home_Care_Plants_Complete_Documentation.pdf
) else (
    echo.
    echo ========================================
    echo ERROR: Failed to generate PDF
    echo ========================================
    echo.
    echo Please check the error messages above.
    echo You may need to install wkhtmltopdf:
    echo https://wkhtmltopdf.org/downloads.html
)

echo.
pause
