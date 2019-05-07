package com.arctouch.codechallenge.view.home

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.ActivityMovieDetailBinding
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.AppConstants
import com.arctouch.codechallenge.view.ScrollListener
import com.arctouch.codechallenge.view.moviedetail.MovieDetailActivity
import com.arctouch.codechallenge.viewmodel.HomeViewModel
import com.arctouch.codechallenge.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeAdapter.HomeListener, SearchView.OnQueryTextListener,
        AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewAdapter: HomeAdapter
    private lateinit var movieDialog: Dialog
    private lateinit var binding: ActivityMovieDetailBinding

    private var filter: String? = null
    private var filterGenre: String? = null

    private val scrollListener = object : ScrollListener() {
        override fun loadMore() {
            viewAdapter.setData(null)
            viewModel.getMoreMovies()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupRecyclerView()
        setupViewModel()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        viewAdapter.filter(filter, filterGenre)
    }

    override fun onDestroy() {
        recyclerView.removeOnScrollListener(scrollListener)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        // Set the filter on search view when rotate the device
        if (filter != null && filter?.isNotEmpty()!!) {
            searchView.setQuery(filter, false)
            searchView.isIconified = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filter = newText
        viewAdapter.filter(newText)
        return true
    }

    private fun setupRecyclerView() {
        viewAdapter = HomeAdapter(this)
        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this@HomeActivity,
                    LinearLayoutManager.VERTICAL))
            adapter = viewAdapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getGenres().observe(this, Observer {
            observeMovies()
        })
    }

    private fun showDialog(id: Long) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.activity_movie_detail, null, false)

        val viewModel = ViewModelProviders.of(this)
                .get(MovieDetailViewModel::class.java)

        viewModel.getMovie(id).observe(this, Observer {
            binding.movie = it
        })

        movieDialog = Dialog(this)

        movieDialog.setContentView(binding.root)
        movieDialog.show()
    }

    private fun observeMovies() {
        viewModel.getMovies().observe(this, Observer {
            progressBar.visibility = View.GONE
            viewAdapter.setData(it!!)
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem = parent?.getItemAtPosition(position).toString()
        filterGenre = selectedItem
        viewAdapter.filterGenre(selectedItem)

    }

    override fun onClick(movie: Movie) {
        showDialog(movie.id)
    }

    override fun showEmptyFilter() {
        emptyFilterText.visibility = View.VISIBLE
    }

    override fun removeEmptyFilter() {
        emptyFilterText.visibility = View.GONE
    }
}
