package org.sec.weather

import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.client.RestTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.beans.factory.annotation.Autowired

@RestController
class WeatherController {

    @Autowired
    val weeklyWeatherCacheService: WeeklyWeatherCacheService? = null

    @Operation(summary = "Get the weather report for a determinate location", description = "")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "Bad Request", content=[Content(schema = Schema(hidden = true))]),
        ApiResponse(responseCode = "404", description = "Location not found", content=[Content(schema = Schema(hidden = true))]),
        ApiResponse(responseCode = "500", description = "Internal server error", content=[Content(schema = Schema(hidden = true))])
    ])
    @GetMapping("/weather", produces = ["application/json"])
    fun weather(
        @Parameter(description="The locality of weather report", required=true, example="Marano Principato, CS") 
        @RequestParam("locality") locality: String): WeeklyWeather? {
        val oldWeather = weeklyWeatherCacheService?.getWeeklyWeather(locality)
        if (oldWeather != null) {
            return oldWeather
        }

        val response = 
            RestTemplate().getForObject("https://nominatim.openstreetmap.org/search?format=json&limit=1&q=${locality}", Array<Location>::class.java, locality)
        
        val location = response?.get(0)

        if(location == null) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "location not found"
            )
        }

        val weather = RestTemplate().getForObject(
            "https://api.openweathermap.org/data/2.5/onecall?exclude=hourly,minutely,current&lat=${location.lat}&lon=${location.lon}&appid=${System.getenv("OPENWEATHER_APY_KEY")}", 
            WeeklyWeather::class.java,
        )

        if(weather == null) {
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "cannot contact weather service"
            )
        }

        weeklyWeatherCacheService?.setWeeklyWeather(locality, weather)

        return weather
    }

}
