package io.clean.crud

import io.clean.crud.dataprovider.AuthorDocument
import io.clean.crud.dataprovider.BookDocument
import io.clean.crud.dataprovider.BookRepository
import io.clean.crud.dataprovider.BookServiceDataProvider
import io.clean.crud.domain.BookCategory
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Flux

class BookServiceDataProviderTests {

    @MockK
    private lateinit var bookRepository: BookRepository

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Find book by title`() {

        // given
        val aBook = BookDocument(
                id = "5c0d82ddd7524043aeec084f",
                author = AuthorDocument(fullName = "Ray Bradbury"),
                category = BookCategory.FICTION,
                title = "Fahrenheit 451",
                year = 1953)

        val anotherBook = BookDocument(
                id = "5c0d82ddd7524043aeec04f5",
                author = AuthorDocument(fullName = "Ray Bradbury"),
                category = BookCategory.FICTION,
                title = "Fahrenheit 451 - special edition",
                year = 1953)

        every { bookRepository.findByTitle("451") } answers { Flux.just(aBook, anotherBook) }

        val dataProvider = BookServiceDataProvider(bookRepository)

        // when
        val results = dataProvider.findByTitle("451")

        // then
        assertThat(results.collectList().block()!!.size).isEqualTo(2)

    }


}