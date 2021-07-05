package com.example.movieapps.services

import android.content.res.Configuration
import android.mtp.MtpConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapps.R
import com.example.movieapps.models.Movies
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val orientation: Int,var movies: MutableList<Movies>, private val onMovieClick: (movie: Movies) -> Unit) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: MutableList<Movies>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(this.movies.size, movies.size - 1)
    }

    fun getRate() {
        movies[0].vote_average
    }

    fun clear_movies() {
        movies.clear()
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movies) {
            itemView.title.text = movie?.title
            itemView.description.text = movie?.overview
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
                    .transform(CenterCrop())
                    .into(poster)
            }
            else {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${movie.backdrop_path}")
                    .transform(CenterCrop())
                    .into(poster)
            }

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }
}