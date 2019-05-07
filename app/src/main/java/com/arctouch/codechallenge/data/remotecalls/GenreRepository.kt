package com.arctouch.codechallenge.data.remotecalls

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arctouch.codechallenge.api.GenreApi
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.GenreResponse
import com.arctouch.codechallenge.util.AppConstants.API_KEY
import com.arctouch.codechallenge.util.AppConstants.DEFAULT_LANGUAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreRepository : BaseRemoteRepository() {

    private val api = retrofit.create(GenreApi::class.java)

    companion object {
        private val TAG = this::class.simpleName
    }

    fun getGenres(): LiveData<List<Genre>> {
        val data = MutableLiveData<List<Genre>>()

        api.genres(API_KEY, DEFAULT_LANGUAGE)
                .enqueue(object : Callback<GenreResponse> {
                    override fun onResponse(call: Call<GenreResponse>?,
                                            response: Response<GenreResponse>?) {
                        if (response?.isSuccessful!!) {
                            Log.d(TAG, "Genre call response successful: $response")
                            val genres = response.body()?.genres
                            data.value = genres
                        }
                    }

                    override fun onFailure(call: Call<GenreResponse>?, t: Throwable?) {
                        Log.e(TAG, "Failure on genre call: $t")
                    }
                })

        return data
    }
}