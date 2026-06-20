@echo off
cls

setlocal enabledelayedexpansion

REM Default parameters
set "mode=run"
set "quiet=0"

REM Parse command-line arguments
:parse_args
if "%~1"=="" goto start_mode
if /i "%~1"=="-run" (set "mode=run" & shift & goto parse_args)
if /i "%~1"=="--run" (set "mode=run" & shift & goto parse_args)
if /i "%~1"=="-test" (set "mode=test" & shift & goto parse_args)
if /i "%~1"=="--test" (set "mode=test" & shift & goto parse_args)
if /i "%~1"=="-jar" (set "mode=jar" & shift & goto parse_args)
if /i "%~1"=="--jar" (set "mode=jar" & shift & goto parse_args)
if /i "%~1"=="--quiet" (set "quiet=1" & shift & goto parse_args)
REM Unknown parameter, skip and continue
shift
goto parse_args

:start_mode
if /i "%mode%"=="run" goto mode_run
if /i "%mode%"=="test" goto mode_test
if /i "%mode%"=="jar" goto mode_jar
goto mode_run

REM ============================================
REM MODE: RUN - Run application with gradlew
REM ============================================
:mode_run
echo.
echo Running BeyondDouble Application...
echo.

call gradlew.bat run
set "exit_code=%errorlevel%"

if %exit_code% neq 0 (
	echo.
	echo [ERROR] Application failed with exit code %exit_code%
	echo.
	if %quiet% equ 0 pause
	exit /b %exit_code%
)

echo.
echo [SUCCESS] Application completed successfully
echo.
if %quiet% equ 0 pause
exit /b 0

REM ============================================
REM MODE: TEST - Run unit tests
REM ============================================
:mode_test
echo.
echo Running BeyondDouble Tests...
echo.

call gradlew.bat test
set "exit_code=%errorlevel%"

if %exit_code% neq 0 (
	echo.
	echo [ERROR] Tests failed with exit code %exit_code%
	echo.
	if %quiet% equ 0 pause
	exit /b %exit_code%
)

echo.
echo [SUCCESS] All tests passed
echo.
if %quiet% equ 0 pause
exit /b 0

REM ============================================
REM MODE: JAR - Build JAR and run it
REM ============================================
:mode_jar
echo.
echo Building BeyondDouble JAR...
echo.

call gradlew.bat build
set "exit_code=%errorlevel%"

if %exit_code% neq 0 (
	echo.
	echo [ERROR] Build failed with exit code %exit_code%
	echo.
	if %quiet% equ 0 pause
	exit /b %exit_code%
)

echo.
echo [SUCCESS] Build completed successfully
echo.
echo Running JAR...
echo.

java -jar BeyondDouble_v0.0.20.jar
set "exit_code=%errorlevel%"

if %exit_code% neq 0 (
	echo.
	echo [ERROR] JAR execution failed with exit code %exit_code%
	echo.
	if %quiet% equ 0 pause
	exit /b %exit_code%
)

echo.
echo [SUCCESS] JAR completed successfully
echo.
if %quiet% equ 0 pause
exit /b 0
