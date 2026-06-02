package com.example.movieapps.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.databinding.ItemMovieBinding

class MovieListAdapter(private val onMovieClick: (MovieListEntity.Movie) -> Unit): ListAdapter<MovieListEntity.Movie, MovieListAdapter.MovieListViewHolder>(DiffUtilCallback()) {

    class MovieListViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieListEntity.Movie, onMovieClick: (MovieListEntity.Movie) -> Unit) {
            binding.tvTitle.text = item.title
            binding.root.setOnClickListener {
                onMovieClick(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieClick)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<MovieListEntity.Movie>() {
    override fun areItemsTheSame(oldItem: MovieListEntity.Movie, newItem: MovieListEntity.Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieListEntity.Movie, newItem: MovieListEntity.Movie): Boolean {
        return oldItem == newItem
    }
}