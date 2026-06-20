[![Gradle](https://img.shields.io/badge/Build-Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org)
[![Java](https://img.shields.io/badge/Java-17%2B-ED8936?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![License: APACHE](https://img.shields.io/badge/License-APACHE-yellow?style=for-the-badge)](https://www.apache.org/licenses/LICENSE-2.0)
[![JUnit 5](https://img.shields.io/badge/Test-JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)

# BeyondDouble: Arbitrary-Precision Arithmetic based on Taylor Series

BeyondDouble is a Java library that provides arbitrary-precision decimal arithmetic operations using Taylor series approximations for complex mathematical functions. This implementation allows for mathematical computations beyond the limitations of primitive Java types, with configurable precision and multiple notation systems.

## Features

- **Arbitrary Precision:** Handle real numbers of virtually unlimited size (memory permitting)
- **Multiple Notation Systems:** Support for decimal point (1,234.56) and comma (1.234,56) formats
- **Comprehensive Arithmetic Operations:** Addition, subtraction, multiplication, division with precision control
- **Taylor Series Implementation:** Advanced mathematical functions using Taylor series expansions
- **Type Conversion:** Full implementation of Java Number primitive conversions
- **JUnit 5 Testing:** Comprehensive test suite with 90% code coverage using JaCoCo
- **Docker Support:** Pre-configured Docker and Docker Compose for containerized deployment

## Mathematical Foundation

BeyondDouble utilizes Taylor series expansions to compute complex mathematical operations. The Taylor series of a function f(x) that is infinitely differentiable at a point a is given by:

```
f(x) = f(a) + f'(a)(x-a)/1! + f''(a)(x-a)²/2! + f'''(a)(x-a)³/3! + ... + fⁿ(a)(x-a)ⁿ/n! + ...
```

## Core Components

```java
// Various construction methods
Digit a = new Digit("123.456");          // From string
Digit b = new Digit(123.456);            // From double
Digit c = new Digit("123", "456", true); // From parts with notation

// Arithmetic operations
Digit result = a.add(b).multiply(c).divide(new Digit("2"), 64); // 64 decimal places precision
```

## Requirements

- **Java 17 or higher** - Minimum version required to run the application
- **Gradle 8.0+** - Build tool (included via Gradle Wrapper)
- **Docker & Docker Compose** (optional) - For containerized execution

## Installation & Setup

### Prerequisites

- Java 17+
- Gradle 8.0+ (or use the included Gradle Wrapper)

### Building from Source

```bash
# Clone the repository
git clone https://github.com/MaySalguedo/BeyondDouble.git
cd BeyondDouble

# Build the project with Gradle
./gradlew build

# Build on Windows
gradlew.bat build
```

### Running Tests

```bash
# Run all tests with JUnit 5
./gradlew test

# Run tests on Windows
gradlew.bat test

# Run with verbose output
./gradlew test --info
```

### Generating Coverage Reports

BeyondDouble enforces a minimum of **90% code coverage** using JaCoCo.

```bash
# Generate JaCoCo coverage report
./gradlew jacocoTestReport

# On Windows
gradlew.bat jacocoTestReport

# View the HTML coverage report in your browser
# Open: build/reports/jacoco/test/html/index.html
```

To verify coverage meets the minimum threshold:

```bash
# Run coverage verification (fails if coverage < 90%)
./gradlew jacocoTestCoverageVerification

# On Windows
gradlew.bat jacocoTestCoverageVerification
```

### Building the JAR Artifact

```bash
# Build the JAR file (skipping tests)
./gradlew jar

# JAR location: build/libs/BeyondDouble-1.0-SNAPSHOT.jar

# Or build with tests included
./gradlew build
```

### Running the Application

```bash
# Run the application directly
./gradlew run

# On Windows
gradlew.bat run

# Run a built JAR
java -jar build/libs/BeyondDouble-1.0-SNAPSHOT.jar
```

## Docker Support

### Building with Docker

```bash
# Build a Docker image
docker build -t beyonddouble:latest -f .github/docker/Dockerfile .

# Run the container
docker run --name beyonddouble beyonddouble:latest

# View logs
docker logs beyonddouble

# Remove the container after it exits
docker rm beyonddouble
```

### Using Docker Compose

`docker-compose.yml` currently points to the legacy root `Dockerfile`. Update it to use `.github/docker/Dockerfile` before relying on Compose for local runs.

```bash
# Start the application with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop the application
docker-compose down
```

The CI Docker image is defined in `.github/docker/Dockerfile` and uses Gradle inside the container.

## Build System Details

### Gradle Configuration

The project uses Gradle with the following key plugins:

- **Java Plugin** - Standard Java compilation and testing
- **Application Plugin** - For running the application directly
- **JaCoCo Plugin** - Code coverage measurement
- **Checkstyle Plugin** - Java source linting

Main application class: `BeyondDouble.App`

### Build Task Dependencies

```
build
├── compile
├── test
├── verify (code coverage check - 90% minimum)
└── jar (assembly)
```

### Key Gradle Commands

| Command | Description |
|---------|-------------|
| `./gradlew build` | Clean build with tests and coverage verification |
| `./gradlew clean` | Clean build artifacts |
| `./gradlew checkstyleMain` | Lint Java source files |
| `./gradlew test` | Run unit tests |
| `./gradlew jacocoTestReport` | Generate coverage report |
| `./gradlew run` | Run the application |
| `./gradlew jar` | Build JAR file |
| `./gradlew --help` | Show all available tasks |

## Testing

### JUnit 5 Test Suite

The project uses JUnit 5 (Jupiter) for testing with comprehensive test coverage:

- **DigitTest.java** - Core arithmetic operations
- **TrigonometryTest.java** - Taylor series implementations
- **AppTest.java** - Application entry point

Run tests with:

```bash
./gradlew test

# Run specific test class
./gradlew test --tests DigitTest

# Run with coverage report
./gradlew build
```

Coverage reports are located at: `build/reports/jacoco/test/html/index.html`

## GitHub Actions CI/CD

The project includes a GitHub Actions workflow at `.github/workflows/ci.yml`.

All jobs run inside Docker containers:

- **Linting** - Java-only Checkstyle linting with `./gradlew checkstyleMain`
- **Testing** - JUnit 5 tests with JaCoCo reports
- **Building** - Gradle package build and JAR artifact upload
- **Docker** - Docker image build and runtime smoke test

Branch behavior:

| Branch/Event | Jobs |
|--------------|------|
| Any branch | `lint` |
| `main` or `develop` | `lint`, `test` |
| `main` | `lint`, `test`, `build`, `docker` |
| Pull request into `develop` | `lint`, `test` |
| Pull request into `main` | `lint`, `test`, `build`, `docker` |

### Local CI/CD Testing with act

You can test GitHub Actions workflows locally using [nektos/act](https://github.com/nektos/act).

#### Prerequisites
- Install [Docker](https://docs.docker.com/get-docker/)
- Install [act](https://github.com/nektos/act#installation)

#### Usage

```bash
# Feature branch push: lint only
act push -e .github/events/push-feature.json

# Develop branch push: lint and test
act push -e .github/events/push-develop.json

# Main branch push: lint, test, build, and docker
act push -e .github/events/push-main.json

# Pull request into develop: lint and test
act pull_request -e .github/events/pull-request-develop.json

# Pull request into main: lint, test, build, and docker
act pull_request -e .github/events/pull-request-main.json

# Run with verbose output
act -v

# List available jobs
act -l
```

The local event payloads are stored in `.github/events/`.

## Dependencies

### Build-time

- **Gradle 8.0+** (included via Gradle Wrapper)
- **JDK 17+** (Java Development Kit)

### Runtime

- **JDK 17+**

### Test Dependencies

- **JUnit 5 (5.11.0)** - JUnit Jupiter API, Engine, and Parameterized Tests
- **JaCoCo 0.8.11** - Code coverage measurement

No external production dependencies - BeyondDouble is a lightweight library with minimal dependencies.

## License

BeyondDouble is licensed under the **Apache License 2.0**. See [LICENSE.md](LICENSE.md) for details.

## Contributing

Contributions are welcome! Please ensure:

1. Code passes all tests: `./gradlew test`
2. Coverage meets 90% minimum: `./gradlew jacocoTestCoverageVerification`
3. Java lint passes: `./gradlew checkstyleMain`
4. Code is properly documented
5. Commit messages are clear and descriptive

## Support & Documentation

For issues, feature requests, or documentation improvements, please open an issue on GitHub.

---

**Last Updated:** June 2026
**Build System:** Gradle 8.0+
**Java Version:** 17
