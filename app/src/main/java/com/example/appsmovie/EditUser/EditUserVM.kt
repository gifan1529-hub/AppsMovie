package com.example.appsmovie.EditUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.RoomDatabase.AppDatabase
import com.example.appsmovie.RoomDatabase.User
import kotlinx.coroutines.launch

data class EditUserUiState(
    val email: String = "",
    val passwordBaru: String = "",
    val isLoading: Boolean = false,
    val updateSuccess: Boolean = false,
    val error: String? = null
)

class EditUserVM (private val database: AppDatabase): ViewModel() {

    private val userDao = database.userDao()

    private val _uiState = MutableLiveData<EditUserUiState>()
    val uiState: LiveData<EditUserUiState> get() = _uiState

    private var currentUser : User? = null

    init {
        _uiState.value = EditUserUiState()
    }

    fun loadUser(userEmail: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isLoading = true)
            currentUser = userDao.get(userEmail)
            currentUser?.let { user ->
                _uiState.value = _uiState.value?.copy(
                    email = user.email,
                    passwordBaru = user.userPassword,
                    isLoading = false
                )
            } ?: run {
                _uiState.value = _uiState.value?.copy(
                    error = "User not found",
                    isLoading = false
                )
            }
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.value = _uiState.value?.copy(email = email)
    }

    fun onPasswordBaruChanged(passwordBaru: String) {
        _uiState.value = _uiState.value?.copy(passwordBaru = passwordBaru)
    }

    fun onUpdateClicked() {
        viewModelScope.launch {
            val currentState = _uiState.value ?: return@launch
            val originalUser = currentUser ?: return@launch

            val passwordUntukDisimpan = currentState.passwordBaru.ifBlank {
                originalUser.userPassword
            }

            val updatedUser = originalUser.copy(
                email = currentState.email,
                userPassword = passwordUntukDisimpan
            )
            _uiState.value = currentState.copy(isLoading = true)

            userDao.updateUser(updatedUser)

            _uiState.value = currentState.copy(
                updateSuccess = true,
                isLoading = false
            )
        }
    }

}