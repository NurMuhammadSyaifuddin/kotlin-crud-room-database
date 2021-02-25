package com.tutorial.catatansalamah

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val CREATE_NOTE_REQUEST_CODE = 1
        const val UPDATE_NOTE_REQUEST_CODE = 2
    }

    private lateinit var model: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NoteAdapter()
        model = ViewModelProvider(this).get(NoteViewModel::class.java)
        showListNote()

        btn_to_create.setOnClickListener {
            Intent(this@MainActivity, CreateNoteActivity::class.java).also {
                startActivityForResult(it, CREATE_NOTE_REQUEST_CODE)
            }
        }
    }

    private fun showListNote() {
        rv_note.setHasFixedSize(true)
        rv_note.layoutManager = LinearLayoutManager(this)
        model.getListNotes().observe(this, object : Observer<List<Note>>{
            override fun onChanged(t: List<Note>?) {
                if (t != null) {
                    adapter.setListNote(t)
                }
                rv_note.adapter = adapter
            }

        })
        adapter.setOnItemClickCallback(object : NoteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Note) {
                Intent(this@MainActivity, EditNoteActivity::class.java).also {
                    it.putExtra(EditNoteActivity.NOTE_ID, data.id)
                    startActivityForResult(it, UPDATE_NOTE_REQUEST_CODE)
                }
            }

        })

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                model.delete(adapter.getNoteAt(viewHolder.layoutPosition))
                Toast.makeText(this@MainActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(rv_note)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val note = data?.getStringExtra(CreateNoteActivity.NEW_NOTE)
            if(note != null){
                model.insert(note)
            }
            Toast.makeText(this, "Note Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
        }else if(requestCode == UPDATE_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val noteId = data?.getStringExtra(EditNoteActivity.NOTE_ID)
            val updatedNote = data?.getStringExtra(EditNoteActivity.UPDATED_NOTE)

            val note = noteId?.let {
                updatedNote?.let { it1 ->
                    Note(
                        it,
                        it1
                    )
                }
            }
            note?.let { model.update(it) }

            Toast.makeText(this, "Note Berhasil Diupdate", Toast.LENGTH_SHORT).show()
        }
    }

}