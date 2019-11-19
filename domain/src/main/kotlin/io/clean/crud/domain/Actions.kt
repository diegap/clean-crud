package io.clean.crud.domain

import reactor.core.publisher.toMono

class CreateBook(private val bookRepository: BookRepository) {
	fun execute(book: Book) = bookRepository.create(book)
}

class ReadBook(private val bookRepository: BookRepository) {

	fun execute(id: String) = bookRepository.findById(id)

	fun execute() = bookRepository.findAll()

}

class UpdateBook(private val bookRepository: BookRepository) {
	fun execute(book: Book) = bookRepository.update(book)
}

class DeleteBook(private val bookRepository: BookRepository) {
	fun execute(id: String) = bookRepository.delete(id).toMono()
}