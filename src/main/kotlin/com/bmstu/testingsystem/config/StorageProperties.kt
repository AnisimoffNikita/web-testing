package com.bmstu.testingsystem.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("storage")
class StorageProperties {

    var location = "src/main/resources/static/avatar"

}