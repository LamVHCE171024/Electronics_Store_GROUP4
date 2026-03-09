@echo off
REM ========================================
REM Quick Start Script for TShop Project
REM ========================================

setlocal enabledelayedexpansion

REM Set colors
color 0A

echo ========================================
echo    TShop Quick Start - Automated Setup
echo ========================================
echo.

REM 1. SET ENVIRONMENT VARIABLES
echo [1/6] Setting up environment variables...
set JAVA_HOME=C:\Program Files\Java\jdk-13
set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 10.0
set CATALINA_HOME=%TOMCAT_HOME%
set MAVEN_HOME=C:\apache-maven-3.9.13
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%TOMCAT_HOME%\bin;%PATH%

echo   - JAVA_HOME: %JAVA_HOME%
echo   - TOMCAT_HOME: %TOMCAT_HOME%
echo   - CATALINA_HOME: %CATALINA_HOME%
echo   - MAVEN_HOME: %MAVEN_HOME%
echo.

REM 2. SETUP DATABASE - CREATE SCHEMA
echo [2/6] Creating database and schema...
sqlcmd -S localhost -U sa -P 0606 -i TShop.sql
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to create database schema
    pause
    exit /b 1
)
echo   - Database schema created successfully
echo.

REM 3. SETUP DATABASE - INSERT SAMPLE DATA
echo [3/6] Inserting sample data...
sqlcmd -S localhost -U sa -P 0606 -i InsertDTB.sql
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to insert sample data
    pause
    exit /b 1
)
echo   - Sample data inserted successfully
echo.

REM 4. BUILD PROJECT WITH MAVEN
echo [4/6] Building project with Maven...
echo   (This may take a few minutes on first build)
call mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Maven build failed
    pause
    exit /b 1
)
echo   - Build completed successfully
echo.

REM 5. DEPLOY TO TOMCAT
echo [5/6] Deploying to Tomcat...
if not exist "%TOMCAT_HOME%\webapps\" (
    echo [ERROR] Tomcat not found at %TOMCAT_HOME%
    echo Please update TOMCAT_HOME variable in this script
    pause
    exit /b 1
)

REM Stop Tomcat if running
taskkill /F /IM java.exe >nul 2>&1

REM Clean old deployment
del /q "%TOMCAT_HOME%\webapps\ROOT.war" >nul 2>&1
rmdir /s /q "%TOMCAT_HOME%\webapps\ROOT" >nul 2>&1
del /q "%TOMCAT_HOME%\webapps\TMobile-1.0-SNAPSHOT.war" >nul 2>&1
rmdir /s /q "%TOMCAT_HOME%\webapps\TMobile-1.0-SNAPSHOT" >nul 2>&1

REM Copy new war file with ROOT name
copy target\TMobile-1.0-SNAPSHOT.war "%TOMCAT_HOME%\webapps\ROOT.war" >nul
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to deploy WAR file to Tomcat
    pause
    exit /b 1
)
echo   - WAR file deployed to Tomcat
echo.

REM 6. START TOMCAT
echo [6/6] Starting Tomcat server...
call "%TOMCAT_HOME%\bin\startup.bat"

echo.
echo ========================================
echo    Setup Complete!
echo ========================================
echo.
echo Application will be available at:
echo   http://localhost:8080/
echo.
echo Tomcat is starting in background...
echo Please wait 10-15 seconds for full startup
echo.

timeout /t 5 /nobreak

REM Open browser (optional - uncomment to auto-open)
REM start http://localhost:8080/TMobile-1.0-SNAPSHOT/

echo Done!
pause
