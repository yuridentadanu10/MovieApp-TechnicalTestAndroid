package com.pastitechnicaltest.pastimovieapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.pastitechnicaltest.pastimovieapp.R
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.utils.orZero
import com.pastitechnicaltest.pastimovieapp.databinding.ActivityDetailBinding
import com.pastitechnicaltest.pastimovieapp.presentation.review.ReviewActivity
import com.pastitechnicaltest.pastimovieapp.presentation.video.VideoPlayerActivity
import com.pastitechnicaltest.pastimovieapp.utils.BundleKeys.MOVIE_BUNDLE
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.gone
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.setImageFromUrl
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.show
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var _detailActivity: ActivityDetailBinding
    private val binding get() = _detailActivity

    private var movie: Movie? = null

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _detailActivity = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(_detailActivity.root)

        initIntent()
        initUI()
        initObservers()
    }

    private fun initIntent() {
        movie = intent.getParcelableExtra(MOVIE_BUNDLE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        setupToolbar()
        binding.textViewOpenReview.setOnClickListener {
            movie?.let {
                ReviewActivity.startActivity(this, it.id)
            }
        }
    }

    private fun initObservers() {
        detailViewModel.getMovieDetail(movie?.id.orZero()).observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    binding.apply {
                        textViewTitle.text = movie?.title.toString()
                        textViewVoteAverage.text = movie?.voteAverage.toString()
                        textViewDetailPopularity.text = movie?.voteAverage.toString()
                        textViewDetailRelease.text = movie?.releaseDate.toString()
                        imageViewFilm.show()
                        imageViewFilm.setImageFromUrl(movie?.posterPath.orEmpty())
                        textViewDetailGenre.text = result.data.genres.joinToString()
                        textViewRuntime.text = result.data.runtime.toString()
                        textViewDetailOverview.text = movie?.overview

                        textViewWatchTrailer.setOnClickListener {
                            VideoPlayerActivity.startActivity(this@DetailActivity, movie?.id ?: 0)
                        }
                    }
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


    private fun setupToolbar() {
        binding.apply {
            setSupportActionBar(this.toolBar)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            collapsingToolBar.title = movie?.title
            collapsingToolBar.setExpandedTitleColor(Color.WHITE)
            collapsingToolBar.setCollapsedTitleTextColor(Color.WHITE)
            toolBar.setNavigationOnClickListener {
                finish()
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
            binding.detailView.show()
            binding.shimmerLoading.gone()
        }
    }

    companion object {
        fun startActivity(context: Context, movie: Movie) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_BUNDLE, movie)
            context.startActivity(intent)
        }
    }
}