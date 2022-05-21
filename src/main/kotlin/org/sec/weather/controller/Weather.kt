package org.sec.weather

import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.client.RestTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.beans.factory.annotation.Autowired

@RestController
class WeatherController {

    @Autowired
    val weeklyWeatherCacheService: WeeklyWeatherCacheService? = null

    @RequestMapping("/weather")
    fun weather(@RequestParam("locality") locality: String): WeeklyWeather? {
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
