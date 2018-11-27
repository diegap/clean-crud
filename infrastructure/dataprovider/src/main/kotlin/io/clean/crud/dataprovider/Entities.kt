package io.clean.crud.dataprovider


import io.clean.crud.dataprovider.domain.BookCategory
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "Book")
data class BookDocument(
        @Id
        val id: String? = null,
        @Indexed
        val title: String,
        val category: BookCategory,
        val author: AuthorDocument,
        val year: Int? = null)

@Document(value = "Author")
data class AuthorDocument(
        @Id
        val id: String? = null,
        val fullName: String
)

