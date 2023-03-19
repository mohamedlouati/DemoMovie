package com.meddev.demomovie.ui.listMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meddev.demomovie.R
import com.meddev.demomovie.databinding.FragmentListMoviesBinding
import com.meddev.demomovie.models.MoviesItem
import com.meddev.demomovie.ui.adapter.MovieAdapter
import com.meddev.demomovie.ui.viewmodel.MovieListViewModel

class MoviesListFragment: Fragment(), MovieAdapter.ClickListener {

    private var _binding: FragmentListMoviesBinding? = null
    private val binding get() = _binding!!

    private var page = 1

    private lateinit var moviesViewModel: MovieListViewModel

    private lateinit var adapter: MovieAdapter
    private lateinit var arrayList: ArrayList<MoviesItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel=
            ViewModelProvider(this)[MovieListViewModel::class.java]

        binding.rvData.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    page+=1
                    moviesViewModel.getAllMovies(page)
                }
            }
        })

        initRecyclerView()
        observeMovieList()

        moviesViewModel.getAllMovies(page)
    }

    private fun initRecyclerView(){
        arrayList = ArrayList()
        adapter = MovieAdapter(this)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false)
        binding.rvData.layoutManager = mLayoutManager
        binding.rvData.adapter = adapter
    }

    private fun observeMovieList() {
        moviesViewModel.moviesList.observe(viewLifecycleOwner) {
            arrayList.addAll(it)
            adapter.setMovies(arrayList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickListener(position: Int) {
        val bundle = Bundle()
        bundle.putInt("id", arrayList[position].id)
        NavHostFragment.findNavController(this)
            .navigate(R.id.detailMovieFragment, bundle)
    }

}