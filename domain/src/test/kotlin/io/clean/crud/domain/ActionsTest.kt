package io.clean.crud.domain

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test

class ActionsTest {

	@Test
	fun `Create a new book`() {

		// given
		val aNewBook = Book(
                id = BookId("1"),
				author = Author(value = "Arthur C. Clarke"),
				category = BookCategory.FICTION,
				title = Title("The Sentinel"),
				publicationDate = PublicationDate(DateTime.parse("1948-06-30")))

		val useCase = CreateBook(InMemoryBookRepository())

		// when
		val result = useCase(aNewBook).block()!!

		// then
		assertThat(result).isNotNull
		assertThat(result.id.value).isEqualTo("1")
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

        val bookRepository = InMemoryBookRepository(mutableMapOf(existingBook.id!! to existingBook))
		val useCase = ReadBook(bookRepository)

		// when
		val result = useCase(BookId("1"))?.block()

		// then
		assertThat(result).isNotNull
		assertThat(result!!.id.value).isEqualTo("1")
		assertThat(result.category).isEqualTo(BookCategory.FICTION)
		assertThat(result.title.value).isEqualTo("The Sentinel")

		assertThat(result.author).isNotNull
		assertThat(result.author.value).isEqualTo("Arthur C. Clarke")

	}

	@Test
	fun `Read unexisting book passing id`() {

		// given

		val useCase = ReadBook(InMemoryBookRepository())

		// when
		val result = useCase(BookId("2"))

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

		val useCase = UpdateBook(InMemoryBookRepository())

		// when
		val result = useCase(bookToUpdate).block()!!

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
		val bookIdToDelete = BookId("1")

		val useCase = DeleteBook(InMemoryBookRepository())

		// when
		val result = useCase(bookIdToDelete).block()

		// then
		assertThat(result).isNull()

	}

}