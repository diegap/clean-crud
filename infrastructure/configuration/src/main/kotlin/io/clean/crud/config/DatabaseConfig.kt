package io.clean.crud.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.clean.crud.dataprovider.BookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = [BookRepository::class])
class MongoReactiveApplication(private val environment: Environment) : AbstractReactiveMongoConfiguration() {

    override fun reactiveMongoClient(): MongoClient = mongoClient()

    @Bean
    //@DependsOn("embeddedMongoServer")
    fun mongoClient(): MongoClient {
        val port = environment.getProperty("spring.data.mongodb.port", Int::class.java)
        return MongoClients.create(String.format("mongodb://localhost:$port"))
    }

    override fun getDatabaseName(): String {
        return "rxdb"
    }
}