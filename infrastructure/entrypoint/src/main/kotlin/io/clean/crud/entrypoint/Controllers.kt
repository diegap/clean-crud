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
            readBook.execute(id)


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() =
            readBook.execute()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: Book) =
            createBook.execute(book).toMono()


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: String) =
            deleteBook.execute(id)

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody book: Book) =
            updateBook.execute(book).toMono()

}