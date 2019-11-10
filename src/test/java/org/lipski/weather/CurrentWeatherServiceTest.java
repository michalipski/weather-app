package org.lipski.weather;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lipski.weather.model.CurrentWeather;
import org.lipski.weather.service.CurrentWeatherService;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.lipski.weather.TestConstants.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrentWeatherServiceTest {

    private CurrentWeatherService currentWeatherService;

    @Mock
    private CloseableHttpResponse closeableHttpResponse;

    @Mock
    private CloseableHttpClient closeableHttpClient;

    @BeforeEach
    void setUp() {
        currentWeatherService = new CurrentWeatherService();
        closeableHttpClient = mock(CloseableHttpClient.class);
        closeableHttpResponse = mock(CloseableHttpResponse.class);
        currentWeatherService.setAppKey(TEST_APP_ID);
        currentWeatherService.setUrl(TEST_SERVICE_URL);
        when(closeableHttpResponse.getEntity()).thenReturn(new InputStreamEntity(getClass().getResourceAsStream("/sample.json")));
    }

    @Test
    void testOkResponseAndJsonExtraction() throws IOException, URISyntaxException {
        when(closeableHttpResponse.getStatusLine()).thenReturn(OK_STATUS);
        when(closeableHttpClient.execute(any(HttpGet.class))).thenReturn(closeableHttpResponse);
        currentWeatherService.setHttpClient(closeableHttpClient);

        CurrentWeather actual = currentWeatherService.getWeatherForCity(TEST_CITY);

        assertThat(actual).isEqualTo(CURRENT_WEATHER);
    }

    @Test
    void testNotFoundResponse() throws IOException {
        when(closeableHttpResponse.getStatusLine()).thenReturn(NOT_FOUND_STATUS);
        when(closeableHttpClient.execute(any(HttpGet.class))).thenReturn(closeableHttpResponse);
        currentWeatherService.setHttpClient(closeableHttpClient);

        assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> currentWeatherService.getWeatherForCity(TEST_CITY))
                .withMessageContaining("Error when connecting remote service");
    }
}
