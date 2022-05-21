package org.sec.weather

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class DailyWeather(
    @JsonProperty("dt") val dt: Int, 
    @JsonProperty("sunrise") val sunrise: Int,
    @JsonProperty("sunset") val sunset: Int,
    @JsonProperty("temp") val temp: Temperature,
    @JsonProperty("moonrise") val moonrise: Int,
    @JsonProperty("moonset") val moonset: Int,
    @JsonProperty("moon_phase") val moon_phase: Double,
)