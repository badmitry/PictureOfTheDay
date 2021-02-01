package com.badmitry.pictureoftheday.ui.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badmitry.pictureoftheday.R
import com.badmitry.pictureoftheday.mvvm.model.entity.Note
import com.badmitry.pictureoftheday.mvvm.vm.NoteViewModel
import kotlinx.android.synthetic.main.item_rv_note.view.*

class NotesRVAdapter(private var notes: MutableList<Note>, private val viewModel: NoteViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter  {
    private val HEADER = 0
    private val NOTE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == HEADER) {
            HeaderViewHolder(
                inflater.inflate(
                    R.layout.item_rv_header,
                    parent,
                    false
                ) as View
            )
        } else {
            NoteViewHolder(
                inflater.inflate(
                    R.layout.item_rv_note,
                    parent,
                    false
                ) as View
            )
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notes.removeAt(fromPosition - 1).apply {
            notes.add(if (toPosition > fromPosition) toPosition - 2 else toPosition - 1, this)
        }
        val fromNote = Note(toPosition - 1, notes[toPosition - 1].note)
        val toNote = Note(fromPosition -1, notes[fromPosition - 1].note)
        viewModel.setChangeNotesToCache(toNote)
        viewModel.setChangeNotesToCache(fromNote)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        val currentPosition = position - 1
        notes.removeAt(currentPosition)
        viewModel.removeNoteFromCache(currentPosition)
        notifyItemRemoved(position)
    }


    override fun getItemCount() = notes.size + 1
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER else NOTE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != 0) {
            holder as NoteViewHolder
            holder.bind(position)
        }
    }

    fun appendItem() {
        notes.add(generateItem())
        notifyItemInserted(notes.size + 1)
    }

    private fun generateItem() = Note(notes.size + 1, "Новая заметка")

    inner class HeaderViewHolder(view: View) :
        RecyclerView.ViewHolder(view)

    inner class NoteViewHolder(view: View) :
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder  {
        fun bind(pos: Int) {
            val currentPosition = pos - 1
            itemView.note_tv.text = notes[currentPosition].note
            itemView.setOnClickListener {
                itemView.note_tv.visibility = View.GONE
                itemView.input_layout.visibility = View.VISIBLE
            }
            itemView.input_layout.setEndIconOnClickListener {
                val text: String = itemView.input_edit_text.text.toString()
                if (text != "") {
                    val note = Note(currentPosition, text)
                    itemView.note_tv.text = text
                    viewModel.setChangeNotesToCache(note)
                }
                itemView.note_tv.visibility = View.VISIBLE
                itemView.input_layout.visibility = View.GONE
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }
}