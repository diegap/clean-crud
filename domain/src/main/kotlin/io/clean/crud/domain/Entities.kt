package io.clean.crud.domain

import org.joda.time.DateTime

data class Book(val id: BookId? = null,
				val title: Title,
				val category: BookCategory,
				val author: Author,
				val publicationDate: PublicationDate
)

data class BookId(val value: String) {
	init {
		check(value.isNotBlank()) { "Book id cannot be blank" }
	}
}

data class Title(val value: String) {
	init {
		check(value.isNotBlank()) { "Title cannot be blank" }
	}
}

data class PublicationDate(val value: DateTime) {
	init {
		check(value.isBeforeNow) { "Publication date must be before today" }
	}
}

data class Author(val value: String) {
	init {
		check(value.isNotBlank())
	}
}

enum class BookCategory {
	FICTION,
	HISTORICAL,
	TECHNICAL
}
