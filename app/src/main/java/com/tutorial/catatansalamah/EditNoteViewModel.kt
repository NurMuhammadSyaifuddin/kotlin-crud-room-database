package com.tutorial.catatansalamah

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class EditNoteViewModel(application: Application): AndroidViewModel(application) {

    private var noteDao: NoteDao?
    private var noteDb: NoteRoomDatabase?

    init {
        noteDb = NoteRoomDatabase.getDatabase(application)
        noteDao = noteDb?.noteDao()
    }

    fun getNote(noteId: String): LiveData<Note>? = noteDao?.getNote(noteId)

}