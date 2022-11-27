package com.pastitechnicaltest.pastimovieapp.presentation.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.databinding.ActivitySearchBinding
import com.pastitechnicaltest.pastimovieapp.presentation.detail.DetailActivity
import com.pastitechnicaltest.pastimovieapp.presentation.home.MovieEndlessAdapter
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.gone
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.hide
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.show
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var _activitySearchBinding: ActivitySearchBinding
    private val binding get() = _activitySearchBinding

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(_activitySearchBinding.root)

        initUI()
        initAction()
    }

    private fun initUI() {
        binding.shimmerLoading.hide()
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initAction() {
        binding.svUser.apply {
            isActivated = true
            onActionViewExpanded()
            isIconified = false
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    getSearchUser(query.toString())
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun getSearchUser(query: String) {
        searchViewModel.getMoviesByQuery(query).observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    binding.emptyState.hide()
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    val movieAdapter = MovieEndlessAdapter(
                        onClick = {
                            DetailActivity.startActivity(this, it)
                        }
                    )
                    binding.rvMovie.apply {
                        movieAdapter.addData(result.data)
                        adapter = movieAdapter
                        layoutManager = LinearLayoutManager(this@SearchActivity)
                    }
                    binding.emptyState.hide()
                    isLoading(false)
                }
                is ApiResponse.Empty -> {
                    isLoading(false)
                    binding.emptyState.show()
                    binding.rvMovie.hide()
                    showToast("Movie list is empty...")
                }
                is ApiResponse.Error -> {
                    binding.emptyState.show()
                    isLoading(false)
                    showToast(result.errorMessage)
                }
            }
        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                shimmerLoading.show()
                shimmerLoading.startShimmer()
            }
        } else {
            binding.shimmerLoading.gone()
            binding.rvMovie.show()
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }

}