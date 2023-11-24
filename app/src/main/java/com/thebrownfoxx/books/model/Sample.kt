package com.thebrownfoxx.books.model

import java.time.LocalDate
import java.time.Month

object Sample {
    val Book = Book(
        title = "Sober Up",
        author = "AJR",
        datePublished = LocalDate.of(2017, Month.JUNE, 9),
        pages = 17,
    ).copy(pagesRead = 8)

    val Books = listOf(
        Book,
        Book(
            title = "Inertia",
            author = "AJR",
            datePublished = LocalDate.of(2023, Month.NOVEMBER, 10),
            pages = 23,
        ),
        Book(
            title = "Karma",
            author = "AJR",
            datePublished = LocalDate.of(2019, Month.APRIL, 26),
            pages = 19,
        )
    )
}