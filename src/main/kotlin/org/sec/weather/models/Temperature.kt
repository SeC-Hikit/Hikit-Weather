package org.sec.weather

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Temperature(
    @JsonProperty("day") val day: Double,
    @JsonProperty("min") val min: Double,
    @JsonProperty("max") val max: Double,
    @JsonProperty("night") val night: Double,
    @JsonProperty("eve") val eve: Double,
    @JsonProperty("morn") val morn: Double
)