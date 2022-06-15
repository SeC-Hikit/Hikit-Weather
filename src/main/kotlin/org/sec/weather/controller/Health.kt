package org.sec.weather

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.beans.factory.annotation.Autowired
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse

@RestController
class HealthController {

    @Autowired
    val weeklyWeatherCacheService: WeeklyWeatherCacheService? = null

    @Operation(summary = "Get the service status", description = "")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Internal server error")
    ])
    @GetMapping("/health")
    fun health(): String {
        weeklyWeatherCacheService?.ping()!!
        return "OK"
    }

}