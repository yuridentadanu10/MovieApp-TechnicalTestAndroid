package com.pastitechnicaltest.pastimovieapp.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pastitechnicaltest.pastimovieapp.core.R
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Genre
import com.pastitechnicaltest.pastimovieapp.databinding.ItemGenreHorizontalBinding

class GenreHorizontalAdapter(private val onClick: (Genre) -> Unit
) : RecyclerView.Adapter<GenreHorizontalAdapter.GenreViewHolder>() {

    private val genreList: MutableList<Genre> = arrayListOf()
    private var selectedGenre: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view =
            ItemGenreHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        genreList[position].let {
            holder.bind(it)
        }
    }

    fun setItemSelected(genreId: Int) {
        selectedGenre = genreId
        notifyDataSetChanged()
    }

    fun clearData() {
        genreList.clear()
        notifyDataSetChanged()
    }

    fun addGenreList(movies: List<Genre>) {
        val previousItemSize = genreList.size
        genreList.addAll(movies)
        notifyItemRangeInserted(previousItemSize, movies.size)
    }

    override fun getItemCount(): Int = genreList.size

    inner class GenreViewHolder(private val binding: ItemGenreHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.apply {
                tvGenre.text = genre.name
                if (genre.id == selectedGenre){
                    rlGenre.setBackgroundResource(R.drawable.bg_selected_item)
                    tvGenre.setTextColor(Color.BLACK)
                } else{
                    rlGenre.setBackgroundResource(R.drawable.bg_unselected_item)
                    tvGenre.setTextColor(Color.WHITE)
                }
            }

            binding.root.setOnClickListener {
                onClick(genre)
                selectedGenre = genre.id
                notifyDataSetChanged()
            }
        }
    }

}