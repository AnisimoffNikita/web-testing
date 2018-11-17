package com.bmstu.testingsystem

import com.bmstu.testingsystem.config.StorageProperties
import com.bmstu.testingsystem.services.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication



@SpringBootApplication
@EnableConfigurationProperties(StorageProperties::class)
class TestingSystemApplication

fun main(args: Array<String>) {
    runApplication<TestingSystemApplication>(*args)
}
