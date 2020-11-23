package com.example.trypaging3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trypaging3.Injection
import com.example.trypaging3.R
import com.example.trypaging3.ui.adapter.GameAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private var gameAdapter = GameAdapter()
    private var gameJob : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_game.layoutManager = layoutManager

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MainViewModel::class.java)

        initAdapter()

    }

    private fun initAdapter(){
        rv_game.adapter = gameAdapter
        gameJob?.cancel()
        gameJob = lifecycleScope.launch {
            viewModel.gameData().collectLatest { data ->
                gameAdapter.submitData(data)
            }
        }
    }
}