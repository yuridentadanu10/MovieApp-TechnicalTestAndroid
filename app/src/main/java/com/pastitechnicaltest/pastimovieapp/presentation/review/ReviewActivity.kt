package com.pastitechnicaltest.pastimovieapp.presentation.review

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.utils.Constant
import com.pastitechnicaltest.pastimovieapp.core.utils.orZero
import com.pastitechnicaltest.pastimovieapp.core.view.OnLoadMoreListener
import com.pastitechnicaltest.pastimovieapp.core.view.RecyclerViewLoadMoreScroll
import com.pastitechnicaltest.pastimovieapp.databinding.ActivityReviewBinding
import com.pastitechnicaltest.pastimovieapp.utils.BundleKeys
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.gone
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.show
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewActivity : AppCompatActivity() {
    private lateinit var _activityReviewBinding: ActivityReviewBinding
    private val binding get() = _activityReviewBinding
    private val reviewViewModel: ReviewViewModel by viewModel()
    private var movieId: Int? = null

    lateinit var reviewAdapter: ReviewEndlessAdapter
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var scrollListener: RecyclerViewLoadMoreScroll

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityReviewBinding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(_activityReviewBinding.root)

        initIntent()
        initUI()
        initObserver()
    }

    private fun initIntent() {
        movieId = intent.getIntExtra(BundleKeys.MOVIE_ID, 0)
    }

    private fun initObserver() {
        getReviewMovie()
    }

    private fun initUI() {
        setupAdapter()
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupAdapter() {
        binding.apply {
            reviewAdapter = ReviewEndlessAdapter()
            rvReviewMovie.adapter = reviewAdapter
            mLayoutManager = LinearLayoutManager(this@ReviewActivity)
            rvReviewMovie.layoutManager = mLayoutManager
            rvReviewMovie.setHasFixedSize(true)
            scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
            scrollListener.setOnLoadMoreListener(object :
                OnLoadMoreListener {
                override fun onLoadMore() {
                    if (reviewViewModel.hasMore) {
                        reviewViewModel.addPage()
                        getReviewMovie(
                            reviewViewModel.page
                        )
                    }
                }
            })
            rvReviewMovie.addOnScrollListener(scrollListener)
        }
    }

    private fun getReviewMovie(page: Int = 1) {
        reviewViewModel.getReviewMovie(movieId.orZero(), page).observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    if (page == 1) {
                        isLoading(true)
                    } else {
                        reviewAdapter.addLoadingView()
                    }
                }
                is ApiResponse.Success -> {
                    if (page == 1) {
                        reviewAdapter.clearData()
                        reviewAdapter.addData(result.data.results)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            reviewAdapter.removeLoadingView()
                            reviewAdapter.addData(result.data.results)
                            scrollListener.setLoaded()
                        }, Constant.DELAY_TWO_MILLISECOND)
                    }
                    reviewViewModel.hasMore = result.data.page != result.data.totalPages
                    isLoading(false)
                }
                is ApiResponse.Empty -> {
                    binding.emptyState.show()
                    isLoading(false)
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
            binding.rvReviewMovie.show()
            binding.shimmerLoading.gone()
        }
    }

    companion object {

        fun startActivity(context: Context, movieId: Int) {
            val intent = Intent(context, ReviewActivity::class.java)
            intent.putExtra(BundleKeys.MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }

}