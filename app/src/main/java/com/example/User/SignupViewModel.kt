package com.example.User

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.models.User
import com.example.models.UserResponse
import com.example.models.userRequest
import com.example.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

//    var _userResponse=MutableLiveData<UserResponse>()
//    val resLive:LiveData<UserResponse>
//        get() = _userResponse

    //We are handling with the networking class so the LiveData type will be NetworkResult<UserResponse>
    var _userResponse=MutableLiveData<NetworkResult<UserResponse>>()
    val resLive:LiveData<NetworkResult<UserResponse>>
        get() = _userResponse

    fun signUpUser(userRequest: userRequest){

        viewModelScope.launch {

            _userResponse.value=NetworkResult.Loading()

            val userResponse=userRepository.signUp(userRequest)
            handleResponse(userResponse)
           // Log.d("SignUp View Model", userResponse.toString())
        }
    }

    private fun handleResponse(userResponse: Response<UserResponse>) {
        if (userResponse.isSuccessful && userResponse.body() != null) {
            _userResponse.value = NetworkResult.Success(userResponse.body()!!)
        } else if (userResponse.errorBody() != null) {        //ERROR BODY HAS A JSON
            //parsing the JSON
            val errorObj = JSONObject(userResponse.errorBody()!!.charStream().readText())
            _userResponse.value = NetworkResult.Error(errorObj.getString("message"))
        } else {
            _userResponse.value = NetworkResult.Error("Something Went Wrong")
        }
    }

    fun signInUser(userRequest: userRequest){

        viewModelScope.launch {

            _userResponse.value=NetworkResult.Loading()
            val userResponse=userRepository.signIn(userRequest)

            handleResponse(userResponse)
        }
    }

    fun validateCredential(name:String,email:String,password:String):Pair<Boolean,String>{
        var result=Pair(true,"")

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result=Pair(false,"Please Provide a valid Email Address")
        }
        return result
    }
}