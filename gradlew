#!/bin/bash

# Simple Gradle Wrapper

GRADLE_HOME="$HOME/.gradle/wrapper/dists"
GRADLE_VERSION="8.5"

# Set up environment
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CLASSPATH="$SCRIPT_DIR/gradle/wrapper/gradle-wrapper.jar"

# Download Gradle if needed (handled by wrapper jar)
exec java -Dorg.gradle.appname=gradlew -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
