package io.clean.crud

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookService
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BookRepository : ReactiveMongoRepository<BookDocument, String>

class BookServiceDataProvider(private val bookRepository: BookRepository) : BookService {

    override fun create(book: Book): Mono<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(id: String): Mono<Book>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(book: Book): Mono<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByTitle(title: String): Flux<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByAuthot(author: Author): Flux<Book> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}