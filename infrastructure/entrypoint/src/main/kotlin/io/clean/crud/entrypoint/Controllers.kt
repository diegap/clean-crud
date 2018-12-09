package io.clean.crud.entrypoint

import io.clean.crud.domain.Book
import io.clean.crud.domain.BookService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable id: String) =
            bookService.findById(id)


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: Book) =
            bookService.create(book).subscribe {
                logger.debug { "Created book $it" }
            }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: String) =
            bookService.delete(id).subscribe {
                logger.debug { "Deleted book with id: $id" }
            }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody book: Book) =
            bookService.update(book).subscribe {
                logger.debug { "Updated book: $book to >> $it" }
            }

}