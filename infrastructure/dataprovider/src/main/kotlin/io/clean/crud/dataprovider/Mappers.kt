package io.clean.crud.dataprovider

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book

object BookMapper {

    fun from(book: Book): BookDocument {
        return BookDocument(
                id = book.id,
                title = book.title,
                category = book.category,
                author = AuthorMapper.from(book.author))
    }

    fun from(bookDocument: BookDocument): Book {
        return Book(
                id = bookDocument.id,
                title = bookDocument.title,
                category = bookDocument.category,
                author = AuthorMapper.from(bookDocument.author)
        )
    }

}

object AuthorMapper {

    fun from(author: Author) =
            AuthorDocument(id = author.id, fullName = author.fullName)

    fun from(authorDocument: AuthorDocument) =
            Author(id = authorDocument.id, fullName = authorDocument.fullName)

}