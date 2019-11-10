package org.lipski.weather;

import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicStatusLine;
import org.lipski.weather.model.CurrentWeather;

class TestConstants {
    static CurrentWeather CURRENT_WEATHER = CurrentWeather.builder().city("London").description("Drizzle").tempInCelsius(7).tempInFahrenheit(45).sunrise("07:40 AM").sunset("04:47 PM").build();
    static BasicStatusLine OK_STATUS = new BasicStatusLine(new ProtocolVersion("HTTP",1,1),200,"OK");
    static BasicStatusLine NOT_FOUND_STATUS = new BasicStatusLine(new ProtocolVersion("HTTP",1,1),404,"Not found");
    static String TEST_CITY = "London";
    static String TEST_APP_ID = "test_id";
    static String TEST_SERVICE_URL = "http://test-url.pl";

}
