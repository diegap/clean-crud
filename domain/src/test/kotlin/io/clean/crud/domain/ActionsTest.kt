package io.clean.crud.domain

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test

class ActionsTest {

	private lateinit var bookRepository: BookRepository

	private lateinit var createBookAction: CreateBook
	private lateinit var readBookAction: ReadBook
	private lateinit var updateBookAction: UpdateBook
	private lateinit var deleteBookAction: DeleteBook

	private var retrievedBook: Book? = null

	private val aNewBook = Book(
			id = BookId("1"),
			author = Author(value = "Arthur C. Clarke"),
			category = BookCategory.FICTION,
			title = Title("The Sentinel"),
			publicationDate = PublicationDate(DateTime.parse("1948-06-30")))

	@Test
	fun `Create a new book`() {

		givenACreateBookAction()

		whenCreateActionIsCalledWith(aNewBook)

		thenBooksIsCreated()

	}

	@Test
	fun `Read existing book passing id`() {

		givenAReadBookAction()

		whenReadBookIsCalledWith(BookId("1"))

		thenBookIsRetrieved()

	}

	@Test
	fun `Read unexisting book passing id`() {

		givenAReadBookAction()

		whenReadBookIsCalledWith(BookId("2"))

		thenNoBookIsRetrieved()

	}

	@Test
	fun `Update existing book`() {

		givenAnUpdateBookAction()

		whenUpdateIsCalledWith(aNewBook.copy(title = Title("The Sentinell")))

		thenBookIsUpdated()

	}

	@Test
	fun `Delete book passing id`() {

		givenADeleteBookAction()

		whenExistingBookIsDeleted()

		thenBookNoLongerExists()

	}

	private fun givenACreateBookAction() {
		bookRepository = InMemoryBookRepository()
		createBookAction = CreateBook(bookRepository)
	}

	private fun whenCreateActionIsCalledWith(book: Book) {
		createBookAction(book).block()!!
	}

	private fun thenBooksIsCreated() {
		assertThat(bookRepository.findById(aNewBook.id)).isNotNull
	}

	private fun givenAReadBookAction() {
		bookRepository = InMemoryBookRepository(mutableMapOf(aNewBook.id to aNewBook))
		readBookAction = ReadBook(bookRepository)
	}

	private fun whenReadBookIsCalledWith(bookId: BookId) {
		retrievedBook = readBookAction(bookId)?.block()
	}

	private fun thenBookIsRetrieved() {
		assertThat(retrievedBook).isNotNull
		assertThat(retrievedBook!!.id.value).isEqualTo("1")
	}

	private fun thenNoBookIsRetrieved() {
		assertThat(retrievedBook).isNull()
	}

	private fun givenAnUpdateBookAction() {
		bookRepository = InMemoryBookRepository(mutableMapOf(aNewBook.id to aNewBook))
		updateBookAction = UpdateBook(bookRepository)
	}

	private fun whenUpdateIsCalledWith(book: Book) {
		updateBookAction(book)
	}

	private fun thenBookIsUpdated() {
		val book = bookRepository.findById(BookId("1"))?.block()!!
		assertThat(book).isNotNull
		assertThat(book.title.value).isEqualTo("The Sentinell")
	}

	private fun givenADeleteBookAction() {
		bookRepository = InMemoryBookRepository(mutableMapOf(aNewBook.id to aNewBook))
		deleteBookAction = DeleteBook(bookRepository)
	}

	private fun whenExistingBookIsDeleted() {
		deleteBookAction(BookId("1"))
	}

	private fun thenBookNoLongerExists() {
		val book = bookRepository.findById(BookId("1"))
		assertThat(book).isNull()
	}

}