package io.clean.crud.dataprovider

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookService
import mu.KotlinLogging
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

@Repository
interface BookRepository : ReactiveMongoRepository<BookDocument, String> {

    fun findByTitle(title: String): Flux<BookDocument>

    fun findByAuthor(author: AuthorDocument): Flux<BookDocument>

}

class BookServiceDataProvider(private val bookRepository: BookRepository) : BookService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun create(book: Book) =
            bookRepository.save(BookMapper.from(book)).map { BookMapper.from(it) }.toMono()

    override fun update(book: Book): Mono<Book> =
            bookRepository
                    .findById(book.id!!)
                    .flatMap {
                        it.copy(
                                title = book.title,
                                category = book.category,
                                year = book.year,
                                author = AuthorMapper.from(book.author)
                        ).toMono()
                    }.map {
                        bookRepository.save(it)
                        it
                    }.map {
                        BookMapper.from(it)
                    }.toMono()

    override fun delete(id: String): Mono<Void> =
            bookRepository
                    .deleteById(id)
                    .doOnSuccess { logger.debug("Successfully deleted book with id: $id") }
                    .doOnError { logger.error("Cannot delete book with id: $id", it) }

    override fun findById(id: String) =
            bookRepository.findById(id).map { BookMapper.from(it) }

    override fun findByTitle(title: String) =
            bookRepository.findByTitle(title).map { BookMapper.from(it) }.toFlux()

    override fun findByAuthor(author: Author) =
            bookRepository.findByAuthor(AuthorMapper.from(author)).map { BookMapper.from(it) }.toFlux()

}