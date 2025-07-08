
# Ambient Test Automation Project

## Overview

This project contains automated test suites for the **Ambient** application, covering both **API** and **UI** layers.  
The automation framework leverages **Selenium WebDriver** for UI testing and **RestAssured** for API testing. 
Test execution is managed via **TestNG**.

---

## Project Structure

```
com.ambient
│
├── api
│   ├── client        # REST API client and services
│   ├── models        # API response/request models
│   └── services      # Service classes calling API endpoints
│
├── ui
│   ├── pages         # Page Object Model classes for UI tests
│   └── tests         # UI TestNG test classes
│
├── tests
│   ├── api           # API TestNG test classes
│   └── ui            # UI TestNG test classes
│
└── utils
    └── config       # Configuration utilities for base URLs, timeouts, etc.
```

---

## Key Technologies

- **Java 17+**
- **Selenium WebDriver** — UI automation
- **RestAssured** — API testing
- **TestNG** — Test runner & suite management
- **Lombok** — Boilerplate reduction (e.g., @NonNull, @Slf4j)
- **Allure** — Test reporting
- **AssertJ** — Fluent assertions in tests
- **Log4j/SLF4J** — Logging

---

## Usage

### Prerequisites

- Java JDK 17 or higher
- Maven (for dependencies and build)
- ChromeDriver or appropriate WebDriver for UI tests

### Running Tests

To run all tests (API + UI):

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

Or use the TestNG XML suite directly:

```bash
testng -s testng.xml
```

### TestNG Suite Configuration

Located in `src/test/resources/testng.xml`:

```xml
<suite name="Ambient Test Suite">
    <test name="Ambient Tests">
        <classes>
            <class name="com.ambient.tests.api.AmbientAPITests"/>
            <class name="com.ambient.tests.ui.AmbientUITests"/>
        </classes>
    </test>
</suite>
```

---

## Framework Highlights

### API Client

- `RestCall` (abstract base) sets up RestAssured with logging and Allure reporting.
- `AppointmentService` handles API calls related to appointments.
- `Services` class manages instances of service classes (singleton style).

### UI Framework

- BasePage class provides common Selenium WebDriver utilities:
  - Element visibility waits, clicking, typing, JavaScript interactions.
- Page classes use Selenium's `@FindBy` with PageFactory for element management.
- Example page: `AppointmentConfirmationPage` with validation and navigation methods.
- Fluent API style navigation with methods returning page objects.

---

## Example Usage

### API Test Example

```java
Appointments appointments = Services.api().getAppointmentService().getQuery();
assertNotNull(appointments);
```

### UI Test Example

```java
AmbientLandingPage landingPage = new AmbientLandingPage(driver);
NewAppointmentPage newAppointmentPage = landingPage.clickOnNewButton();
AppointmentConfirmationPage confirmationPage = newAppointmentPage.bookAppointment(...);
confirmationPage.validateAppointmentConfirmed("John Doe");
```

---

## Contributing

Contributions are welcome! Please submit pull requests with clear descriptions of your changes.

---

## License

Specify your license here (e.g., MIT License).

---

## Contact

For questions or support, contact the project maintainer.
