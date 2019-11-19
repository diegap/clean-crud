package io.clean.crud.domain

import reactor.core.publisher.toMono

class CreateBook(private val bookRepository: BookRepository) {
	operator fun invoke(book: Book) = bookRepository.create(book)
}

class ReadBook(private val bookRepository: BookRepository) {
	operator fun invoke(id: BookId) = bookRepository.findById(id)
	operator fun invoke() = bookRepository.findAll()
}

class UpdateBook(private val bookRepository: BookRepository) {
	operator fun invoke(book: Book) = bookRepository.update(book)
}

class DeleteBook(private val bookRepository: BookRepository) {
	operator fun invoke(id: BookId) = bookRepository.delete(id).toMono()
}