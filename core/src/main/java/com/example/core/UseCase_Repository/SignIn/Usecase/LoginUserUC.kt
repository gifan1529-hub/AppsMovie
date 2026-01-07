package com.example.core.UseCase_Repository.SignIn.Usecase


import com.example.core.UseCase_Repository.SignUp.Domain.Repository.UserRepository
import javax.inject.Inject


sealed class LoginResult {
    data class Success(val email: String) : LoginResult()
    data class Error(val message: String) : LoginResult()
    data class Failure(val error: Exception) : LoginResult()
}

class LoginUserUC @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        if (email.isBlank()) {
            return LoginResult.Error("Email harus diisi")
        }
        if (password.isBlank()) {
            return LoginResult.Error("Password harus diisi")
        }

        return try {
            val user = repository.findUserByEmail(email)

            if (user != null && user.userPassword == password) {
                LoginResult.Success(user.email!!)
            } else {
                LoginResult.Error("Email atau password salah")
            }
        } catch (e: Exception) {
            LoginResult.Failure(e)
        }
    }
}
