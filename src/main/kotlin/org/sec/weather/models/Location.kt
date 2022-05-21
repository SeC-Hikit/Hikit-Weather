package org.sec.weather

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(
    @JsonProperty("place_id") val place_id: Int, 
    @JsonProperty("osm_id") val osm_id: Int, 
    @JsonProperty("display_name") val display_name: String, 
    @JsonProperty("lat") val lat: String, 
    @JsonProperty("lon") val lon: String
)