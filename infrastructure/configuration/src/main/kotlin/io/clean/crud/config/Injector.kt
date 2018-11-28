package io.clean.crud.config

import io.clean.crud.dataprovider.BookRepository
import io.clean.crud.dataprovider.BookServiceDataProvider
import io.clean.crud.domain.CreateBookUseCase
import io.clean.crud.domain.DeleteBookUseCase
import io.clean.crud.domain.ReadBookUseCase
import io.clean.crud.domain.UpdateBookUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Injector {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Bean
    fun bookService() =
            BookServiceDataProvider(bookRepository)

    @Bean
    fun createBookUseCase() = CreateBookUseCase(bookService())

    @Bean
    fun readBookUseCase() = ReadBookUseCase(bookService())

    @Bean
    fun updateBookUseCase() = UpdateBookUseCase(bookService())

    @Bean
    fun deleteBookUseCase() = DeleteBookUseCase(bookService())

}