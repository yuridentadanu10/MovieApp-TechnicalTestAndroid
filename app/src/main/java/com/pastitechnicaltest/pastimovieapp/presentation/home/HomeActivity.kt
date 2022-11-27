package com.pastitechnicaltest.pastimovieapp.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pastitechnicaltest.pastimovieapp.R
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.utils.Constant
import com.pastitechnicaltest.pastimovieapp.core.view.OnLoadMoreListener
import com.pastitechnicaltest.pastimovieapp.core.view.RecyclerViewLoadMoreScroll
import com.pastitechnicaltest.pastimovieapp.databinding.ActivityHomeBinding
import com.pastitechnicaltest.pastimovieapp.presentation.detail.DetailActivity
import com.pastitechnicaltest.pastimovieapp.presentation.search.SearchActivity
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.gone
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.show
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var _activityHomeBinding: ActivityHomeBinding
    private val binding get() = _activityHomeBinding
    private val mainViewModel: HomeViewModel by viewModel()

    lateinit var movieAdapter: MovieEndlessAdapter
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(_activityHomeBinding.root)

        initUI()
        initObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSearch -> {
                SearchActivity.startActivity(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUI() {
        setupToolbar()
        setupAdapter()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun setupAdapter() {
        binding.apply {
            movieAdapter = MovieEndlessAdapter {
                DetailActivity.startActivity(this@HomeActivity, it)
            }
            rvDiscoverMovie.adapter = movieAdapter
            mLayoutManager = LinearLayoutManager(this@HomeActivity)
            rvDiscoverMovie.layoutManager = mLayoutManager
            rvDiscoverMovie.setHasFixedSize(true)
            scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
            scrollListener.setOnLoadMoreListener(object :
                OnLoadMoreListener {
                override fun onLoadMore() {
                    if (mainViewModel.hasMore) {
                        mainViewModel.addPage()
                        getDiscoverMovie(
                            mainViewModel.choosenGenre.value.orEmpty(),
                            mainViewModel.page
                        )
                    }
                }
            })
            rvDiscoverMovie.addOnScrollListener(scrollListener)
        }
    }

    private fun initObserver() {
        getDiscoverMovie()
        mainViewModel.getGenre().observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    showToast("Genre list is empty...")
                }
                is ApiResponse.Error -> {
                    isLoading(false)
                    showToast(result.errorMessage)
                }
                is ApiResponse.Success -> {
                    val genreHorizontalAdapter = GenreHorizontalAdapter(
                        onClick = {
                            mainViewModel.choosenGenre.value = it.id.toString()
                        }
                    )

                    genreHorizontalAdapter.addGenreList(result.data)
                    binding.rvGenreHorizontal.apply {
                        adapter = genreHorizontalAdapter
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(
                            this@HomeActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    }
                }
            }
        }

        mainViewModel.choosenGenre.observe(this) {
            mainViewModel.resetPage()
            getDiscoverMovie(it)
        }
    }

    private fun getDiscoverMovie(genre: String = "", page: Int = 1) {
        mainViewModel.getDiscoverMovie(genre, page).observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    if (page == 1) {
                        isLoading(true)
                    } else {
                        movieAdapter.addLoadingView()
                    }
                }
                is ApiResponse.Success -> {
                    if (page == 1) {
                        movieAdapter.clearData()
                        movieAdapter.addData(result.data.results)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                movieAdapter.removeLoadingView()
                                movieAdapter.addData(result.data.results)
                                scrollListener.setLoaded()
                            }, Constant.DELAY_TWO_MILLISECOND
                        )
                    }
                    mainViewModel.hasMore = result.data.page != result.data.totalPages
                    isLoading(false)
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    showToast("Movie list is empty...")
                }
                is ApiResponse.Error -> {
                    isLoading(false)
                    showToast(result.errorMessage)
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.shimmerLoading.apply {
                show()
                startShimmer()
            }
        } else {
            binding.linearLayoutMainHomeView.show()
            binding.shimmerLoading.gone()
        }
    }

}