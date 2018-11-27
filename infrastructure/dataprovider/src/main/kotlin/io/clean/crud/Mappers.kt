package io.clean.crud

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book

object BookMapper {

    fun from(book: Book): BookDocument {
        return BookDocument(
                title = book.title,
                category = book.category,
                author = AuthorMapper.from(book.author))
    }

}

object AuthorMapper {

    fun from(author: Author): AuthorDocument {
        return AuthorDocument(
                fullName = author.fullName
        )
    }

}