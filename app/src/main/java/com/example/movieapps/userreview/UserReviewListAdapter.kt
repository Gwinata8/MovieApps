package com.example.movieapps.userreview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.databinding.ItemMovieBinding
import com.example.movieapps.databinding.ItemReviewsBinding
import com.example.movieapps.movielist.MovieListEntity

class UserReviewListAdapter: ListAdapter<UserReviewListEntity.Result, UserReviewListAdapter.UserReviewListViewHolder>(DiffUtilCallback()) {

    class UserReviewListViewHolder(private val binding: ItemReviewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserReviewListEntity.Result) {
            binding.tvAuthor.text = item.author
            binding.tvDate.text = item.created_at
            binding.tvContent.text = item.content
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReviewListViewHolder {
        val binding = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserReviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserReviewListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<UserReviewListEntity.Result>() {
    override fun areItemsTheSame(oldItem: UserReviewListEntity.Result, newItem: UserReviewListEntity.Result): Boolean {
        return oldItem.author == newItem.author
    }

    override fun areContentsTheSame(oldItem: UserReviewListEntity.Result, newItem: UserReviewListEntity.Result): Boolean {
        return oldItem == newItem
    }
}