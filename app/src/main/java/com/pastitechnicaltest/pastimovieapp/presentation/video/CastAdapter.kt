package com.pastitechnicaltest.pastimovieapp.presentation.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pastitechnicaltest.pastimovieapp.core.domain.model.Cast
import com.pastitechnicaltest.pastimovieapp.core.utils.ext.setImageFromUrl
import com.pastitechnicaltest.pastimovieapp.databinding.ItemCastBinding

class CastAdapter(private val castList: List<Cast>): RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        castList[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = castList.size

    inner class CastViewHolder(private val binding: ItemCastBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.apply {
                cast.profilePath?.let { imgCast.setImageFromUrl(it) }
                tvCastCharacter.text = cast.character
                tvCastName.text = cast.name
            }
        }
    }

}