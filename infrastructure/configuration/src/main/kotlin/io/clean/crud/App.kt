package io.clean.crud

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
class App

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}