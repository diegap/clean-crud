package io.clean.crud.domain

import mu.KotlinLogging
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

interface BookService {
    fun create(book: Book): Mono<Book>
    fun update(book: Book): Mono<Book>
    fun delete(id: String): Mono<Void>
    fun findById(id: String): Mono<Book>?
    fun findByTitle(title: String): Flux<Book>
    fun findByAuthor(author: Author): Flux<Book>
    fun findAll(): Flux<Book>
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
        return bookService.findById(id).also { logger.debug("Returning book: $it") }
    }

    fun execute(): Flux<Book> {
        logger.debug("Retrieving all books ...")
        return bookService.findAll()
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

    fun execute(id: String): Mono<Void> {
        logger.debug("Deleting book with id: $id")
        return bookService.delete(id).toMono()
    }
}