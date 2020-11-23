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

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this@MainActivity))
            .get(MainViewModel::class.java)

        getDataGame()
        initAdapter()
    }

    private fun initAdapter(){
        rv_game.adapter = movieAdapter.withLoadStateFooter(
            footer = MovieLoadStateAdapter { movieAdapter.retry() }
        )
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