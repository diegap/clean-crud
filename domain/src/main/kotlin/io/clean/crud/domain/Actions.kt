package io.clean.crud.domain

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookService {
    fun create(book: Book): Mono<Book>
    fun read(id: String): Mono<Book>?
    fun update(book: Book): Mono<Book>
    fun delete(id: String)
    fun findByTitle(title: String): Flux<Book>
    fun findByAuthot(author: Author): Flux<Book>
}

class CreateBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(book: Book): Mono<Book> {

        logger.debug("Creating book: $book")

        return bookService.create(book).also { logger.debug("Successfully created book: $book") }

    }

}

class ReadBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(id: String): Mono<Book>? {

        logger.debug("Fetching book with id: $id")

        return bookService.read(id).also { logger.debug("Returning book: $it") }

    }

}

class UpdateBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(book: Book): Mono<Book> {

        logger.debug("Updating book $book")

        return bookService.update(book).also { it -> logger.debug("Book ${it.map { it.id }} updated") }

    }

}

class DeleteBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(id: String): String {

        logger.debug("Deleting book with id: $id")

        bookService.delete(id)

        return "OK"

    }
}