package io.clean.crud.domain

import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

class InMemoryBookRepository(private val cache: MutableMap<BookId, Book> = mutableMapOf()) : BookRepository {

	override fun create(book: Book): Mono<Book> {
		cache[book.id] = book
		return Mono.just(book)
	}

	override fun update(book: Book): Mono<Book> {
		cache[book.id] = book
		return Mono.just(book)
	}

	override fun delete(id: BookId): Mono<Void> {
		cache.remove(id)
		return Mono.empty()
	}

	override fun findById(id: BookId) = cache[id]?.toMono()

	override fun findByTitle(title: Title) = cache.values.filter { it.title == title }.toFlux()

	override fun findByAuthor(author: Author) = cache.values.filter { it.author == author }.toFlux()

	override fun findAll() = cache.values.toFlux()
}