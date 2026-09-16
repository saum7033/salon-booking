@echo off
setlocal EnableDelayedExpansion

title Microservices - Startup

echo.
echo ==========================================
echo        MICROSERVICES STARTUP
echo ==========================================
echo.

REM Create logs folder
if not exist "%~dp0logs" mkdir "%~dp0logs"

REM ------------------------------------------
REM 1. Start Docker Desktop
REM ------------------------------------------
echo [1/12] Starting Docker Desktop...

start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe"

echo        Waiting for Docker Desktop to start...
timeout /t 10 /nobreak >nul

REM Wait for Docker to be ready
:WAIT_DOCKER
docker info >nul 2>&1
if errorlevel 1 (
    echo        Docker not ready yet, waiting...
    timeout /t 5 /nobreak >nul
    goto WAIT_DOCKER
)

echo        [OK] Docker Desktop is running.
echo.

REM ------------------------------------------
REM 2. Start Docker infrastructure
REM ------------------------------------------
echo [2/12] Starting Docker infrastructure...

docker compose -f "%~dp0docker-compose\default\docker.compose.yml" up -d > "%~dp0logs\docker.log" 2>&1

if errorlevel 1 (
echo        [FAILED] Docker infrastructure could not start.
echo        Check logs\docker.log
exit /b 1
)

echo        [OK] Docker infrastructure started.
echo.

REM ------------------------------------------
REM 2. Eureka Server
REM ------------------------------------------
echo [3/12] Starting Eureka Server...

start "" /b cmd /c "cd /d "%~dp0eureka-server" && mvnw.cmd spring-boot:run > "%~dp0logs\eureka.log" 2>&1"

call :waitForPort 8070 "Eureka Server"

if errorlevel 1 (
echo        [FAILED] Eureka Server did not start.
exit /b 1
)

echo        [OK] Eureka Server is RUNNING on port 8070.
echo.

REM ------------------------------------------
REM 3. User Service
REM ------------------------------------------
echo [4/12] Starting User Service...

start "" /b cmd /c "cd /d "%~dp0user-service" && mvnw.cmd spring-boot:run > "%~dp0logs\user-service.log" 2>&1"

call :waitForPort 8086 "User Service"

if errorlevel 1 (
echo        [FAILED] User Service did not start.
exit /b 1
)

echo        [OK] User Service is RUNNING on port 8086.
echo.

REM ------------------------------------------
REM 4. Salon Service
REM ------------------------------------------
echo [5/12] Starting Salon Service...

start "" /b cmd /c "cd /d "%~dp0salon-service" && mvnw.cmd spring-boot:run > "%~dp0logs\salon-service.log" 2>&1"

call :waitForPort 8081 "Salon Service"

if errorlevel 1 (
echo        [FAILED] Salon Service did not start.
exit /b 1
)

echo        [OK] Salon Service is RUNNING.
echo.

REM ------------------------------------------
REM 5. Category Service
REM ------------------------------------------
echo [6/12] Starting Category Service...

start "" /b cmd /c "cd /d "%~dp0category-service" && mvnw.cmd spring-boot:run > "%~dp0logs\category-service.log" 2>&1"

call :waitForPort 8082 "Category Service"

if errorlevel 1 (
echo        [FAILED] Category Service did not start.
exit /b 1
)

echo        [OK] Category Service is RUNNING on port 8082.
echo.

REM ------------------------------------------
REM 6. Service Offering
REM ------------------------------------------
echo [7/12] Starting Service Offering...

start "" /b cmd /c "cd /d "%~dp0service-offering" && mvnw.cmd spring-boot:run > "%~dp0logs\service-offering.log" 2>&1"

call :waitForPort 8085 "Service Offering"

if errorlevel 1 (
echo        [FAILED] Service Offering did not start.
exit /b 1
)

echo        [OK] Service Offering is RUNNING on port 8085.
echo.

REM ------------------------------------------
REM 7. Booking Service
REM ------------------------------------------
echo [8/12] Starting Booking Service...

start "" /b cmd /c "cd /d "%~dp0booking-service" && mvnw.cmd spring-boot:run > "%~dp0logs\booking-service.log" 2>&1"

