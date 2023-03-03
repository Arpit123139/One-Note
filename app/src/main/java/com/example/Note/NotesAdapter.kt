package com.example.Note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.models.Note
import com.example.onenote.R

class NotesAdapter:ListAdapter<Note,NotesAdapter.noteViewHolder>(DiffCallback()) {

    inner class  noteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title1=view.findViewById<TextView>(R.id.title1)
        val desc=view.findViewById<TextView>(R.id.desc)

        fun bind(noteData:Note){
            with(noteData){
                desc.text=noteData.description
                title1.text=noteData.title

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteViewHolder {
        val itemLayout= LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false)
        return noteViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
class DiffCallback : DiffUtil.ItemCallback<Note>()
{
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem._id==newItem._id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem==newItem
    }
}