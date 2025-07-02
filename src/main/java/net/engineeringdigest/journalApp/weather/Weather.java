package net.engineeringdigest.journalApp.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class Weather {
    private Current current;
    @Setter
    @Getter
    public class Current {
        @JsonProperty("observation_time")
        private  String observationTime;
        private int temperature;
        private int weather_code;
        private int wind_speed;
        private int wind_degree;
        private String wind_dir;
        private int pressure;
        private int precip;
        private int humidity;
        private int cloudcover;
        private int feelslike;

    }
}
