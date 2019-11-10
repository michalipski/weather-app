package org.lipski.weather.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentWeather {

    private String city;
    private String description;
    private Integer tempInCelsius;
    private Integer tempInFahrenheit;
    private String sunrise;
    private String sunset;

}
