package org.sec.weather

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class HealthController {
    @RequestMapping("/health")
    fun health(): String = "Ok"
}