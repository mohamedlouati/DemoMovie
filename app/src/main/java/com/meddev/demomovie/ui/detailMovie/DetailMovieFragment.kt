package com.meddev.demomovie.ui.detailMovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.meddev.demomovie.R
import com.meddev.demomovie.data.BIG_POSTER_URL
import com.meddev.demomovie.databinding.FragmentDetailMovieBinding
import com.meddev.demomovie.ui.viewmodel.MovieDetailsViewModel

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieDetailsViewModel

    private var movieId = 0

    private val args by navArgs<DetailMovieFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel=
            ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        movieId = args.id
        if (movieId == 0) {
            NavHostFragment.findNavController(this).popBackStack()
        }
        observeMovieDetails()

        movieViewModel.getMovieDetails(movieId)
    }

    private fun observeMovieDetails() {
        movieViewModel.movieDetails.observe(viewLifecycleOwner){
            binding.tvName.text = it.title
            binding.tvRelease.text = String.format(resources.getString(R.string.release), it.releaseDate)
            binding.tvOverview.text = String.format(resources.getString(R.string.overview), it.overview)

            val poster = "$BIG_POSTER_URL${it.posterPath}"
            Glide.with(this)
                .load(poster)
                .into(binding.imgPoster)
        }
        movieViewModel.errorMessage.observe(viewLifecycleOwner){
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}