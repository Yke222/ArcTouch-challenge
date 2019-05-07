package com.arctouch.codechallenge.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.data.remotecalls.GenreRepository
import com.arctouch.codechallenge.data.remotecalls.MovieRepository
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie

class HomeViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private val genreRepository = GenreRepository()

    private val pageLiveData = MutableLiveData<Long>()
    private var moviesLiveData: LiveData<List<Movie>>? = null
    private var genresLiveData: LiveData<List<Genre>>? = null

    init {
        pageLiveData.value = 1L
    }

    fun getMovies(): LiveData<List<Movie>> {
        if (moviesLiveData == null) {
            moviesLiveData = Transformations.switchMap(pageLiveData) {
                movieRepository.getMovies(it!!)
            }
        }
        return moviesLiveData!!
    }

    fun getMoreMovies() {
        pageLiveData.value = pageLiveData.value?.plus(1)
    }

    fun getGenres(): LiveData<List<Genre>> {
        if (genresLiveData == null) {
            genresLiveData = genreRepository.getGenres()
        }

        return genresLiveData!!
    }
}