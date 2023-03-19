package com.meddev.demomovie.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meddev.demomovie.R
import com.meddev.demomovie.data.SMALL_POSTER_URL
import com.meddev.demomovie.databinding.ItemMoviesBinding
import com.meddev.demomovie.models.MoviesItem

@SuppressLint("NotifyDataSetChanged")
class MovieAdapter(listener: ClickListener) : RecyclerView.Adapter<MovieAdapter.MoviesHolder>() {

    private lateinit var context: Context
    private var defList: ArrayList<MoviesItem>
    private var movies: ArrayList<MoviesItem>
    private val listener: ClickListener

    init {
        this.defList = ArrayList()
        this.movies = ArrayList()
        this.listener = listener
    }

    //set movies list and notify adapter to refresh
    fun setMovies(list: ArrayList<MoviesItem>) {
        this.defList = ArrayList(list)
        this.movies = defList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MoviesHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.context = parent.context
        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MoviesHolder, position: Int) {
        holder.bind(position)
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(position)
        }
    }

    override fun getItemCount() = movies.size

    inner class MoviesHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val movie = movies[position]

            binding.tvName.text = movie.title
            binding.tvRelease.text =
                String.format(context.resources.getString(R.string.release), movie.releaseDate)
            binding.tvVote.text =
                String.format(context.resources.getString(R.string.vote), movie.voteAverage)

            val poster = "$SMALL_POSTER_URL${movie.posterPath}"

            Glide.with(context)
                .load(poster)
                .into(binding.imgPoster)

        }
    }

    //detect item click
    interface ClickListener {
        fun onItemClickListener(position: Int)
    }
}