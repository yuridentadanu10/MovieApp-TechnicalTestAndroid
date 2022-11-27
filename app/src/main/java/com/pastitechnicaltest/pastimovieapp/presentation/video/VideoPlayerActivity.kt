package com.pastitechnicaltest.pastimovieapp.presentation.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pastitechnicaltest.pastimovieapp.core.data.lib.ApiResponse
import com.pastitechnicaltest.pastimovieapp.core.utils.orZero
import com.pastitechnicaltest.pastimovieapp.databinding.ActivityVideoPlayerBinding
import com.pastitechnicaltest.pastimovieapp.utils.BundleKeys
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.gone
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.show
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.showToast
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var _activityVideoPlayerBinding: ActivityVideoPlayerBinding
    private val binding get() = _activityVideoPlayerBinding
    private val videoViewModel: VideoPlayerViewModel by viewModel()
    private lateinit var castAdapter: CastAdapter

    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityVideoPlayerBinding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(_activityVideoPlayerBinding.root)
        initIntent()
        initUI()
        initObserver()

    }

    private fun initIntent() {
        movieId = intent.getIntExtra(BundleKeys.MOVIE_ID, 0)
    }

    private fun initUI() {
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initObserver() {
        videoViewModel.getMovieCasts(movieId.orZero()).observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    isLoading(true)
                }
                is ApiResponse.Success -> {
                    castAdapter = CastAdapter(result.data)
                    binding.rvCast.apply {
                        adapter = castAdapter
                        layoutManager = LinearLayoutManager(this@VideoPlayerActivity)
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

        videoViewModel.getVideoTrailer(movieId!!).observe(this) { result ->
            when (result) {
                is ApiResponse.Loading -> {
                    //isLoading(true)
                }
                is ApiResponse.Success -> {
                    binding.apply {
                        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(result.data.key, 0f)
                            }
                        })
                    }
                    //isLoading(false)
                }
                is ApiResponse.Empty -> {
                   // isLoading(false)
                    showToast("Movie list is empty...")
                }
                is ApiResponse.Error -> {
                   // isLoading(false)
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
            binding.rvCast.show()
            binding.shimmerLoading.gone()
        }
    }

    companion object {

        fun startActivity(context: Context, movieId: Int) {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(BundleKeys.MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }
}
