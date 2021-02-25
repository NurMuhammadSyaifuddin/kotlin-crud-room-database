package com.tutorial.catatansalamah

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private var noteDao: NoteDao?
    private var noteDb: NoteRoomDatabase?
    private var list: LiveData<List<Note>>

    init {
        noteDb = NoteRoomDatabase.getDatabase(application)
        noteDao = noteDb?.noteDao()
        list = noteDao?.getListNotes()!!

    }

    fun insert(textNote: String){
        insertToDatabase(textNote)
    }

    fun getListNotes(): LiveData<List<Note>> = list

    private fun insertToDatabase(textNote: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val noteId = UUID.randomUUID().toString()
            val note = Note(noteId, textNote)
            noteDao?.insert(note)
        }

    }

    fun update(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao?.updateNote(note)
        }
    }

    fun delete(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao?.deleteNote(note)
        }
    }

}