package com.example.trypaging3.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trypaging3.data.local.entity.MovieEntity
import com.example.trypaging3.data.network.response.MovieResponse

class MovieAdapter : PagingDataAdapter<MovieEntity, RecyclerView.ViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null){
            (holder as MovieViewHolder).bind(items)
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}