package io.clean.crud.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.clean.crud.dataprovider.BookRepository
import io.clean.crud.dataprovider.BookServiceDataProvider
import io.clean.crud.domain.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import


@Configuration
@Import(MongoReactiveApplication::class)
class Injector(val bookRepository: BookRepository) {

    @Bean
    fun bookService(): BookService =
            BookServiceDataProvider(bookRepository)

    @Bean
    fun createBookUseCase() = CreateBookUseCase(bookService())

    @Bean
    fun readBookUseCase() = ReadBookUseCase(bookService())

    @Bean
    fun updateBookUseCase() = UpdateBookUseCase(bookService())

    @Bean
    fun deleteBookUseCase() = DeleteBookUseCase(bookService())

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(Jdk8Module())
        objectMapper.registerKotlinModule()
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        return objectMapper
    }

}