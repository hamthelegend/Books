package com.thebrownfoxx.books

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.books.realm.BookRealmDatabase

class BookRealmApplication : Application() {
    lateinit var database: BookRealmDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = BookRealmDatabase()
    }
}

val CreationExtras.application
    get() = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookRealmApplication)