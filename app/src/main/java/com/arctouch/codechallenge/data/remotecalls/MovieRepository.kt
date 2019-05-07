package com.arctouch.codechallenge.data.remotecalls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arctouch.codechallenge.api.UpcomingMoviesApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.util.AppConstants.API_KEY
import com.arctouch.codechallenge.util.AppConstants.DEFAULT_LANGUAGE
import com.arctouch.codechallenge.util.AppConstants.DEFAULT_REGION
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository : BaseRemoteRepository() {

    private val api = retrofit.create(UpcomingMoviesApi::class.java)

    private val cache = LinkedHashMap<Long, List<Movie>>()

    companion object {
        private val TAG = this::class.simpleName
    }

    fun getMovies(page: Long): LiveData<List<Movie>> {
        val data = MutableLiveData<List<Movie>>()

        if (cache.containsKey(page)) {
            data.value = getMoviesFromPreviousLoad(page)
        } else {
            api.upcomingMovies(API_KEY, DEFAULT_LANGUAGE, page, DEFAULT_REGION)
                    .enqueue(object : Callback<UpcomingMoviesResponse> {
                        override fun onResponse(call: Call<UpcomingMoviesResponse>?
                                                , response: Response<UpcomingMoviesResponse>?) {
                            if (response?.isSuccessful!!) {
                                Log.d(TAG, "Genre call response successful: $response")

                                val results = response.body()?.results

                                cache[page] = results!!
                                data.value = getMoviesFromPreviousLoad(page)
                            }
                        }

                        override fun onFailure(call: Call<UpcomingMoviesResponse>?, t: Throwable?) {
                            Log.e(TAG, "Failure on genre call: $t")
                        }
                    })
        }

        return data
    }

    private fun getMoviesFromPreviousLoad(page:Long) :List<Movie>{
        val movies = mutableListOf<Movie>()
        cache.filter { it.key <= page }.forEach { movies.addAll(it.value) }
        return movies
    }
}