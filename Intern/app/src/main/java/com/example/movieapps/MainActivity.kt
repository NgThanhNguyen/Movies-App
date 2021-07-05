package com.example.movieapps

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapps.models.Movies
import com.example.movieapps.services.MovieRepository
import com.example.movieapps.services.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    // Recycler View
    private lateinit var popularMovies: RecyclerView
    // Layout
    private lateinit var MoviesLayoutManager: LinearLayoutManager
    // Swipe to refresh
    lateinit var swipeContainer: SwipeRefreshLayout
    // get orientation of phone

    private var MoviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val orient = resources.configuration.orientation

        iniRefreshListener() // refreshing page

        popularMovies = findViewById(R.id.popular_movies)

        MoviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        popularMovies.layoutManager = MoviesLayoutManager

        popularMoviesAdapter = MoviesAdapter(orient,mutableListOf()) { movie -> showMovieDetails(movie) }

        popularMovies.adapter = popularMoviesAdapter

        getMovies()


    }

    private fun iniRefreshListener() {
        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener{
            popularMoviesAdapter.clear_movies()
            MoviesPage = 1
            getMovies()
            swipeContainer.isRefreshing = false
        }
    }

    private fun getMovies() {
        MovieRepository.getMovies(
            MoviesPage,
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = MoviesLayoutManager.itemCount
                val visibleItemCount = MoviesLayoutManager.childCount
                val firstVisibleItem = MoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    MoviesPage++
                    getMovies()
                }
            }
        })
    }


    private fun onPopularMoviesFetched(movies: MutableList<Movies>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun showMovieDetails(movie: Movies) {
        val intent = Intent(this, MovieDetail::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdrop_path)
        intent.putExtra(MOVIE_POSTER, movie.poster_path)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.vote_average)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.release_date)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }
}

