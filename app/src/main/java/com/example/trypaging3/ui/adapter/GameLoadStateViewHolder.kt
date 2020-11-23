package com.example.trypaging3.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.trypaging3.R
import kotlinx.android.synthetic.main.load_state_footer.view.*

class GameLoadStateViewHolder(
    view: View,
    retry : () -> Unit
) : RecyclerView.ViewHolder(view) {
    init {
        itemView.retry_button.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState){
        if (loadState is LoadState.Error){
            itemView.error_msg.text = loadState.error.localizedMessage
        }
        with(itemView){
            progress_bar.isVisible = loadState is LoadState.Loading
            retry_button.isVisible = loadState !is LoadState.Loading
            error_msg.isVisible = loadState !is LoadState.Loading
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit) : GameLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer, parent, false)
            return GameLoadStateViewHolder(view, retry)
        }
    }
}