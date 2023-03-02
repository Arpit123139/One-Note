package com.example.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.models.userRequest
import com.example.onenote.R
import com.example.onenote.databinding.FragmentSignInBinding
import com.example.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val signupViewModel by viewModels<SignupViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignInBinding.inflate(layoutInflater)

        binding.btnSignUp.setOnClickListener{
            val action=SignInFragmentDirections.actionSignInFragmentToSignupFragment();
            findNavController().navigate(action)
        }
        binding.btnLogin.setOnClickListener {
           signupViewModel.signInUser(userRequest("one@gmail.com","","1"))
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        signupViewModel.resLive.observe(viewLifecycleOwner, Observer{
            binding.progressBar.isVisible=false          // jaise hi response aagay progress baar hatado
            when(it){
                is NetworkResult.Success->{
                    val action=SignInFragmentDirections.actionSignInFragmentToNoteFragment();
                    findNavController().navigate(action)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible=true
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }


}