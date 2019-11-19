package io.clean.crud.entrypoint

import io.clean.crud.domain.Book
import io.clean.crud.domain.BookFactory
import io.clean.crud.domain.BookId
import io.clean.crud.domain.CreateBook
import io.clean.crud.domain.DeleteBook
import io.clean.crud.domain.DraftBook
import io.clean.crud.domain.ReadBook
import io.clean.crud.domain.UpdateBook
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.toMono

@RestController
@RequestMapping("/books")
class BookController(private val createBook: CreateBook,
					 private val readBook: ReadBook,
					 private val updateBook: UpdateBook,
					 private val deleteBook: DeleteBook,
					 private val bookFactory: BookFactory) {

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun getById(@PathVariable id: String) = readBook(BookId(id))

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun getAll() = readBook()

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun create(@RequestBody draftBook: DraftBook) = createBook(bookFactory.from(draftBook)).toMono()


	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun delete(@PathVariable id: String) = deleteBook(BookId(id))

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	fun update(@RequestBody book: Book) = updateBook(book).toMono()

}