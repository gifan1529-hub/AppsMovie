package com.example.appsmovie.SignUp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Database.RoomDatabase.User
import com.example.core.UseCase_Repository.SignUp.Domain.Usecase.RegisterUserUC
import com.example.core.UseCase_Repository.SignUp.Domain.Usecase.RegistrationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor (
    private val repository: RegisterUserUC
) : ViewModel() {

    private val _registerStatus = MutableLiveData<SignUpStatus>()
    val registerStatus: LiveData<SignUpStatus> = _registerStatus

    fun registerUser(user: User) {
        viewModelScope.launch {
            when(repository(user)) {
                is RegistrationStatus.Success -> _registerStatus.postValue(SignUpStatus.SUCCESS)
                is RegistrationStatus.EmailExists -> _registerStatus.postValue(SignUpStatus.EMAIL_EXISTS)
                is RegistrationStatus.Failure -> _registerStatus.postValue(SignUpStatus.FAILURE)
            }
        }
    }

    sealed class SignUpStatus {
        object SUCCESS : SignUpStatus()
        object EMAIL_EXISTS : SignUpStatus()
        object FAILURE : SignUpStatus()
    }

}