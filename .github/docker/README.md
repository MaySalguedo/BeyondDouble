# BeyondDouble Docker Configuration

This directory contains Docker configuration for building and testing the BeyondDouble Java application in GitHub Actions workflows and local development.

## Files

- **Dockerfile**: Multi-stage Docker build that:
  - Builds and tests the application using Maven
  - Generates JaCoCo code coverage reports (90% minimum coverage required)
  - Produces the final JAR artifact
  - Runs the application in a lightweight JRE container

- **.dockerignore**: Excludes unnecessary files from the build context to improve build speed and image size

## Building the Image

```bash
# Build the image
docker build -f .github/docker/Dockerfile -t beyonddouble:latest .

# Build with specific tag
docker build -f .github/docker/Dockerfile -t beyonddouble:v1.0.0 .
```

## Running the Container

```bash
# Run the application
docker run -p 8080:8080 beyonddouble:latest

# Run with volume mount for coverage reports
docker run -v $(pwd)/coverage:/app/coverage-reports beyonddouble:latest

# Run tests only (override default CMD)
docker run beyonddouble:latest mvn test
```

## GitHub Actions Usage

Use this Dockerfile in your GitHub Actions workflow:

```yaml
- name: Build Docker image
  run: docker build -f .github/docker/Dockerfile -t beyonddouble:${{ github.sha }} .

- name: Run tests in Docker
  run: docker run beyonddouble:${{ github.sha }} mvn test

- name: Extract coverage reports
  run: |
    docker run --name coverage beyonddouble:${{ github.sha }} true
    docker cp coverage:/app/coverage-reports ./
```

## Features

- ✅ Java 17 (eclipse-temurin base image)
- ✅ Maven build system with clean verify
- ✅ JaCoCo code coverage reports
- ✅ Multi-stage build for optimized image size
- ✅ Health check configuration
- ✅ Proper artifact extraction

## Requirements

- Docker 17.05+ (for multi-stage build support)
- Docker Compose (optional, for local testing)

## Code Coverage

The build enforces 90% code coverage on both instruction and line metrics using JaCoCo. Coverage reports are generated in `/app/coverage-reports` inside the container.

## Troubleshooting

- **Build fails with test coverage**: Ensure your code has adequate test coverage (90% minimum)
- **JAR not found**: Check that `pom.xml` is properly configured with the maven-jar-plugin
- **Port already in use**: Use `-p <different-port>:8080` when running the container
