package io.clean.crud.test

import io.clean.crud.dataprovider.BookRepository
import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookCategory
import io.clean.crud.domain.BookService
import org.assertj.core.api.Assertions.assertThat
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
class BookServiceIT : BaseTest() {

    @Autowired
    private lateinit var bookService: BookService

    @Autowired
    private lateinit var bookRepository: BookRepository

    @BeforeMethod
    fun init() {
        bookRepository.deleteAll().block()
    }

    @Test
    fun `Create a new Book`() {

        // given
        val aNewBook = Book(
                author = Author(fullName = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = "The Sentinel",
                year = 1948)

        // when
        val createdBook = bookService.create(aNewBook).block()

        // then
        assertThat(createdBook).isNotNull
        assertThat(createdBook!!.id).isNotNull()
        assertThat(createdBook.author).isNotNull
        assertThat(createdBook.author.fullName).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Update existing Book`() {

        // given
        val aNewBook = Book(
                author = Author(fullName = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = "The Sentinel",
                year = 1948)

        val createdBook = bookService.create(aNewBook).block()!!

        assertThat(createdBook.id!!).isNotNull()

        // when
        val update = bookService.update(createdBook.copy(year = 1984)).block()!!

        // then
        assertThat(update.year).isEqualTo(1984)

    }

    @Test
    fun `Delete existing Book`() {

        // given
        val aNewBook = Book(
                author = Author(fullName = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = "The Sentinel",
                year = 1948)

        val createdBook = bookService.create(aNewBook).block()!!

        assertThat(createdBook.id!!).isNotNull()
        assertThat(bookRepository.count().block()!!.toInt()).isEqualTo(1)

        // when
        bookService.delete(createdBook.id!!).block()

        // then
        assertThat(bookRepository.count().block()!!.toInt()).isEqualTo(0)

    }

}