# bank-account-tdd# Bank Account TDD

A Java project demonstrating **clean code principles** and **test-driven development** using JUnit 5 and Mockito.

This project was originally completed as part of a university software engineering course (COMP305) at the University of San Diego. It has since been rebuilt from scratch as a personal portfolio project to reinforce and showcase core software engineering fundamentals.

## Overview

This project models a simple bank account system as a vehicle for practicing:

- **Clean code** — descriptive naming, single-responsibility methods, meaningful exceptions, and Javadoc documentation
- **Test-driven development** — tests written to define and verify behavior
- **Unit testing** — isolated tests per behavior with clear DisplayName descriptions
- **Mocking** — TransactionLogger is mocked with Mockito to test logging behavior without file I/O

## Running Tests

./gradlew test

Test results are generated at build/reports/tests/test/index.html.

## Dependencies

- **JUnit Jupiter 5.10.2** — test framework
- **JUnit Jupiter Params 5.10.2** — parameterized test support
- **Mockito 5.11.0** — mocking framework for TransactionLogger

## Skills Demonstrated

- Gradle project configuration
- Custom exception design with informative messages
- Interface-based design enabling testability
- Mockito setup with @ExtendWith(MockitoExtension.class)
- Organized test structure using @Nested classes
- @BeforeEach shared state setup
- @DisplayName for readable test output

