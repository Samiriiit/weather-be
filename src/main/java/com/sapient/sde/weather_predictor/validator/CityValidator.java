package com.sapient.sde.weather_predictor.validator;

public class CityValidator {
    public static boolean isValid(String city) {
        // null check
        if (city == null || city.isBlank()) {
            return false;
        }
        // Maximum length check \
        if (city.length() > 50) {
            return false;
        }
        // Allow-list REGEX validation (letters, spaces, hyphens, and apostrophes)
        // Prevents inputs with special characters like <, >, ;, *, "
        String cityRegex = "^[\\p{L} .'-]+$"; // \p{L} matches any kind of letter from any language
        return city.matches(cityRegex);
    }
}
