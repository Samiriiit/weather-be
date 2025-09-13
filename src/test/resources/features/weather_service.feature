

Feature: Weather API

  Scenario: Get weather forecast for a valid city
    Given the city name is "London"
    When I request the weather forecast
    Then the response should contain a valid forecast

  Scenario: Get forecast for New York
    Given the city name is "New York"
    When I request the weather forecast
    Then the response should contain a valid forecast

  Scenario: Get forecast with empty city name
    Given the city name is ""
    When I request the weather forecast
    Then an InvalidInputException should be thrown

  Scenario: Get forecast in Fahrenheit
    Given the city name is "London"
    And the preferred unit is "Fahrenheit"
    When I request the weather forecast
    Then the response temperature should be in "Fahrenheit"

  Scenario: Get forecast for multiple days
    Given the city name is "London"
    And I request a forecast for 3 days
    When I request the weather forecast
    Then the response should contain weather data for 3 days

