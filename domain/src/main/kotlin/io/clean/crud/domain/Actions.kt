package io.clean.crud.domain

import mu.KotlinLogging

interface BookService {
    fun create(book: Book): Book
    fun read(id: String): Book?
    fun update(book: Book): Book
    fun delete(id: String)
}

class CreateBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(book: Book): Book {

        logger.debug("Creating book: $book")

        return bookService.create(book).also { logger.debug("Successfully created book: $book") }

    }

}

class ReadBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(id: String): Book? {

        logger.debug("Fetching book with id: $id")

        return bookService.read(id).also { logger.debug("Returning book: $it") }

    }

}

class UpdateBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(book: Book): Book {

        logger.debug("Updating book $book")

        return bookService.update(book).also { logger.debug("Book ${it.id} updated") }

    }

}

class DeleteBookUseCase(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    fun execute(id: String) {

        logger.debug("Deleting book with id: $id")

        bookService.delete(id)

    }
}