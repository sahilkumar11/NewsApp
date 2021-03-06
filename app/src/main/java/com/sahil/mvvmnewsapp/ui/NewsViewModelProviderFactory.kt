package com.sahil.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sahil.mvvmnewsapp.repository.NewsRepository

class NewsViewModelProviderFactory(
    val newsRepository: NewsRepository // paasing as constructor
):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T // returning news VIew Model
    }
}