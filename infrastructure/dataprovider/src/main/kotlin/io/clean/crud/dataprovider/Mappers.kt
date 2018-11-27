package io.clean.crud.dataprovider

import io.clean.crud.dataprovider.domain.Author
import io.clean.crud.dataprovider.domain.Book

object BookMapper {

    fun from(book: Book): BookDocument {
        return BookDocument(
                title = book.title,
                category = book.category,
                author = AuthorMapper.from(book.author))
    }

    fun from(bookDocument: BookDocument): Book {
        return Book(
                title = bookDocument.title,
                category = bookDocument.category,
                author = AuthorMapper.from(bookDocument.author)
        )
    }

}

object AuthorMapper {

    fun from(author: Author): AuthorDocument {
        return AuthorDocument(
                fullName = author.fullName
        )
    }

    fun from(authorDocument: AuthorDocument): Author {
        return Author(id = authorDocument.id, fullName = authorDocument.fullName)
    }

}