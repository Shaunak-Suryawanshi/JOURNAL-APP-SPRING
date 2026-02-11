package com.shaunak.journal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {

    private Current current;

    @Getter
    @Setter
    public class Current {

        private int temprature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescription;

        private int feelslike;
    }
}
