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
import com.example.onenote.databinding.FragmentSignupBinding
import com.example.utils.NetworkResult
import com.example.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val signupViewModel by viewModels<SignupViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignupBinding.inflate(layoutInflater)

        if(tokenManager.getToken()!=null){
            val action=SignupFragmentDirections.actionSignupFragmentToNoteFragment()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener{
            val action=SignupFragmentDirections.actionSignupFragmentToSignInFragment();
            findNavController().navigate(action)
        }

        binding.btnSignUp.setOnClickListener {
                val userRequest=getUserRequest()
                signupViewModel.signUpUser(userRequest);
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        signupViewModel.resLive.observe(viewLifecycleOwner,Observer{
            binding.progressBar.isVisible=false          // jaise hi response aagay progress baar hatado
            when(it){
                is NetworkResult.Success->{

                    tokenManager.saveToken(it.data!!.token)
                    val action=SignupFragmentDirections.actionSignupFragmentToNoteFragment()
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

//    fun validateInput(): Pair<Boolean, String> {
//          val  userRequest=getUserRequest()
//
//        return signupViewModel.validateCredential(userRequest.name,userRequest.email, userRequest.password)
//    }

    fun getUserRequest():userRequest{
        val email=binding.txtEmail.text.toString()
        val name=binding.txtUsername.text.toString()
        val password=binding.txtPassword.text.toString()

        return userRequest(email,name,password)
    }

}