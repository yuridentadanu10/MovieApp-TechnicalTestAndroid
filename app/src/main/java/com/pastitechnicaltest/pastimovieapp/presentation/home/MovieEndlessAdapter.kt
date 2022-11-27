package com.pastitechnicaltest.pastimovieapp.presentation.home

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import com.pastitechnicaltest.pastimovieapp.core.utils.Constant
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pastitechnicaltest.pastimovieapp.R
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Movie
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.setImageFromUrl
import com.pastitechnicaltest.pastimovieapp.databinding.ItemCardHorizontalBinding
import com.pastitechnicaltest.pastimovieapp.databinding.ItemLoadingBinding

class MovieEndlessAdapter(private val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val movieList: MutableList<Movie?> = arrayListOf()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: List<Movie>) {
        movieList.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun clearData() {
        movieList.clear()
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        Handler(Looper.getMainLooper()).post {
            movieList.add(null)
            notifyItemInserted(movieList.size - 1)
        }
    }

    fun removeLoadingView() {
        if (movieList.size != 0) {
            movieList.removeAt(movieList.size - 1)
            notifyItemRemoved(movieList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_card_horizontal, parent, false)
            ItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            val binding = ItemLoadingBinding.bind(view)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.progressBar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                binding.progressBar.indeterminateDrawable.setColorFilter(
                    Color.WHITE,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movieList[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val binding = ItemCardHorizontalBinding.bind(holder.itemView)
            val movie = movieList[position]

            binding.apply {
                imageItem.setImageFromUrl(movie?.posterPath.toString())
                tvTitle.text = movie?.title
                tvRelaseDate.text = movie?.releaseDate
                tvOverview.text = movie?.overview
            }
            binding.root.setOnClickListener {
                onClick(movie!!)
            }
        }
    }
}