call :waitForPort 8083 "Booking Service"

if errorlevel 1 (
echo        [FAILED] Booking Service did not start.
exit /b 1
)

echo        [OK] Booking Service is RUNNING on port 8083.
echo.

REM ------------------------------------------
REM 8. Payment Service
REM ------------------------------------------
echo [9/12] Starting Payment Service...

start "" /b cmd /c "cd /d "%~dp0payment" && mvnw.cmd spring-boot:run > "%~dp0logs\payment.log" 2>&1"

call :waitForPort 8084 "Payment Service"

if errorlevel 1 (
echo        [FAILED] Payment Service did not start.
exit /b 1
)

echo        [OK] Payment Service is RUNNING on port 8084.
echo.

REM ------------------------------------------
REM 9. Review Service
REM ------------------------------------------
echo [10/12] Starting Review Service...

start "" /b cmd /c "cd /d "%~dp0review" && mvnw.cmd spring-boot:run > "%~dp0logs\review.log" 2>&1"

call :waitForPort 8088 "Review Service"

if errorlevel 1 (
echo        [FAILED] Review Service did not start.
exit /b 1
)

echo        [OK] Review Service is RUNNING.
echo.

REM ------------------------------------------
REM 10. Notification Service
REM ------------------------------------------
echo [11/12] Starting Notification Service...

start "" /b cmd /c "cd /d "%~dp0notification" && mvnw.cmd spring-boot:run > "%~dp0logs\notification.log" 2>&1"

call :waitForPort 5007 "Notification Service"

if errorlevel 1 (
echo        [FAILED] Notification Service did not start.
exit /b 1
)

echo        [OK] Notification Service is RUNNING on port 5007.
echo.

REM ------------------------------------------
REM 11. AI Assistant
REM ------------------------------------------
echo [12/12] Starting AI Assistant...

start "" /b cmd /c "cd /d "%~dp0ai-assistant" && mvnw.cmd spring-boot:run > "%~dp0logs\ai-assistant.log" 2>&1"

call :waitForPort 8087 "AI Assistant"

if errorlevel 1 (
echo        [FAILED] AI Assistant did not start.
exit /b 1
)

echo        [OK] AI Assistant is RUNNING on port 8087.
echo.

REM ------------------------------------------
REM Gateway
REM ------------------------------------------
echo.
echo [FINAL] Starting API Gateway...

start "" /b cmd /c "cd /d "%~dp0gateway-server" && mvnw.cmd spring-boot:run > "%~dp0logs\gateway.log" 2>&1"

echo        Waiting for API Gateway...

call :waitForPort 8080 "API Gateway"

if errorlevel 1 (
echo        [FAILED] API Gateway did not start.
exit /b 1
)

echo        [OK] API Gateway is RUNNING.
echo.

echo ==========================================
echo        ALL SERVICES ARE RUNNING
echo ==========================================
echo.
echo Eureka          : http://localhost:8070
echo Gateway         : http://localhost:8080
echo User Service    : http://localhost:8086
echo Category        : http://localhost:8082
echo Booking         : http://localhost:8083
echo Payment         : http://localhost:8084
echo Service Offering: http://localhost:8085
echo AI Assistant    : http://localhost:8087
echo Notification    : http://localhost:5007
echo.
echo Logs are available in:
echo %~dp0logs
echo.
echo ==========================================
echo        STARTUP COMPLETE
echo ==========================================
echo.

pause
exit /b 0

REM ==========================================
REM Function: Wait for a port
REM ==========================================
:waitForPort

set PORT=%1
set SERVICE=%~2
set COUNT=0

:CHECK_PORT

powershell -NoProfile -Command "if (Test-NetConnection -ComputerName localhost -Port %PORT% -InformationLevel Quiet) { exit 0 } else { exit 1 }" >nul 2>&1

if not errorlevel 1 (
exit /b 0
)

set /a COUNT+=1

if %COUNT% GEQ 60 (
exit /b 1
)

timeout /t 2 /nobreak >nul
goto CHECK_PORT
