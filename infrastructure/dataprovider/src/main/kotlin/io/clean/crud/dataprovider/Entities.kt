package io.clean.crud.dataprovider

import io.clean.crud.domain.Author
import io.clean.crud.domain.Book
import io.clean.crud.domain.BookCategory
import io.clean.crud.domain.BookId
import io.clean.crud.domain.PublicationDate
import io.clean.crud.domain.Title
import org.joda.time.DateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(value = "Book")
data class BookDocument(
		@Id
		val id: String? = null,
		@Indexed
		val title: String,
		val category: String,
		val author: String,
		val publicationDate: DateTime) {

	constructor(book: Book) : this(
			id = book.id!!.value,
			title = book.title.value,
			category = book.category.name,
			author = book.author.value,
			publicationDate = book.publicationDate.value
	)

	fun toDomain() = Book(
			id = BookId(id!!),
			title = Title(title),
			category = BookCategory.valueOf(category),
			author = Author(author),
			publicationDate = PublicationDate(publicationDate)
	)
}

@Document(value = "Author")
data class AuthorDocument(
		@Id
		val id: String? = null,
		@Indexed
		val fullName: String
) {
	constructor(author: Author) : this(
			fullName = author.value
	)

	fun toDomain(): Author {
		return Author(fullName)
	}
}

