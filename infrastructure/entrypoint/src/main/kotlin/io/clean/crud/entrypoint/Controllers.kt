package io.clean.crud.entrypoint

import io.clean.crud.domain.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.toMono

@RestController
@RequestMapping("/books")
class BookController(private val createBook: CreateBook,
                     private val readBook: ReadBook,
                     private val updateBook: UpdateBook,
                     private val deleteBook: DeleteBook) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable id: String) =
            readBook(id)


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() =
            readBook()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: Book) =
            createBook(book).toMono()


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: String) =
            deleteBook(id)

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody book: Book) =
            updateBook(book).toMono()

}