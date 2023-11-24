package com.thebrownfoxx.books.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class BookRealmDatabase {
    private val config = RealmConfiguration
        .Builder(schema = setOf(RealmBook::class))
        .schemaVersion(1)
        .build()
    private val realm = Realm.open(config)

    fun getBook(id: ObjectId) =
        realm.query<RealmBook>("id == $0", id).first().asFlow().map { it.obj }

    fun getAllNonFavoriteBooks() =
        realm.query<RealmBook>("favorite == $0 AND archived == $1", false, false)
            .asFlow().map { it.list }

    fun getAllFavoriteBooks() =
        realm.query<RealmBook>("favorite == $0 AND archived == $1", true, false)
            .asFlow().map { it.list }

    fun getAllArchivedBooks() =
        realm.query<RealmBook>("archived == $0", true).asFlow().map { it.list }

    suspend fun addBook(
        title: String,
        author: String,
        datePublished: LocalDate,
        pages: Int,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = RealmBook()
                book.title = title
                book.author = author
                book.datePublishedEpochDay = datePublished.toEpochDay()
                book.dateAddedEpochDay = LocalDate.now().toEpochDay()
                book.pages = pages
                copyToRealm(book)
            }
        }
    }

    suspend fun favoriteBook(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = realm.query<RealmBook>("id == $0", id).first().find()
                if (book != null) { findLatest(book)?.favorite = true }
            }
        }
    }

    suspend fun updateBook(
        id: ObjectId,
        title: String? = null,
        author: String? = null,
        datePublished: LocalDate? = null,
        pages: Int? = null,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = realm.query<RealmBook>("id == $0", id).first().find()
                if (book != null) {
                    val latestBook = findLatest(book)
                    if (title != null) latestBook?.title = title
                    if (author != null) latestBook?.author = author
                    if (datePublished != null)
                        latestBook?.datePublishedEpochDay = datePublished.toEpochDay()
                    if (pages != null) latestBook?.pages = pages

                    val hasEdits = listOf(author, title, datePublished, pages).any { it != null }
                    if (hasEdits) latestBook?.dateModifiedEpochDay = LocalDate.now().toEpochDay()
                }
            }
        }
    }

    suspend fun updateBookProgress(
        id: ObjectId,
        pagesRead: Int,
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = realm.query<RealmBook>("id == $0", id).first().find()
                if (book != null) {
                    findLatest(book)?.pagesRead = pagesRead
                }
            }
        }
    }

    suspend fun archiveBook(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = realm.query<RealmBook>("id == $0", id).first().find()
                if (book != null) {
                    findLatest(book)?.archived = true
                }
            }
        }
    }

    suspend fun unarchiveBook(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = realm.query<RealmBook>("id == $0", id).first().find()
                if (book != null) {
                    findLatest(book)?.archived = false
                }
            }
        }
    }

    suspend fun deleteBook(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                val book = query<RealmBook>("id == $0", id).first().find()
                if (book != null) delete(book)
            }
        }
    }
}