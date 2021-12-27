package com.seokan.moviemvvm.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.seokan.moviemvvm.R
import com.seokan.moviemvvm.data.api.POSTER_BASE_URL
import com.seokan.moviemvvm.data.api.TheMovieDBClient
import com.seokan.moviemvvm.data.api.TheMovieDBInterface
import com.seokan.moviemvvm.data.repository.NetworkState
import com.seokan.moviemvvm.data.vo.MovieDetails
import com.seokan.moviemvvm.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private val binding = ActivitySingleMovieBinding.inflate(layoutInflater)
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer{
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer{
            binding.progressBar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(it: MovieDetails){
        binding.movieTitle.text = it.title
        binding.movieTagline.text = it.tagline
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieRuntime.text = it.runtime.toString() + " minutes"
        binding.movieOverview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text = formatCurrency.format(it.budget)
        binding.movieRevenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivMoviePoster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel{
        return ViewModelProviders.of(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}