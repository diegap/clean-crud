package io.clean.crud

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookCategory
import io.clean.crud.domain.PublicationDate
import io.clean.crud.domain.Title
import io.clean.crud.test.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.testng.annotations.Test

@EnableAutoConfiguration(exclude = [EmbeddedMongoAutoConfiguration::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [BaseTest.Initializer::class])
class ControllerIT : BaseTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `Get book by unexisting id`() {
        webTestClient
                .get().uri("/books/5c0d82ddd7524043aeec084f")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody().isEmpty
    }

    @Test
    fun `Create a new book`() {
            val aNewBook = buildBook()

        val response = webTestClient
                .post().uri("/books")
                .body(BodyInserters.fromObject(aNewBook))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated
                .returnResult(Book::class.java)

        val body = response.responseBody.blockFirst()

        assertThat(body!!).isNotNull
        assertThat(body.title).isEqualTo("The Sentinel")
        assertThat(body.author.value).isEqualTo("Arthur C. Clarke")
    }

    @Test
    fun `Get book by existing id`() {

        val aNewBook = buildBook()

        val response = webTestClient
                .post().uri("/books")
                .body(BodyInserters.fromObject(aNewBook))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated
                .returnResult(Book::class.java)

        val bookId = response.responseBody.blockFirst()!!.id!!

        webTestClient
                .get().uri("/books/$bookId")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.title").isNotEmpty
                .jsonPath("$.title").isEqualTo("The Sentinel")
    }

    private fun buildBook(): Book {
        return Book(
                author = Author(value = "Arthur C. Clarke"),
                category = BookCategory.FICTION,
                title = Title("The Sentinel"),
                publicationDate = PublicationDate(DateTime.parse("1948-06-30")))
    }

}