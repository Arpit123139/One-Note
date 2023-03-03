package com.example.Note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.models.NoteRequest
import com.example.onenote.databinding.FragmentNoteBinding
import com.example.onenote.databinding.FragmentNoteEditBinding
import com.example.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteEditFragment : Fragment() {


    private lateinit var binding: FragmentNoteEditBinding
    private  val NoteViewModel by viewModels<NoteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentNoteEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val safeargs:NoteEditFragmentArgs by navArgs()
        val title:String?=safeargs.title
        val desc:String?=safeargs.description
        val id:String?=safeargs.id
        var editNote=true

        if(title==null && desc==null && id==null){
            binding.addEditText.text="Add Note"
            binding.btnDelete.isVisible=false
            editNote=false
        }else{
            binding.txtTitle.setText(title)
            binding.txtDescription.setText(desc)
        }

        binding.btnSubmit.setOnClickListener {

            if(!editNote){
                val title1=binding.txtTitle.text.toString()
                val desc1=binding.txtDescription.text.toString()

                createNote(NoteRequest(desc1,title1))
            }
            else{
                val title1=binding.txtTitle.text.toString()
                val desc1=binding.txtDescription.text.toString()
                updateNote(id!!,NoteRequest(desc1,title1))
            }
        }

        if(binding.btnDelete.isVisible){
            binding.btnDelete.setOnClickListener {
                deleteNote(id!!)
            }
        }



        super.onViewCreated(view, savedInstanceState)
    }

    fun createNote(noteRequest: NoteRequest){

        NoteViewModel.createNote(noteRequest)
        NoteViewModel.statusLive.observe(viewLifecycleOwner, Observer {

            when(it){

                is NetworkResult.Success->{
                    Toast.makeText(requireContext(),"New Note Created",Toast.LENGTH_SHORT).show()
                    val action=NoteEditFragmentDirections.actionNoteEditFragmentToNoteFragment()
                    findNavController().navigate(action)
                }
                is NetworkResult.Error -> {
//                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading -> {
                   // binding.progressBar.isVisible=true
                }
            }
        })
    }

    fun updateNote(id:String,noteRequest: NoteRequest){

        NoteViewModel.updateNode(id,noteRequest)

        NoteViewModel.statusLive.observe(viewLifecycleOwner, Observer {
            when(it){

                is NetworkResult.Success->{
                    Toast.makeText(requireContext(),"Note Got Updated",Toast.LENGTH_SHORT).show()
                    val action=NoteEditFragmentDirections.actionNoteEditFragmentToNoteFragment()
                    findNavController().navigate(action)
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        })
    }

    fun deleteNote(id:String){
        NoteViewModel.deleteNode(id)

        NoteViewModel.statusLive.observe(viewLifecycleOwner, Observer {
            when(it){

                is NetworkResult.Success->{
                    Toast.makeText(requireContext(),"Note Got Deleted",Toast.LENGTH_SHORT).show()
                    val action=NoteEditFragmentDirections.actionNoteEditFragmentToNoteFragment()
                    findNavController().navigate(action)
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        })

    }


}