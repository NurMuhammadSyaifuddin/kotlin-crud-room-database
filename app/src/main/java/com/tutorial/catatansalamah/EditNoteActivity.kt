package com.tutorial.catatansalamah

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_edit_note.*

class EditNoteActivity : AppCompatActivity() {

    private lateinit var model: EditNoteViewModel
    private lateinit var note: LiveData<Note>

    companion object{
        const val NOTE_ID = "note_id"
        const val UPDATED_NOTE = "note_update"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        val id = intent.getStringExtra(NOTE_ID)
        model = ViewModelProvider(this).get(EditNoteViewModel::class.java)
        note = model.getNote(id)!!

        note.observe(this, object : Observer<Note>{
            override fun onChanged(t: Note?) {
                edtEditText.setText(t?.note)
            }

        })

        btnUpdate.setOnClickListener{
            val updateNote = edtEditText.text.toString().trim()
            Intent().also {
                it.putExtra(NOTE_ID, id)
                it.putExtra(UPDATED_NOTE, updateNote)
                setResult(Activity.RESULT_OK, it)
                finish()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }

    }
}