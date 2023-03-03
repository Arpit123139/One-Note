package com.example.Note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.User.SignInFragmentDirections
import com.example.api.NotesApi
import com.example.models.Note
import com.example.models.NoteResponse
import com.example.onenote.R
import com.example.onenote.databinding.FragmentNoteBinding
import com.example.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : Fragment() {

    @Inject
    lateinit var  notesApi: NotesApi

    private lateinit var binding:FragmentNoteBinding

    private lateinit var adapter: NotesAdapter

    private  val NoteViewModel by viewModels<NoteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        CoroutineScope(Dispatchers.IO).launch {
//            val response =notesApi.getNote()
//            Log.d("Arpit Note Fragment", response.body().toString())
//        }
        binding=FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter= NotesAdapter()
         NoteViewModel.getNote()
        NoteViewModel.notelive.observe(viewLifecycleOwner, Observer {

            when(it){
                is NetworkResult.Success->{
                    Log.d("NoteFragment", it.data.toString())
                    SetDataInAdapter(it.data!!.note,view)
                }
                is NetworkResult.Error -> {
//                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading -> {
//                    binding.progressBar.isVisible=true
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    fun  SetDataInAdapter(noteList:List<Note>, view: View){
        val layotManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val myList=view.findViewById<RecyclerView>(R.id.note_list)
        myList.layoutManager=layotManager
        myList.adapter=adapter

        (myList.adapter as NotesAdapter).submitList(noteList)
    }


}