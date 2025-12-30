package com.example.appsmovie.SignUp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.User
import kotlinx.coroutines.launch
class SignUpVM (private val database: AppDatabase) : ViewModel() {
    private val userDao = database.userDao()

    private val _registerStatus = MutableLiveData<RegistrationStatus>()
    val registerStatus: LiveData<RegistrationStatus> = _registerStatus

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val existingUser = userDao.findByEmail(user.email)

                if (existingUser == null) {
                    userDao.insertdata(user)
                    _registerStatus.postValue(RegistrationStatus.SUCCESS)
                } else {
                    _registerStatus.postValue(RegistrationStatus.EMAIL_EXISTS)
                }
            } catch (e: Exception) {
                _registerStatus.postValue(RegistrationStatus.FAILURE)
            }
        }
    }

    sealed class RegistrationStatus {
        object SUCCESS : RegistrationStatus()
        object EMAIL_EXISTS : RegistrationStatus()
        object FAILURE : RegistrationStatus()
    }

}