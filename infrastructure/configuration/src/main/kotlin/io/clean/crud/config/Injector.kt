package io.clean.crud.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.clean.crud.dataprovider.BookDocument
import io.clean.crud.dataprovider.BookMongoQueryRepository
import io.clean.crud.dataprovider.MongoBookRepository
import io.clean.crud.domain.BookRepository
import io.clean.crud.domain.CreateBook
import io.clean.crud.domain.DeleteBook
import io.clean.crud.domain.ReadBook
import io.clean.crud.domain.UpdateBook
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.ReactiveMongoRepository


@Configuration
class Injector(val bookRepository: ReactiveMongoRepository<BookDocument, String>) {

	@Bean
	fun bookRepository(bookMongoQuery: BookMongoQueryRepository) = MongoBookRepository(bookMongoQuery)

	@Bean
	fun createBookUseCase(bookRepository: BookRepository) = CreateBook(bookRepository)

	@Bean
	fun readBookUseCase(bookRepository: BookRepository) = ReadBook(bookRepository)

	@Bean
	fun updateBookUseCase(bookRepository: BookRepository) = UpdateBook(bookRepository)

	@Bean
	fun deleteBookUseCase(bookRepository: BookRepository) = DeleteBook(bookRepository)

	@Bean
	fun objectMapper(): ObjectMapper {
		val objectMapper = ObjectMapper()
		objectMapper.registerModule(Jdk8Module())
		objectMapper.registerKotlinModule()
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
		return objectMapper
	}

}