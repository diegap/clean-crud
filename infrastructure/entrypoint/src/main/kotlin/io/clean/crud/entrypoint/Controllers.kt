package io.clean.crud.entrypoint

import io.clean.crud.domain.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.toMono

@RestController
@RequestMapping("/books")
class BookController(private val createBookUseCase: CreateBookUseCase,
                     private val readBookUseCase: ReadBookUseCase,
                     private val updateBookUseCase: UpdateBookUseCase,
                     private val deleteBookUseCase: DeleteBookUseCase) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable id: String) =
            readBookUseCase.execute(id)


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() =
            readBookUseCase.execute()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: Book) =
            createBookUseCase.execute(book).toMono()


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: String) =
            deleteBookUseCase.execute(id)

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody book: Book) =
            updateBookUseCase.execute(book).toMono()

}