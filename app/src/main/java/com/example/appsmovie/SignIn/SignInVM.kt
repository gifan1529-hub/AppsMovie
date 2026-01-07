package com.example.appsmovie.SignIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Database.RoomDatabase.User
import com.example.core.UseCase_Repository.SignIn.Usecase.LoginResult
import com.example.core.UseCase_Repository.SignIn.Usecase.LoginUserUC
import com.example.core.UseCase_Repository.SignUp.Domain.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val loginUserUC: LoginUserUC,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            val result = loginUserUC(email, pass)
            _loginResult.postValue(result)
        }
    }
    suspend fun getUserByEmail(email: String): User? {
        return userRepository.findUserByEmail(email)
    }
}