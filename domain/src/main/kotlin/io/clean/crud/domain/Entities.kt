package io.clean.crud.domain

data class Book(val id: String, val title: String, val category: BookCategory, val author: Author, val year: Int?)

data class Author(val id: String, val fullName: String)

enum class BookCategory {
    FICTION,
    HISTORICAL,
    TECHNICAL
}
