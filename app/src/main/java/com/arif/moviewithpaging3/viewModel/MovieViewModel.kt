package com.arif.moviewithpaging3.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arif.moviewithpaging3.apiService.MovieApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val api : MovieApi) :  ViewModel(){

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val popularMovies = MutableLiveData<List<com.arif.moviewithpaging3.entity.Result>>()
    val upcomingMovies = MutableLiveData<List<com.arif.moviewithpaging3.entity.Result>>()
    val topRatedMovies = MutableLiveData<List<com.arif.moviewithpaging3.entity.Result>>()
    var popullarMoviesTotalResults = -1
    var topRatedMoviesTotalResults = -1
    val loding = MutableLiveData<Boolean>()
    val movieLoadError = MutableLiveData<String?>()

    fun getPopularMovies(language : String, page : Int){
        fetchPopularMovies(language,page)
    }

    fun getUpcomingMovies(){
        fetchUpcomingMovies()
    }
    fun getTopRatedMovies(language: String, page: Int){
        fetchTopRatedMovies(language,page)
    }

    private fun fetchUpcomingMovies(){
        loding.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler)
            .launch {
                val reponse = api.getUpcomingMovie("",1)
                withContext(Dispatchers.Main){
                    if (reponse.isSuccessful){
                        upcomingMovies.value = reponse.body()?.results
                        movieLoadError.value = null
                        loding.value = false
                    }else{
                        onError("Error : ${reponse.message()}")
                    }
                }
            }
        movieLoadError.value = ""
        loding.value = false
    }


    private fun fetchPopularMovies(language: String, page: Int) {
        loding.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler)
            .launch {
                val response = api.getPopularMovie(language,page)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        popularMovies.value = response.body()?.results
                        popullarMoviesTotalResults = response.body()!!.totalResults
                        movieLoadError.value = null
                        loding.value = false
                    }else{
                        onError("Error : ${response.message()}")
                    }
                }
            }
        movieLoadError.value = ""
        loding.value = false
    }


    private fun fetchTopRatedMovies(language: String, page: Int) {
        loding.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler)
            .launch {
                val response = api.getTopRatedMovie(language,page)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        topRatedMovies.value = response.body()?.results
                        movieLoadError.value = null
                        loding.value = false
                    }else{
                        onError("Error : ${response.message()}")
                    }
                }
            }
        movieLoadError.value = ""
        loding.value = false
    }

    private fun onError(message: String) {

        GlobalScope.launch {
            withContext(Dispatchers.Main){
                movieLoadError.value = message
                loding.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}