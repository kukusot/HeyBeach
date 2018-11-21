package com.heybeach

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heybeach.beaches.domain.data.BeachesRemoteDataSource
import com.heybeach.beaches.ui.BeachesAdapter
import com.heybeach.http.Response
import com.heybeach.utils.dispatchOnMainThread
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var beachesAdapter: BeachesAdapter

    val beachesDataSource = BeachesRemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()


        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch(Dispatchers.IO) {
            val response = beachesDataSource.fetchBooks(1)
            if (response is Response.Success) {
                dispatchOnMainThread {
                    beachesAdapter.beaches = response.data
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.divider)!!)
            }
            addItemDecoration(dividerItemDecoration)

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            beachesAdapter = BeachesAdapter()
            adapter = beachesAdapter
        }
    }


}
