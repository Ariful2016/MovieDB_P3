package com.arif.moviewithpaging3.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arif.moviewithpaging3.R
import com.arif.moviewithpaging3.adpater.MovieAdapter
import com.arif.moviewithpaging3.adpater.SlidingAdapter
import com.arif.moviewithpaging3.databinding.FragmentHomeBinding
import com.arif.moviewithpaging3.viewModel.MovieViewModel
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Wave
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding

    lateinit var movieViewModel: MovieViewModel
    lateinit var popularMovieAdapter: MovieAdapter
    lateinit var topRatedMovieAdapter: MovieAdapter

    lateinit var movieSliderAdapter: SlidingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getPopularMovies("",1)
        movieViewModel.getUpcomingMovies()
        movieViewModel.getTopRatedMovies("",1)


        popularMovieAdapter = MovieAdapter(requireActivity())
        topRatedMovieAdapter = MovieAdapter(requireActivity())

        observeViewModel()


        val doubleBounce: Sprite = Wave()
        binding.spinKitMovieFrag.setIndeterminateDrawable(doubleBounce)
        hideLayout()


        showLayout()

        return binding.root
    }

    private fun hideLayout() {

        binding.imageSliderMovieFragment.visibility = View.GONE
        binding.popularMovieLayoutMovieFrag.visibility = View.GONE
        binding.topRatedMovieLayoutMovieFrag.visibility = View.GONE
        binding.spinKitMovieFrag.visibility = View.VISIBLE
    }

    private fun showLayout(){

        binding.spinKitMovieFrag.visibility = View.GONE
        binding.imageSliderMovieFragment.visibility = View.VISIBLE
        binding.popularMovieLayoutMovieFrag.visibility = View.VISIBLE
        binding.topRatedMovieLayoutMovieFrag.visibility = View.VISIBLE
    }

    private fun observeViewModel() {
        movieViewModel.upcomingMovies.observe(requireActivity()){ upcoming ->
            upcoming?.let {
                Log.d("TAG", "upcoming movie: ${it.size}")
                movieSliderAdapter = SlidingAdapter(requireActivity(),it)
                binding.imageSliderMovieFragment.setSliderAdapter(movieSliderAdapter)
                binding.imageSliderMovieFragment.setIndicatorAnimation(IndicatorAnimationType.WORM)
                binding.imageSliderMovieFragment.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
                binding.imageSliderMovieFragment.startAutoCycle()
            }
        }
        movieViewModel.popularMovies.observe(requireActivity()) { popular ->
            popular?.let {
                Log.d("TAG", "popular: ${it.get(0).id}")
                popularMovieAdapter.submitList(it)
                binding.popularRecycler.apply {
                    adapter = popularMovieAdapter
                    setHasFixedSize(true)
                }

            }
        }

        movieViewModel.topRatedMovies.observe(requireActivity()) { topRate ->
            topRate?.let {
                Log.d("TAG", "topRated: ${it.get(0).id}")
                topRatedMovieAdapter.submitList(it)
                binding.topRatedRecycler.apply {
                    adapter = topRatedMovieAdapter
                    setHasFixedSize(true)
                }

            }
        }

        movieViewModel.movieLoadError.observe(requireActivity(), Observer { isError ->
            if(isError == "" || isError == null){
                binding.noInternetLayoutMovieFragment.visibility =  View.GONE
                binding.imageSliderMovieFragment.visibility = View.VISIBLE
                binding.popularMovieLayoutMovieFrag.visibility = View.VISIBLE
                binding.topRatedMovieLayoutMovieFrag.visibility = View.VISIBLE
            }else{
                binding.noInternetLayoutMovieFragment.visibility =  View.VISIBLE
                binding.imageSliderMovieFragment.visibility = View.INVISIBLE
                binding.popularMovieLayoutMovieFrag.visibility = View.INVISIBLE
                binding.topRatedMovieLayoutMovieFrag.visibility  = View.INVISIBLE
            }

        })
        movieViewModel.loding.observe(requireActivity(), Observer { isLoading ->
            isLoading?.let {
                binding.spinKitMovieFrag.visibility = if (it) View.VISIBLE else View.GONE
                if (it){
                    binding.imageSliderMovieFragment.visibility = View.GONE
                    binding.popularMovieLayoutMovieFrag.visibility = View.GONE
                    binding.topRatedMovieLayoutMovieFrag.visibility = View.GONE
                }
            }
        })

    }


}