package com.example.trypaging3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trypaging3.R
import com.example.trypaging3.data.local.entity.MovieEntity
import com.example.trypaging3.data.network.response.MovieResponse

class MovieViewHolder (view: View) : RecyclerView.ViewHolder(view){
    private val image = view.findViewById<ImageView>(R.id.img_game)
    private val title = view.findViewById<TextView>(R.id.tv_title_game)

    fun bind(data: MovieEntity?){
        if (data != null){
            Glide.with(itemView.context)
                .load(data.backdrop_path)
                .into(image)
            title.text = data.title
        }
    }

    companion object{
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_game_item, parent, false)
            return MovieViewHolder(view)
        }
    }

}