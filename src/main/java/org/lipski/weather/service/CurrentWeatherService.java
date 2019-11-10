package org.lipski.weather.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.lipski.weather.model.CurrentWeather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
@Data
public class CurrentWeatherService {

    @Value("${app.url}")
    String url;

    @Value("${app.id}")
    private String appKey;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public CurrentWeather getWeatherForCity(String cityName) throws IOException, URISyntaxException {
        try (CloseableHttpResponse response = httpClient.execute(prepareGetRequest(cityName))) {
            if (Objects.equals(response.getStatusLine().getStatusCode(), HttpStatus.OK.value())) {
                return WeatherJsonExtractor.extract(EntityUtils.toString(response.getEntity()));
            } else {
                throw new ResponseStatusException(HttpStatus.valueOf(response.getStatusLine().getStatusCode()),"Error when connecting remote service");
            }
        }
    }

    private HttpGet prepareGetRequest(String cityName) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("q", cityName);
        uriBuilder.addParameter("APPID", appKey);
        return new HttpGet(uriBuilder.build());
    }
}
