package com.badmitry.pictureoftheday.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.badmitry.pictureoftheday.R
import com.badmitry.pictureoftheday.databinding.NoteLayoutBinding
import com.badmitry.pictureoftheday.mvvm.vm.NoteViewModel
import com.badmitry.pictureoftheday.ui.App
import com.badmitry.pictureoftheday.ui.list.ItemTouchHelperCallback
import com.badmitry.pictureoftheday.ui.list.NotesRVAdapter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class NoteActivity : AppCompatActivity() {
    private var binding: NoteLayoutBinding? = null
    private var noteAdapter: NotesRVAdapter? = null

    @Inject
    lateinit var viewModel: NoteViewModel

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        setTheme(viewModel.theme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.note_layout)
        viewModel.getNoteListLiveData().observe(this, { value ->
            binding?.let { bind ->
                bind.noteRv.layoutManager = LinearLayoutManager(this)
                noteAdapter = NotesRVAdapter(value, viewModel)
                bind.noteRv.adapter = noteAdapter
                noteAdapter?.let {
                    ItemTouchHelper(ItemTouchHelperCallback(it))
                        .attachToRecyclerView(bind.noteRv)
                }
            }
        })
        viewModel.getNotesFromCache()
        binding?.let {
            it.fab.setOnClickListener {
                noteAdapter?.appendItem()
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigator = SupportAppNavigator(this, 0)
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}