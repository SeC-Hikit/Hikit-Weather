package org.sec.weather

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "S&C Weather API", version = "0.0.2"))
class WeatherApplication: SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<WeatherApplication>(*args)
}
