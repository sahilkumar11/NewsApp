package com.sahil.mvvmnewsapp.api

import com.sahil.mvvmnewsapp.model.NewsResponse
import com.sahil.mvvmnewsapp.util.constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/**this interface will be used to define our single requests that we can exacute from code
and the first functon of this interface will used to get all the breaking news from our api.

 When ever we make HTTP request we need to specify the type of request.
 */
interface NewsAPI {

    // get annotation because we need to get data from api
    // in paranthesis we need to specify the url that we want to get data from(url after base url
    // the network call function should be done asyncrousily , so we use coroutines for that we have use
    // suspended function
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        PageNumber:Int =1,
        @Query("apiKey")
        apiKey : String = API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery:String,
        @Query("page")
        PageNumber: Int =1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>

    // now we need to retrofit singleton class that enables us to make request in everywhere in
   // code
}