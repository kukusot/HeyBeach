package com.heybeach.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.heybeach.R
import com.heybeach.main.data.BEACHES
import com.heybeach.main.data.MainModel
import com.heybeach.main.data.PROFILE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelFactory = MainInjector.mainViewModelFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.beaches -> viewModel.setSelectedFragment(BEACHES)
                R.id.profile -> viewModel.setSelectedFragment(PROFILE)
            }
            return@setOnNavigationItemSelectedListener true
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.mainModel.observe(this, Observer { mainModel ->
            setupFragments(mainModel)
        })
    }

    private fun setupFragments(mainModel: MainModel) {
        for (fragmentName in mainModel.fragmentNames) {
            val added = supportFragmentManager.findFragmentByTag(fragmentName)
            if (added == null) {
                val fragment = Fragment.instantiate(this, fragmentName, null)
                supportFragmentManager.transaction {
                    add(R.id.content, fragment, fragmentName)
                    setFragmentVisibility(fragment, mainModel)
                }
            } else {
                setFragmentVisibility(added, mainModel)
            }
        }
    }

    private fun setFragmentVisibility(fragment: Fragment, mainModel: MainModel) {
        supportFragmentManager.transaction {
            val fragmentName = fragment.javaClass.canonicalName!!
            if (fragmentName == mainModel.seletedFragmentName) {
                show(fragment)
            } else {
                hide(fragment)
            }

        }
    }


    /*   private fun setupRecyclerView() {
           recyclerView.apply {
               val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                   setDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.divider)!!)
               }
               addItemDecoration(dividerItemDecoration)

               layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
               beachesAdapter = BeachesAdapter()
               adapter = beachesAdapter
           }
       }*/


}
