@echo off
REM ========================================
REM Build Only - Compile Project
REM ========================================

setlocal enabledelayedexpansion
color 0A

echo ========================================
echo      TShop - Build Only
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Java\jdk-13
set MAVEN_HOME=C:\apache-maven-3.9.13
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

echo Building project...
call mvn clean package -DskipTests

if %ERRORLEVEL% equ 0 (
    echo.
    echo [SUCCESS] Build completed!
    echo Output: target\TMobile-1.0-SNAPSHOT.war
) else (
    echo.
    echo [ERROR] Build failed!
)

pause
