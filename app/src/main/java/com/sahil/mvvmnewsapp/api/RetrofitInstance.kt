package com.sahil.mvvmnewsapp.api

import com.sahil.mvvmnewsapp.util.constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {

        // the lazy means that we only initialize this whole code once
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor() // for log responces of retrofit which is very useful for debugging
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

         // this will actual api object that we will actul abe to use later to make our actual network request
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}
