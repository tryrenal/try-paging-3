package com.example.trypaging3.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trypaging3.Injection
import com.example.trypaging3.R
import com.example.trypaging3.ui.adapter.MovieAdapter
import com.example.trypaging3.ui.adapter.MovieLoadStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private var movieAdapter = MovieAdapter()
    private var movieJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_game.layoutManager = layoutManager

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MainViewModel::class.java)

        getDataGame()
        initAdapter()

        retry_button_main.setOnClickListener { movieAdapter.retry() }
    }

    private fun initAdapter(){
        rv_game.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.addLoadStateListener { loadState ->
            rv_game.isVisible = loadState.source.refresh is LoadState.NotLoading
            pb_main.isVisible = loadState.source.refresh is LoadState.Loading
            retry_button_main.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(this, "error ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDataGame(){
        movieJob?.cancel()
        movieJob = lifecycleScope.launch {
            viewModel.gameData().collectLatest { data ->
                movieAdapter.submitData(data)
            }
        }
    }
}