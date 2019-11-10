package org.lipski.weather.service;

public enum KelvinConversion {
    CELSIUS(1.0, 273),
    FAHRENHEIT(1.8, 459);

    private final Double multiplier;
    private final Integer subtractor;

    KelvinConversion(Double multiplier, Integer subtractor) {
        this.multiplier = multiplier;
        this.subtractor = subtractor;
    }

    public int convert(Double temperatureInKelvin) {
        return (int) (temperatureInKelvin * multiplier - subtractor);
    }
}
