package io.clean.crud.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookRepository {
	fun create(book: Book): Mono<Book>
	fun update(book: Book): Mono<Book>
	fun delete(id: String): Mono<Void>
	fun findById(id: String): Mono<Book>?
	fun findByTitle(title: String): Flux<Book>
	fun findByAuthor(author: Author): Flux<Book>
	fun findAll(): Flux<Book>
}