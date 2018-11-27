package io.clean.crud.dataprovider

import io.clean.crud.dataprovider.domain.Author
import io.clean.crud.dataprovider.domain.Book
import io.clean.crud.dataprovider.domain.BookService
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

interface BookRepository : ReactiveMongoRepository<BookDocument, String> {

    fun findByTitle(title: String): Flux<BookDocument>

    fun findByAuthor(author: AuthorDocument): Flux<BookDocument>

}

class BookServiceDataProvider(private val bookRepository: BookRepository) : BookService {

    override fun create(book: Book) =
            bookRepository.save(BookMapper.from(book)).map { BookMapper.from(it) }.toMono()

    override fun update(book: Book): Mono<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: String) = bookRepository.findById(id).map { BookMapper.from(it) }

    override fun findByTitle(title: String) =
            bookRepository.findByTitle(title).map { BookMapper.from(it) }.toFlux()

    override fun findByAuthor(author: Author) =
            bookRepository.findByAuthor(AuthorMapper.from(author)).map { BookMapper.from(it) }.toFlux()

}