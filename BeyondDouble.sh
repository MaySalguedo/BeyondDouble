#!/bin/bash

# BeyondDouble.sh - Unified script runner
# Supports multiple modes: run (default), test, jar
# Parameters:
#   -r, --run   : Run the application (default)
#   -t, --test  : Run tests
#   -j, --jar   : Generate JAR and run it
#   -q, --quiet : Suppress "Press ENTER to exit" prompt

set -e

# Default values
mode="run"
quiet=false

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -r|--run)
            mode="run"
            shift
            ;;
        -t|--test)
            mode="test"
            shift
            ;;
        -j|--jar)
            mode="jar"
            shift
            ;;
        -q|--quiet)
            quiet=true
            shift
            ;;
        *)
            echo "[ERROR] Unknown parameter: $1"
            echo "Usage: $0 [-r|--run] [-t|--test] [-j|--jar] [-q|--quiet]"
            exit 1
            ;;
    esac
done

# Function to display header
display_header() {
    local title=$1
    echo "========================================"
    echo "   $title"
    echo "========================================"
    echo ""
}

# Function to calculate and display duration
calculate_duration() {
    local start_time=$1
    local end_time=$2
    local start_datetime=$3
    local end_datetime=$4
    
    # Calculate duration in milliseconds
    duration_ns=$((end_time - start_time))
    duration_ms=$((duration_ns / 1000000))
    
    # Convert milliseconds to HH:MM:SS.MS format
    hours=$((duration_ms / 3600000))
    remaining=$((duration_ms % 3600000))
    minutes=$((remaining / 60000))
    remaining=$((remaining % 60000))
    seconds=$((remaining / 1000))
    milliseconds=$((remaining % 1000))
    
    # Format with leading zeros
    duration_formatted=$(printf "%02d:%02d:%02d.%03d" $hours $minutes $seconds $milliseconds)
    
    echo ""
    echo "========================================"
    echo "Duration: $duration_formatted - [$start_datetime] to [$end_datetime]"
    echo "========================================"
    echo ""
}

# Function to wait for user input (unless quiet mode)
wait_for_exit() {
    if [ "$quiet" = false ]; then
        read -p "Press ENTER to exit..."
    fi
}

# Check if gradlew exists
if [ ! -f "./gradlew" ]; then
    echo "[ERROR] gradlew not found. Please ensure you are in the BeyondDouble project directory."
    exit 1
fi

# Execute based on mode
case $mode in
    run)
        display_header "BeyondDouble Application Runner"
        
        # Record start time
        start_time=$(date +%s%N)
        start_datetime=$(date '+%H:%M:%S.%N' | cut -b1-12)
        
        echo "Runtime started: $start_datetime"
        echo ""
        
        # Run the application using Gradle
        if ./gradlew run --quiet 2>&1; then
            exit_code=0
        else
            exit_code=$?
            echo "[ERROR] Application execution failed with exit code: $exit_code"
        fi
        
        # Record end time
        end_time=$(date +%s%N)
        end_datetime=$(date '+%H:%M:%S.%N' | cut -b1-12)
        
        calculate_duration "$start_time" "$end_time" "$start_datetime" "$end_datetime"
        wait_for_exit
        exit $exit_code
        ;;
    
    test)
        display_header "BeyondDouble Test Runner"
        
        echo "Compiling source and test files..."
        echo ""
        
        # Run the tests using Gradle
        if ./gradlew test --quiet 2>&1; then
            exit_code=0
            echo ""
            echo "========================================"
            echo "All tests passed successfully!"
            echo "========================================"
        else
            exit_code=$?
            echo ""
            echo "========================================"
            echo "[ERROR] Test execution failed with exit code: $exit_code"
            echo "========================================"
        fi
        
        echo ""
        wait_for_exit
        exit $exit_code
        ;;
    
    jar)
        display_header "BeyondDouble JAR Generator"
        
        echo "Building project and generating JAR..."
        echo ""
        
        # Build the project using Gradle
        if ./gradlew build --quiet 2>&1; then
            exit_code=0
        else
            exit_code=$?
            echo "[ERROR] Build failed with exit code: $exit_code"
            wait_for_exit
            exit $exit_code
        fi
        
        echo "Build completed successfully!"
        echo ""
        echo "JAR file generated: build/libs/BeyondDouble-1.0-SNAPSHOT.jar"
        echo ""
        echo "Running JAR file..."
        echo "========================================"
        echo ""
        
        # Run the generated JAR
        if java -jar build/libs/BeyondDouble-1.0-SNAPSHOT.jar 2>&1; then
            exit_code=0
        else
            exit_code=$?
            echo "[ERROR] JAR execution failed with exit code: $exit_code"
        fi
        
        echo ""
        echo "========================================"
        echo "JAR execution completed."
        echo "========================================"
        echo ""
        
        wait_for_exit
        exit $exit_code
        ;;
    
    *)
        echo "[ERROR] Unknown mode: $mode"
        exit 1
        ;;
esac
