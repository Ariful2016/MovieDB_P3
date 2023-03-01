package com.arif.moviewithpaging3.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.arif.moviewithpaging3.Const
import com.arif.moviewithpaging3.Const.Companion.getGenre
import com.arif.moviewithpaging3.databinding.SingleMovieSliderBinding
import com.arif.moviewithpaging3.utils.Helper
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter

class SlidingAdapter(val context: Context, val upcomingMovieList: List<com.arif.moviewithpaging3.entity.Result>) : SliderViewAdapter<SlidingAdapter.slidingViewHolder>(){
    lateinit var binding: SingleMovieSliderBinding



    override fun onBindViewHolder(holder: slidingViewHolder, position: Int) {
            val movie : com.arif.moviewithpaging3.entity.Result = upcomingMovieList[position]

            Glide.with(context).load(Const.IMAGE_BASE_URL +movie.posterPath).into(holder.binding.imageViewSingleMovieSlider)
            if(Helper.CompareDate(movie.releaseDate) == 1){
                holder.binding.titleBigSingleMovieSlider.text = "New Movies"
            }else if(Helper.CompareDate(movie.releaseDate) == 2){
                holder.binding.titleBigSingleMovieSlider.text = "Upcoming Movies"
            }
            if(movie.adult){
                holder.binding.adultCheckMovieSlider.text = "18+"
            }
            else{
                holder.binding.adultCheckMovieSlider.text = "13+"
            }
            holder.binding.titleSingleMovieSlider.text = movie.title
            holder.binding.dateSingleMovieSlider.text = movie.releaseDate
            holder.binding.genre1MovieSlider.text = getGenre(movie.genreIds[0])



            if(movie.genreIds.size > 1){
                holder.binding.genre2MovieSlider.text = getGenre(movie.genreIds[1])
                holder.binding.genre2LayoutMovieSlider.visibility = View.VISIBLE
            }else{
                holder.binding.genre2LayoutMovieSlider.visibility = View.INVISIBLE
            }


    }

    override fun getCount(): Int {
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup?): slidingViewHolder {
        if (parent != null) {
            binding = SingleMovieSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return slidingViewHolder(binding)
    }

    class slidingViewHolder(var binding: SingleMovieSliderBinding) : SliderViewAdapter.ViewHolder(binding.root)
}