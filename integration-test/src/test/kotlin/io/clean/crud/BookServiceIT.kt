package io.clean.crud

import io.clean.crud.config.Injector
import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookCategory
import io.clean.crud.domain.BookService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testng.annotations.Test

@SpringBootTest(classes = [Injector::class])
@ContextConfiguration(initializers = [BaseTest.Initializer::class])
class BookServiceIT : BaseTest() {

    @Autowired
    private lateinit var bookService: BookService

    @Test
    fun `Create a new Book`() {

        // given
        val aNewBook = Book(
                author = Author(id = "1", fullName = "Arthur C. Clarke"),
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

}