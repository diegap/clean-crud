package io.clean.crud.dataprovider.domain

data class Book(val id: String? = null,
                val title: String,
                val category: BookCategory,
                val author: Author,
                val year: Int? = null)

data class Author(val id: String?, val fullName: String)

enum class BookCategory {
    FICTION,
    HISTORICAL,
    TECHNICAL
}
