package com.tutorial.catatansalamah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback?= null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private lateinit var list: List<Note>

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(note: Note){
           with(itemView) {
               tv_note.text = note.note
               itemView.setOnClickListener {
                   onItemClickCallback?.onItemClicked(note)
               }
           }
        }
    }

    fun setListNote(list: List<Note>){
        this.list = list
        notifyDataSetChanged()
    }

    fun getNoteAt(position: Int): Note = list.get(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: Note)
    }

}