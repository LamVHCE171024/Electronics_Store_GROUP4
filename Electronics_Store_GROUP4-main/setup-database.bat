@echo off
REM ========================================
REM Setup Database Only
REM ========================================

setlocal enabledelayedexpansion
color 0A

echo ========================================
echo    TShop - Database Setup
echo ========================================
echo.

echo [1/2] Creating database and schema...
sqlcmd -S localhost -U sa -P 0606 -i TShop.sql
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to create database
    pause
    exit /b 1
)
echo   - Schema created successfully

echo.
echo [2/2] Inserting sample data...
sqlcmd -S localhost -U sa -P 0606 -i InsertDTB.sql
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to insert data
    pause
    exit /b 1
)
echo   - Data inserted successfully

echo.
echo ========================================
echo   Database Setup Complete!
echo ========================================
echo.

pause
