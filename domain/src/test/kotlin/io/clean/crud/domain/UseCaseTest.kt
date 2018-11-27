package io.clean.crud.domain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Mono

class `Use cases related to Book entity` {

    @MockK
    private lateinit var bookService: BookService

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Create a new book`() {

        // given
        val aNewBook = Book(
                author = Author(id = "1", fullName = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = "The Sentinel",
                year = 1948)

        every { bookService.create(aNewBook) } returns Mono.just(aNewBook.copy(id = "1"))

        val useCase = CreateBookUseCase(bookService)

        // when
        val result = useCase.execute(aNewBook).block()!!

        // then
        assertThat(result).isNotNull()
        assertThat(result.id).isNotBlank()
        assertThat(result.id).isEqualTo("1")
        assertThat(result.category).isEqualTo(BookCategory.FICTION)
        assertThat(result.title).isEqualTo("The Sentinel")

        assertThat(result.author).isNotNull()
        assertThat(result.author.fullName).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Read existing book passing id`() {

        // given
        val existingBook = Book(
                id = "1",
                author = Author(id = "1", fullName = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = "The Sentinel",
                year = 1948)

        every { bookService.read("1") } returns Mono.just(existingBook)

        val useCase = ReadBookUseCase(bookService)

        // when
        val result = useCase.execute("1")?.block()

        // then
        assertThat(result).isNotNull()
        assertThat(result!!.id).isNotBlank()
        assertThat(result.id).isEqualTo("1")
        assertThat(result.category).isEqualTo(BookCategory.FICTION)
        assertThat(result.title).isEqualTo("The Sentinel")

        assertThat(result.author).isNotNull()
        assertThat(result.author.fullName).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Read unexisting book passing id`() {

        // given
        val nonExistingBook = null

        every { bookService.read("2") } returns nonExistingBook

        val useCase = ReadBookUseCase(bookService)

        // when
        val result = useCase.execute("2")

        // then
        assertThat(result).isNull()

    }

    @Test
    fun `Update existing book`() {

        // given
        val bookToUpdate = Book(
                id = "1",
                author = Author(id = "1", fullName = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = "The Sentinell",
                year = 1947)

        every { bookService.update(bookToUpdate) } returns Mono.just(bookToUpdate)

        val useCase = UpdateBookUseCase(bookService)

        // when
        val result = useCase.execute(bookToUpdate).block()!!

        // then
        assertThat(result).isNotNull()
        assertThat(result.id).isNotBlank()
        assertThat(result.id).isEqualTo("1")
        assertThat(result.category).isEqualTo(BookCategory.FICTION)
        assertThat(result.title).isEqualTo("The Sentinell")
        assertThat(result.year).isEqualTo(1947)

        assertThat(result.author).isNotNull()
        assertThat(result.author.fullName).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Delete book passing id`() {

        // given
        val bookIdToDelete = "1"

        every { bookService.delete(bookIdToDelete) } answers {}

        val useCase = DeleteBookUseCase(bookService)

        // when
        val result = useCase.execute(bookIdToDelete)

        // then
        assertThat(result).isEqualTo("OK")

    }

}