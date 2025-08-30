package com.sapient.sde.weather_predictor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sapient.sde.weather_predictor.dto.*;
import com.sapient.sde.weather_predictor.exceptions.CityNotAvailableException;
import com.sapient.sde.weather_predictor.exceptions.ServiceNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.time.Duration;
import java.util.*;

@Service
public class WeatherPredictorServiceImpl implements WeatherPredictorService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    // Package-private setter for testing
    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    private static final String API_URL =
            "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=33";

    private RestTemplate restTemplate = new RestTemplate();
    private static final String CACHE_PREFIX = "weather::";

    @Autowired
    private RedisTemplate<String, WeatherResponseDto> redisTemplate;

    void setRestTemplate(RestTemplate restTemplate) { this.restTemplate = restTemplate; }

    private WeatherResponseDto getFromCache(String city) {
        ValueOperations<String, WeatherResponseDto> ops = redisTemplate.opsForValue();
        return ops.get(CACHE_PREFIX + city.toLowerCase());
    }

    private void saveToCache(String city, WeatherResponseDto response) {
        System.out.println(response);
        ValueOperations<String, WeatherResponseDto> ops = redisTemplate.opsForValue();
        ops.set(CACHE_PREFIX + city.toLowerCase(), response);
    }
    @Override
    public WeatherResponseDto getWeatherForecast(String city, boolean offlineMode) {
        try {
            if(offlineMode){
                WeatherResponseDto cached = getFromCache(city);
                if (cached != null) {
                    return cached;
                }
            }
            JsonNode root = fetchWeatherData(city);
            CityDto cityDto = extractCityInfo(root);
            Map<String, List<JsonNode>> forecastsByDate = groupForecastsByDate(root);
            List<DayForecastDto> dailyForecasts = buildDailyForecasts(forecastsByDate);

            WeatherResponseDto responseDto = new WeatherResponseDto();
            responseDto.setCity(cityDto);
            responseDto.setDayForecastList(dailyForecasts);
            saveToCache(city, responseDto);
            return responseDto;
        }catch (ServiceNotAvailableException e){
            WeatherResponseDto cached = getFromCache(city);
            if (cached != null) {
                return cached;
            }
            throw e;
        }
    }

    private JsonNode fetchWeatherData(String city) {
        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(API_URL, JsonNode.class, city, apiKey);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CityNotAvailableException("No city found with name: " + city);
            }
            throw e;
        } catch (HttpServerErrorException e) {
            throw new ServiceNotAvailableException("Weather API is unavailable");
        }
    }

    private CityDto extractCityInfo(JsonNode root) {
        JsonNode cityNode = root.path("city");
        CityDto cityDto = new CityDto();
        cityDto.setName(cityNode.path("name").asText());
        cityDto.setCountry(cityNode.path("country").asText());
        cityDto.setTimezone(cityNode.path("timezone").asInt());
        return cityDto;
    }

    private Map<String, List<JsonNode>> groupForecastsByDate(JsonNode root) {
        Map<String, List<JsonNode>> forecastsByDate = new LinkedHashMap<>();
        for (JsonNode item : root.path("list")) {
            String date = item.path("dt_txt").asText().split(" ")[0];
            forecastsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(item);
        }
        return forecastsByDate;
    }

    private List<DayForecastDto> buildDailyForecasts(Map<String, List<JsonNode>> forecastsByDate) {
        List<DayForecastDto> result = new ArrayList<>();

        for (Map.Entry<String, List<JsonNode>> entry : forecastsByDate.entrySet()) {
            String date = entry.getKey();
            List<JsonNode> items = entry.getValue();

            WeatherForecastDto summary = aggregateForecasts(items);
            summary.setDate(date);

            DayForecastDto day = new DayForecastDto();
           // day.setDate(date);
            day.setAdvice(generateAdvice(summary));
            day.setWeatherForecastList(Collections.singletonList(summary));

            result.add(day);
        }

        return result;
    }

    private WeatherForecastDto aggregateForecasts(List<JsonNode> forecasts) {
        int count = forecasts.size();
        double tempHigh = Double.NEGATIVE_INFINITY, tempLow = Double.POSITIVE_INFINITY;
        double sumTemp = 0, sumFeels = 0, sumHumidity = 0, sumPressure = 0;
        double sumWind = 0, sumCloud = 0, sumPop = 0;
        double totalVisibility = 0, totalRain = 0;
        int rainCount = 0, visCount = 0;
        String icon = "", condition = "";

        for (JsonNode f : forecasts) {
            JsonNode main = f.path("main");
            double max = main.path("temp_max").asDouble() - 273.15;
            double min = main.path("temp_min").asDouble() - 273.15;

            tempHigh = Math.max(tempHigh, max);
            tempLow = Math.min(tempLow, min);

            sumTemp += main.path("temp").asDouble() - 273.15;
            sumFeels += main.path("feels_like").asDouble() - 273.15;
            sumHumidity += main.path("humidity").asDouble();
            sumPressure += main.path("pressure").asDouble();

            sumWind += f.path("wind").path("speed").asDouble();
            sumCloud += f.path("clouds").path("all").asDouble();
            sumPop += f.path("pop").asDouble();

            if (f.has("visibility")) {
                totalVisibility += f.path("visibility").asDouble();
                visCount++;
            }

            if (f.has("rain") && f.path("rain").has("3h")) {
                totalRain += f.path("rain").path("3h").asDouble();
                rainCount++;
            }

            if (f.path("weather").isArray() && f.path("weather").size() > 0) {
                JsonNode weather = f.path("weather").get(0);
                icon = weather.path("icon").asText();
                condition = weather.path("main").asText();
            }
        }

        WeatherForecastDto dto = new WeatherForecastDto();
        dto.setTemperatureHigh(tempHigh);
        dto.setTemperatureLow(tempLow);
        dto.setTemperatureCurrent(sumTemp / count);
        dto.setTemperatureFeelsLike(sumFeels / count);
        dto.setHumidity((int) (sumHumidity / count));
        dto.setPressure((int) (sumPressure / count));
        dto.setWindSpeed(sumWind / count);
        dto.setCloudiness(sumCloud / count);
        dto.setPrecipitationProbability((sumPop / count) * 100);
        dto.setVisibility(visCount > 0 ? totalVisibility / visCount : null);
        dto.setRainTotal(rainCount > 0 ? totalRain : null);
        dto.setIcon(icon);
        dto.setWeatherCondition(condition);
        return dto;
    }

    protected Set<String> generateAdvice(WeatherForecastDto forecast) {
        Set<String> advice = new HashSet<>();

        if (forecast.getTemperatureHigh() > 40) advice.add("Use sunscreen lotion");
        if (forecast.getPrecipitationProbability() > 30) advice.add("Carry umbrella");
        if (forecast.getWindSpeed() > 10) advice.add("It's too windy, watch out!");
        if ("Thunderstorm".equalsIgnoreCase(forecast.getWeatherCondition())) advice.add("Don’t step out! A storm is brewing!");

        if (advice.isEmpty()) advice.add("No special advice for the day");

        return advice;
    }
}



