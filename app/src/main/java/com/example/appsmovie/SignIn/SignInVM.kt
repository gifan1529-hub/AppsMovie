package com.example.appsmovie.SignIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInVM(private val database: AppDatabase) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, pass: String) {
        if (email.isEmpty()) {
            _loginResult.value = LoginResult.Error("Email harus di isi")
            return
        }
        if (pass.isEmpty()) {
            _loginResult.value = LoginResult.Error("Password harus di isi")
            return
        }


        viewModelScope.launch(Dispatchers.IO) {
            val user = database.userDao().findByEmail(email)

            withContext(Dispatchers.Main) {
                if (user == null) {
                    _loginResult.value = LoginResult.Error("User tidak ditemukan")
                } else {
                    if (user.userPassword == pass) {
                        _loginResult.value = LoginResult.Success(user)
                    } else {
                        _loginResult.value = LoginResult.Error("Password salah")
                    }
                }
            }
        }
    }
}

    sealed class LoginResult {
        data class Success(val user: User) : LoginResult()
        data class Error(val message: String) : LoginResult()
    }

