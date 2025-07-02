package net.engineeringdigest.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.AppCache.AppCache;
import net.engineeringdigest.journalApp.weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {


    @Value("${web.key}")
    private String API_KEY;
    private static String API;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    String request = "{\n" +
            "    \"userName\":\"Ganshyam\",\n" +
            "    \"password\":\"ganshyam\"\n" +
            "}";

    HttpHeaders httpHeaders = new HttpHeaders();

    //can also send header in httpEntity
    HttpEntity<String> requestEntity = new HttpEntity<>(request,httpHeaders);

    public Weather getWeather(){
      API =  appCache.cache.get("Weather_API");
      String final_api = API.replace("API_KEY", API_KEY).replace("CITY", "Mumbai");
       ResponseEntity<Weather> responseEntity = restTemplate.exchange(final_api, HttpMethod.GET, null, Weather.class);
       Weather weatherResponse= responseEntity.getBody();
       return weatherResponse;
    }
}
