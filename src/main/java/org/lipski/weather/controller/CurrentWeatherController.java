package org.lipski.weather.controller;

import org.lipski.weather.model.CurrentWeather;
import org.lipski.weather.service.CurrentWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class CurrentWeatherController {

    private final CurrentWeatherService currentWeatherService;

    @Autowired
    public CurrentWeatherController(CurrentWeatherService currentWeatherService) {
        this.currentWeatherService = currentWeatherService;
    }

    @GetMapping("/weatherCityForm")
    String getWeatherForCity(@RequestParam String cityName, ModelMap model) throws IOException, URISyntaxException {
        CurrentWeather currentWeather = currentWeatherService.getWeatherForCity(cityName);
        model.addAttribute(currentWeather);
        return "weatherResult";
    }
}
