@echo off
REM ========================================
REM Stop Tomcat Server
REM ========================================

setlocal enabledelayedexpansion
color 0C

echo ========================================
echo      Stopping Tomcat...
echo ========================================
echo.

set TOMCAT_HOME=C:\Program Files\Apache Software Foundation\Tomcat 10.0
set CATALINA_HOME=%TOMCAT_HOME%

REM Try graceful shutdown first
if exist "%TOMCAT_HOME%\bin\shutdown.bat" (
    echo [1] Attempting graceful shutdown...
    call "%TOMCAT_HOME%\bin\shutdown.bat"
    timeout /t 5 /nobreak
)

REM Force kill if still running
echo [2] Forcing Java process termination...
taskkill /F /IM java.exe >nul 2>&1

echo.
echo ========================================
echo   Tomcat stopped!
echo ========================================
echo.

pause
