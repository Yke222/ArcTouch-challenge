package com.arctouch.codechallenge.data.remotecalls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arctouch.codechallenge.api.DetailsApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.AppConstants.API_KEY
import com.arctouch.codechallenge.util.AppConstants.DEFAULT_LANGUAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailRepository:BaseRemoteRepository() {

    private val api = retrofit.create(DetailsApi::class.java)

    companion object {
        private val TAG = this::class.simpleName
    }

    fun getMovie(id: Long): LiveData<Movie> {
        val data = MutableLiveData<Movie>()

        api.movie(id, API_KEY, DEFAULT_LANGUAGE)
                .enqueue(object : Callback<Movie> {
                    override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                        if (response?.isSuccessful!!) {
                            Log.d(TAG, "Genre call response successful: $response")
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                        Log.e(TAG, "Failure on genre call: $t")
                    }
                })

        return data
    }
}