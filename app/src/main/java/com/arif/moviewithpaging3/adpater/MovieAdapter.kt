package com.arif.moviewithpaging3.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arif.moviewithpaging3.Const.Companion.IMAGE_BASE_URL
import com.arif.moviewithpaging3.databinding.MovieItemBinding
import com.arif.moviewithpaging3.entity.Movie
import com.bumptech.glide.Glide

class MovieAdapter(var context: Context) : ListAdapter<com.arif.moviewithpaging3.entity.Result,MovieAdapter.movieViewHolder>(movieDiffCallBack()){

    lateinit var binding: MovieItemBinding

    class movieViewHolder(var binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    class movieDiffCallBack : DiffUtil.ItemCallback<com.arif.moviewithpaging3.entity.Result>(){
        override fun areItemsTheSame(oldItem: com.arif.moviewithpaging3.entity.Result, newItem: com.arif.moviewithpaging3.entity.Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: com.arif.moviewithpaging3.entity.Result, newItem: com.arif.moviewithpaging3.entity.Result): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieViewHolder {
        binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return movieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: movieViewHolder, position: Int) {
        getItem(position)?.let {
           Glide.with(context).load(IMAGE_BASE_URL+it.posterPath).into(holder.binding.imageViewSingleMovie)
        }
    }
}