package com.example.phonecinemaapp.ui.resenas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote

class ReviewViewModelFactory(
    private val reviewRepository: ReviewRepository,
    private val peliculasRepository: PeliculasRepositoryRemote
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(reviewRepository, peliculasRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
