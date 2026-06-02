package com.example.movieapps.moviegenre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.moviegenre.MovieGenreEntity
import com.example.movieapps.databinding.ItemGenreBinding

class GenresAdapter(private val onGenreClick: (MovieGenreEntity.Genre) -> Unit) : ListAdapter<MovieGenreEntity.Genre, GenresAdapter.GenreViewHolder>(DiffUtilCallback()) {

    class GenreViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieGenreEntity.Genre, onGenreClick: (MovieGenreEntity.Genre) -> Unit) {
            binding.tvGenre.text = item.name
            binding.root.setOnClickListener {
                onGenreClick(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position), onGenreClick)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<MovieGenreEntity.Genre>() {
    override fun areItemsTheSame(oldItem: MovieGenreEntity.Genre, newItem: MovieGenreEntity.Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieGenreEntity.Genre, newItem: MovieGenreEntity.Genre): Boolean {
        return oldItem == newItem
    }
}