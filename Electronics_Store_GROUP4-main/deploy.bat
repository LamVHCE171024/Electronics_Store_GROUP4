@echo off
REM ========================================
REM Deploy and Start Tomcat
REM ========================================

setlocal enabledelayedexpansion
color 0A

echo ========================================
echo   Deploy to Tomcat & Start Server
echo ========================================
echo.

set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 10.0
set CATALINA_HOME=%TOMCAT_HOME%

REM Check if WAR file exists
if not exist "target\TMobile-1.0-SNAPSHOT.war" (
    echo [ERROR] WAR file not found: target\TMobile-1.0-SNAPSHOT.war
    echo Please run 'build.bat' first
    pause
    exit /b 1
)

REM Check if Tomcat exists
if not exist "%TOMCAT_HOME%\webapps\" (
    echo [ERROR] Tomcat not found at %TOMCAT_HOME%
    echo Please update TOMCAT_HOME variable in this script
    pause
    exit /b 1
)

echo [1/3] Stopping old Tomcat instances...
taskkill /F /IM java.exe >nul 2>&1
echo   - Done

echo.
echo [2/3] Deploying application...
REM Clean old deployment
del /q "%TOMCAT_HOME%\webapps\ROOT.war" >nul 2>&1
rmdir /s /q "%TOMCAT_HOME%\webapps\ROOT" >nul 2>&1
del /q "%TOMCAT_HOME%\webapps\TMobile-1.0-SNAPSHOT.war" >nul 2>&1
rmdir /s /q "%TOMCAT_HOME%\webapps\TMobile-1.0-SNAPSHOT" >nul 2>&1

REM Copy new war file with ROOT name
copy target\TMobile-1.0-SNAPSHOT.war "%TOMCAT_HOME%\webapps\ROOT.war" >nul
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to deploy WAR file
    pause
    exit /b 1
)
echo   - WAR deployed successfully

echo.
echo [3/3] Starting Tomcat...
call "%TOMCAT_HOME%\bin\startup.bat"

echo.
echo ========================================
echo   Deployment Complete!
echo ========================================
echo.
echo Application address:
echo   http://localhost:8080/
echo.
echo Please wait 10-15 seconds for server startup...
echo.

pause
