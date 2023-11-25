package com.thebrownfoxx.books.model

import java.time.LocalDate
import java.util.UUID

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val datePublished: LocalDate,
    val dateAdded: LocalDate,
    val dateModified: LocalDate?,
    val pages: Int,
    val pagesRead: Int,
    val type: BookType,
) {
    val readingProgress = pagesRead.toFloat() / pages
}

fun Book(
    author: String,
    title: String,
    datePublished: LocalDate,
    pages: Int,
) = Book(
    id = UUID.randomUUID().toString(),
    title = title,
    author = author,
    datePublished = datePublished,
    dateAdded = LocalDate.now(),
    dateModified = null,
    pages = pages,
    pagesRead = 0,
    type = BookType.NonFavorite,
)