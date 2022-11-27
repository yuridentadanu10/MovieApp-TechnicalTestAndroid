package com.pastitechnicaltest.pastimovieapp.presentation.review

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pastitechnicaltest.pastimovieapp.R
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Review
import com.pastitechnicaltest.pastimovieapp.core.utils.Constant
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.setImageFromUrl
import com.pastitechnicaltest.pastimovieapp.databinding.ItemLoadingBinding
import com.pastitechnicaltest.pastimovieapp.databinding.ItemReviewMovieBinding

class ReviewEndlessAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val reviewList: MutableList<Review?> = arrayListOf()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: List<Review>) {
        reviewList.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun clearData() {
        reviewList.clear()
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        Handler(Looper.getMainLooper()).post {
            reviewList.add(null)
            notifyItemInserted(reviewList.size - 1)
        }
    }

    fun removeLoadingView() {
        if (reviewList.size != 0) {
            reviewList.removeAt(reviewList.size - 1)
            notifyItemRemoved(reviewList.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_review_movie, parent, false)
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
        return if (reviewList[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val binding = ItemReviewMovieBinding.bind(holder.itemView)
            val review = reviewList[position]

            binding.apply {
                review?.let {
                    imageViewReviewer.setImageFromUrl(review.author_details.avatarPath)
                    tvCastName.text = review.author_details.username
                    tvReviewMovie.text = review.content
                    ratingBar.rating = review.author_details.rating.toFloat() / 2
                }
            }
        }
    }
}