package io.clean.crud.test

import io.clean.crud.dataprovider.BookMongoQueryRepository
import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookCategory
import io.clean.crud.domain.BookId
import io.clean.crud.domain.BookRepository
import io.clean.crud.domain.PublicationDate
import io.clean.crud.domain.Title
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@EnableAutoConfiguration(exclude = [EmbeddedMongoAutoConfiguration::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [BaseTest.Initializer::class])
class BookRepositoryIT : BaseTest() {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var bookMongoQueryRepository: BookMongoQueryRepository

    @BeforeMethod
    fun init() {
        bookMongoQueryRepository.deleteAll().block()
    }

    @Test
    fun `Create a new Book`() {

        // given
        val aNewBook = buildBook()

        // when
        val createdBook = bookRepository.create(aNewBook).block()

        // then
        assertThat(createdBook).isNotNull
        assertThat(createdBook!!.id).isNotNull()
        assertThat(createdBook.author).isNotNull
        assertThat(createdBook.author.value).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Update existing Book`() {

        // given
        val aNewBook = buildBook()

        val createdBook = bookRepository.create(aNewBook).block()!!

        assertThat(createdBook.id!!).isNotNull()

        // when
        val update = bookRepository.update(createdBook.copy(publicationDate = PublicationDate(DateTime.parse("1984-06-30")))).block()!!

        // then
        assertThat(update.publicationDate).isEqualTo(1984)

    }

    @Test
    fun `Delete existing Book`() {

        // given
        val aNewBook = buildBook()

        val createdBook = bookRepository.create(aNewBook).block()!!

        assertThat(createdBook.id).isNotNull
        assertThat(bookMongoQueryRepository.count().block()!!.toInt()).isEqualTo(1)

        // when
        bookRepository.delete(createdBook.id).block()

        // then
        assertThat(bookMongoQueryRepository.count().block()!!.toInt()).isEqualTo(0)

    }

    @Test
    fun `Get books by author`() {

        // given
        val aNewBook = buildBook()

        // when
        bookRepository.create(aNewBook).block()

        // then
        val result = bookRepository.findByAuthor(Author(value = "Arthur C. Clarke"))
        assertThat(result.collectList().block()!!.size).isEqualTo(1)

    }

    private fun buildBook(): Book {
        return Book(
                id = BookId("1"),
                author = Author(value = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = Title("The Sentinel"),
                publicationDate = PublicationDate(DateTime.parse("1948-06-30")))
    }

}