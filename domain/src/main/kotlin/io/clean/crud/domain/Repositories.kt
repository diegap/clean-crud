package io.clean.crud.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookRepository {
	fun create(book: Book): Mono<Book>
	fun update(book: Book): Mono<Book>
	fun delete(id: BookId): Mono<Void>
	fun findById(id: BookId): Mono<Book>?
	fun findByTitle(title: Title): Flux<Book>
	fun findByAuthor(author: Author): Flux<Book>
	fun findAll(): Flux<Book>
}

interface BookdIdRepository {
	fun nextId(): BookId
}