package io.clean.crud.domain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Mono

class ActionsTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Create a new book`() {

        // given
        val aNewBook = Book(
                author = Author(value = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = Title("The Sentinel"),
                publicationDate = PublicationDate(DateTime.parse("1948-06-30")))

        every { bookRepository.create(aNewBook) } returns Mono.just(aNewBook.copy(BookId("1")))

        val useCase = CreateBook(bookRepository)

        // when
        val result = useCase.execute(aNewBook).block()!!

        // then
        assertThat(result).isNotNull
        assertThat(result.id!!.value).isEqualTo("1")
        assertThat(result.category).isEqualTo(BookCategory.FICTION)
        assertThat(result.title.value).isEqualTo("The Sentinel")

        assertThat(result.author).isNotNull
        assertThat(result.author.value).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Read existing book passing id`() {

        // given
        val existingBook = Book(
                id = BookId("1"),
                author = Author(value = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = Title("The Sentinel"),
                publicationDate = PublicationDate(DateTime.parse("1948-06-30")))

        every { bookRepository.findById("1") } returns Mono.just(existingBook)

        val useCase = ReadBook(bookRepository)

        // when
        val result = useCase.execute("1")?.block()

        // then
        assertThat(result).isNotNull
        assertThat(result!!.id!!.value).isEqualTo("1")
        assertThat(result.category).isEqualTo(BookCategory.FICTION)
        assertThat(result.title.value).isEqualTo("The Sentinel")

        assertThat(result.author).isNotNull
        assertThat(result.author.value).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Read unexisting book passing id`() {

        // given
        val nonExistingBook = null

        every { bookRepository.findById("2") } returns nonExistingBook

        val useCase = ReadBook(bookRepository)

        // when
        val result = useCase.execute("2")

        // then
        assertThat(result).isNull()

    }

    @Test
    fun `Update existing book`() {

        // given
        val bookToUpdate = Book(
                id = BookId("1"),
                author = Author(value = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = Title("The Sentinell"),
                publicationDate = PublicationDate(DateTime.parse("1947-06-30")))

        every { bookRepository.update(bookToUpdate) } returns Mono.just(bookToUpdate)

        val useCase = UpdateBook(bookRepository)

        // when
        val result = useCase.execute(bookToUpdate).block()!!

        // then
        assertThat(result).isNotNull
        assertThat(result.id!!.value).isEqualTo("1")
        assertThat(result.category).isEqualTo(BookCategory.FICTION)
        assertThat(result.title.value).isEqualTo("The Sentinell")
        assertThat(result.publicationDate.value.year().get()).isEqualTo(1947)

        assertThat(result.author).isNotNull
        assertThat(result.author.value).isEqualTo("Arthur C. Clarke")

    }

    @Test
    fun `Delete book passing id`() {

        // given
        val bookIdToDelete = "1"

        val emptyResponse: Mono<Void> = Mono.empty()
        every { bookRepository.delete(bookIdToDelete) } answers { emptyResponse }

        val useCase = DeleteBook(bookRepository)

        // when
        val result = useCase.execute(bookIdToDelete).block()

        // then
        assertThat(result).isNull()

    }

}