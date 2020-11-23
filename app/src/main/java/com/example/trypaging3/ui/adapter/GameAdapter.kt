package com.example.trypaging3.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trypaging3.data.api.response.GameResponse

class GameAdapter : PagingDataAdapter<GameResponse, RecyclerView.ViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null){
            (holder as GameViewHolder).bind(items)
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<GameResponse>(){
            override fun areItemsTheSame(oldItem: GameResponse, newItem: GameResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GameResponse, newItem: GameResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}