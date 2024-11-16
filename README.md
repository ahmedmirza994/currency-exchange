[![Build and Test](https://github.com/ahmedmirza994/currency-exchange/actions/workflows/build.yml/badge.svg)](https://github.com/ahmedmirza994/currency-exchange/actions/workflows/build.yml)

# Currency Exchange Application

This repository contains a Java-based application for real-time currency exchange rate conversions. The application leverages the [ExchangeRate API](https://www.exchangerate-api.com) for accurate and up-to-date exchange rates.

### Features
- Real-time currency conversation using the ExchangeRate API.
- Clean architecture, enabling easy extension and integration.
- Automated builds, tests, and code coverage reports.

### Prerequisites
- Java 17 or higher
- Environment Configuration: Set up the API key for ExchangeRate API in a file named application.env in the root directory:`EXCHANGE_RATE_API_KEY=YOUR_API_KEY`
- Internet Connection: The application requires an active internet connection to fetch exchange rates.

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/currency-exchange-service.git
    cd currency-exchange-service
    ```

2. Build the project:
    ```sh
    ./gradlew build
    ```

## Running the Application

To run the application, use the following command:
```sh
./gradlew bootRun
```

## Running Tests

To run the tests, use the following command:
```sh
./gradlew test
```

## Generating Code Coverage Report
To generate test coverage reports, use the following command:
```sh
./gradlew test jacocoTestReport
```

The code coverage report will be generated in the following directory: `build/reports/jacoco/test/html/index.html`

### API Endpoint
The application exposes a REST API endpoint for currency conversion. The endpoint is accessible at: `http://localhost:8080/api/v1/currency-exchange`
Method: `POST`

Request Body:
```json
{
  "user": {
    "userType": "EMPLOYEE",
    "tenure": 4
  },
  "items": [
    {
      "name": "iPhone 15 Pro",
      "price": 1250.0,
      "quantity": 1,
      "category": "ELECTRONICS"
    },
    {
      "name": "Eggs",
      "price": 25,
      "quantity": 12,
      "category": "GROCERY"
    }
  ],
  "originalCurrency": "USD",
  "targetCurrency": "PKR"
}
```

Response:
```json
{
  "data": {
    "payableAmount": 100.0,
    "discount": 0.0,
    "totalAmount": 100.0,
    "exchangeRate": 1.0,
    "originalCurrency": "EUR",
    "targetCurrency": "USD"
  },
  "success": true,
  "errorMessage": null
}
```