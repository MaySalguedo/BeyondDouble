# BeyondDouble: Arbitrary-Precision Arithmetic based on Taylor Series
BeyondDouble is a Java library that provides arbitrary-precision decimal arithmetic operations using 
Taylor series approximations for complex mathematical functions. This implementation allows for 
mathematical computations beyond the limitations of primitive Java types, with configurable 
precision and multiple notation systems.

## Features

- **Arbitrary Precision:** Handle real numbers of virtually unlimited size (memory permitting)
- **Multiple Notation Systems:** Support for decimal point (1,234.56) and comma (1.234,56) formats
- **Comprehensive Arithmetic Operations:** Addition, subtraction, multiplication, division with precision control
- **Taylor Series Implementation:** Advanced mathematical functions using Taylor series expansions
- **Type Conversion:** Full implementation of Java Number primitive conversions

## Mathematical Foundation

BeyondDouble utilizes Taylor series expansions to compute complex mathematical operations. 
The Taylor series of a function f(x) that is infinitely differentiable at a point a is given by:

f(x) = f(a) + f'(a)(x-a)/1! + f''(a)(x-a)²/2! + f'''(a)(x-a)³/3! + ... + fⁿ(a)(x-a)ⁿ/n! + ...

## Core Components

```bash

// Various construction methods
Digit a = new Digit("123.456");          // From string
Digit b = new Digit(123.456);            // From double
Digit c = new Digit("123", "456", true); // From parts with notation

// Arithmetic operations
Digit result = a.add(b).multiply(c).divide(new Digit("2"), 64); // 64 decimal places precision

```

## Installation

### Prerequisites

- Java 17 or higher

- Maven 3.6+ (for dependency management)

### Building from Source

```bash

git clone https://github.com/MaySalguedo/BeyondDouble.git
cd BeyondDouble
mvn clean package

```

### Testing

```bash

mvn test & mvn surefire-report:report

#Report folder located at .\target\reports\surefire.html

```