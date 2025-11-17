package com.example.phonecinemaapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.phonecinemaapp.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.UserRepository

class PerfilViewModelFactory(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            return PerfilViewModel(userRepository, reviewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}