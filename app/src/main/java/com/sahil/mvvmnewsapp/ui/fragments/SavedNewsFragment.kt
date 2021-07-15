package com.sahil.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sahil.mvvmnewsapp.R
import com.sahil.mvvmnewsapp.adapter.NewsAdapter
import com.sahil.mvvmnewsapp.ui.NewsActivity
import com.sahil.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel:NewsViewModel
    lateinit var newsAdapter:NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel  = (activity as NewsActivity).viewModel
        setUpRecyclerview()

        newsAdapter.setOnItemClickListener {
            val bundle  = Bundle().apply{
                putSerializable("article", it)
            }
            findNavController().navigate( R.id.action_savedNewsFragment_to_articleFragment, bundle)
        }

        // callback for item touch helper
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition // getting the position of swiped article
                val article = newsAdapter.differ.currentList[position]  // getting the article
                viewModel.deleteArticle(article) // and callting delete function on padding that article
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.savedArticle(article)   // for undo the delete article
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews) // attaching to recyclerview
        }

        // for observing changes in list
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }


    // function to set up recyclerview
    private fun setUpRecyclerview(){
        newsAdapter = NewsAdapter()

        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}