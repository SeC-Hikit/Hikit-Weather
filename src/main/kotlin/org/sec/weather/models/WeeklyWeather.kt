package org.sec.weather

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeeklyWeather(
    @JsonProperty("timezone") val timezone: String,
    @JsonProperty("timezone_offset") val timezone_offset: Int,
    @JsonProperty("daily") val daily: List<DailyWeather>
)