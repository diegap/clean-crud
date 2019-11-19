package io.clean.crud.dataprovider

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

interface BookMongoQueryRepository : ReactiveMongoRepository<BookDocument, String> {
	fun findByTitle(title: String): Flux<BookDocument>
	fun findByAuthor(author: AuthorDocument): Flux<BookDocument>
}

class MongoBookRepository(private val bookMongoQuery: BookMongoQueryRepository) : BookRepository {

	override fun create(book: Book) =
			bookMongoQuery.save(BookDocument(book)).map { it.toDomain() }.toMono()

	override fun update(book: Book): Mono<Book> =
			bookMongoQuery
					.findById(book.id!!.value)
					.flatMap {
						it.copy(
								title = book.title.value,
								category = book.category.name,
								publicationDate = book.publicationDate.value,
								author = book.author.value
						).toMono()
					}.map {
						bookMongoQuery.save(it)
						it
					}.map {
						it.toDomain()
					}.toMono()

	override fun findAll() = bookMongoQuery.findAll().map { it.toDomain() }.toFlux()

	override fun delete(id: String) = bookMongoQuery.deleteById(id)

	override fun findById(id: String) =
			bookMongoQuery.findById(id).map { it.toDomain() }.toMono()

	override fun findByTitle(title: String) =
			bookMongoQuery.findByTitle(title).map { it.toDomain() }.toFlux()

	override fun findByAuthor(author: Author) =
			bookMongoQuery.findByAuthor(AuthorDocument(author)).map { it.toDomain() }.toFlux()

}