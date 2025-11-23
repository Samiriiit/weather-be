package com.sapient.sde.weather_predictor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sapient.sde.weather_predictor.utils.CryptoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherFetcher implements WeatherFetcher {

    private final RestTemplate restTemplate;

//    @Value("${openweathermap.api.key}")
//    private String apiKey;
    @Value("${OPENWEATHER_API_KEY_ENC}")
    private String encryptedApiKey;

    @Value("${CRYPTO_KEY}")
    private String cryptoKey;

    public OpenWeatherFetcher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public JsonNode fetchWeather(String city) {
        String apiKey = CryptoUtils.decrypt(encryptedApiKey, cryptoKey);
//        System.out.println("api key" + apiKey);
        String url = "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={apiKey}&cnt=33";
        return restTemplate.getForObject(url, JsonNode.class, city, apiKey);
    }
}
