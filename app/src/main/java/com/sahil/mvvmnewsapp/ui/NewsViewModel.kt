package com.sahil.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sahil.mvvmnewsapp.model.Article
import com.sahil.mvvmnewsapp.model.NewsResponse
import com.sahil.mvvmnewsapp.repository.NewsRepository
import com.sahil.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
    val breakingNews:MutableLiveData<Resource<NewsResponse>>  =  MutableLiveData()
     var breakingNewsPage = 1 // we initialise here because if we initialise in fragment it restores again to 1

    //for pagination
    var breakingNewsResponse:NewsResponse?= null

    // for search news segment
    val searchNews:MutableLiveData<Resource<NewsResponse>>  =  MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse:NewsResponse?= null

    // for making network request
    init {
        getBreakingNews("in")
    }

    // since get Breaking news is a suspend func it should also be suspend func from newsREpo , but we don't want to start suspend function in fragments
    //So we need to start coroutine in this function ,ANd for viewModel we can do by viewModel Scope
    // ViewModelScope will make sure that this coroutine function will stay alive as long as our viewModel is alive

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        //creating actual response and storing in response val
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        //for handle response
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery:String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    //
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++;
                if(breakingNewsResponse==null){
                    breakingNewsResponse = resultResponse
                }
                else{
                    val oldAritcles = breakingNewsResponse?.articles
                    val newArticles = resultResponse?.articles
                    oldAritcles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
   // this function handles search response
    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
       if(response.isSuccessful) {
           response.body()?.let { resultResponse ->
               searchNewsPage++;
               if(searchNewsResponse==null){
                   searchNewsResponse = resultResponse
               }
               else{
                   val oldAritcles = searchNewsResponse?.articles
                   val newArticles = resultResponse?.articles
                   oldAritcles?.addAll(newArticles)
               }
               return Resource.Success(searchNewsResponse?:resultResponse)
           }
       }
       return Resource.Error(response.message())
    }

    fun savedArticle(article : Article)  = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article:Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}