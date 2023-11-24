package com.thebrownfoxx.books.realm

import com.thebrownfoxx.books.model.Book
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class RealmBook: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String? = null
    var author: String? = null
    var datePublishedEpochDay: Long? = null
    var dateAddedEpochDay: Long? = null
    var dateModifiedEpochDay: Long? = null
    var favorite: Boolean = false
    var pages: Int? = null
    var pagesRead: Int = 0
    var archived: Boolean = false

    fun toBook() = Book(
        id = id.toHexString(),
        author = author ?: "",
        title = title ?: "",
        datePublished = LocalDate.ofEpochDay(datePublishedEpochDay ?: 0),
        dateAdded = LocalDate.ofEpochDay(dateAddedEpochDay ?: 0),
        dateModified = dateModifiedEpochDay?.let { LocalDate.ofEpochDay(it) },
        favorite = favorite,
        pages = pages ?: 0,
        pagesRead = pagesRead,
        archived = archived,
    )
}