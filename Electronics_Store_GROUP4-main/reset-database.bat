@echo off
REM ========================================
REM Reset Database (Drop & Recreate)
REM ========================================

setlocal enabledelayedexpansion
color 0C

echo ========================================
echo    TShop - Reset Database
echo ========================================
echo.

echo [WARNING] This will drop and recreate the database!
echo.
set /p confirm="Are you sure? (Y/N): "
if /i not "%confirm%"=="Y" (
    echo Cancelled.
    exit /b 0
)

echo.
echo [1/3] Dropping old database (if exists)...
sqlcmd -S localhost -U sa -P 0606 -Q "DROP DATABASE IF EXISTS TShop" >nul 2>&1
echo   - Done

echo.
echo [2/3] Creating new database and schema...
sqlcmd -S localhost -U sa -P 0606 -i TShop.sql
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to create database
    pause
    exit /b 1
)
echo   - Schema created successfully

echo.
echo [3/3] Inserting fresh sample data...
sqlcmd -S localhost -U sa -P 0606 -i InsertDTB.sql
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to insert data
    pause
    exit /b 1
)
echo   - Data inserted successfully

echo.
echo ========================================
echo   Database Reset Complete!
echo ========================================
echo.

pause
