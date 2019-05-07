package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.data.remotecalls.MovieDetailRepository
import com.arctouch.codechallenge.model.Movie

class MovieDetailViewModel : ViewModel() {

    private val movieDetailRepository = MovieDetailRepository()
    private var liveData: LiveData<Movie>? = null

    fun getMovie(id: Long): LiveData<Movie> {
        if (liveData == null) {
            liveData = movieDetailRepository.getMovie(id)
        }
        return liveData!!
    }
}