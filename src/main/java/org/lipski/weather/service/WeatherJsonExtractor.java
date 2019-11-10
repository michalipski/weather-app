package org.lipski.weather.service;

import org.json.JSONObject;
import org.lipski.weather.model.CurrentWeather;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

class WeatherJsonExtractor {

    static CurrentWeather extract(String weatherJson) {
        JSONObject jsonObject = new JSONObject(weatherJson);
        Double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        return CurrentWeather.builder()
                .city(jsonObject.getString("name"))
                .description(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"))
                .sunrise(getFormattedTimeFrom(jsonObject.getJSONObject("sys").getInt("sunrise"), jsonObject.getInt("timezone")))
                .sunset(getFormattedTimeFrom(jsonObject.getJSONObject("sys").getInt("sunset"), jsonObject.getInt("timezone")))
                .tempInCelsius(KelvinConversion.CELSIUS.convert(temperature))
                .tempInFahrenheit(KelvinConversion.FAHRENHEIT.convert(temperature))
                .build();
    }

    private static String getFormattedTimeFrom(int timestamp, int zoneOffsetInSeconds) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return dateTimeFormatter.format(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofTotalSeconds(zoneOffsetInSeconds)));
    }
}
