package org.sec.weather

import org.springframework.stereotype.Service
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.RedisCallback
import org.springframework.beans.factory.annotation.Autowired
import com.fasterxml.jackson.databind.ObjectMapper

@Service
class WeeklyWeatherCacheService {
    private val mapper = ObjectMapper()

    @Autowired
    private val redisConfig: RedisConfig? = null

    private fun weeklyWeatherCacheKey(location: String): String = 
        "weeklyWeather:" + location;

    fun getWeeklyWeather(location: String): WeeklyWeather? {
        val rawValue = redisConfig?.redisTemplate()?.opsForValue()?.get(weeklyWeatherCacheKey(location))
        if(rawValue == null) {
            return null
        }
        return mapper.readValue(rawValue, WeeklyWeather::class.java)
    }

    fun setWeeklyWeather(location: String, weeklyWeather: WeeklyWeather) =
        redisConfig?.redisTemplate()?.opsForValue()?.set(weeklyWeatherCacheKey(location), mapper.writeValueAsString(weeklyWeather))

    fun removeWeeklyWeather(location: String) =
        redisConfig?.redisTemplate()?.delete(weeklyWeatherCacheKey(location));

    fun ping() = redisConfig?.redisTemplate()?.execute(RedisCallback<String> { connection ->
        connection.ping()
    })
}