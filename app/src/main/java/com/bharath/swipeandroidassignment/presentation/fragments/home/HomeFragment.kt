package com.bharath.swipeandroidassignment.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.presentation.adapters.HomeListAdapter
import com.bharath.swipeandroidassignment.presentation.states.ListState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeListAdapter = HomeListAdapter()
    private lateinit var recycler: RecyclerView
    private lateinit var newDataProgressBar: CircularProgressIndicator
    private lateinit var progress: LinearProgressIndicator
    private lateinit var floatingActionButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(true)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        bind(v)
        lifecycleScope.launch {
            viewModel.productsListState.collectLatest { state ->
                setListState(state)
            }
        }
        setOnClicks()

        return v
    }

    private fun setListState(state: ListState<ProductEntity>) {
        if (state.isNotCached) {
            progress.visibility = VISIBLE
        } else if (state.isLoading) {
            newDataProgressBar.visibility = VISIBLE

        } else if (state.error.isNullOrBlank().not()) {

        } else {
            newDataProgressBar.visibility = GONE
            progress.visibility = GONE
            adapter.submitNewList(state.list)
        }
    }

    private fun setOnClicks() {
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottomSheetFrag)
        }
    }

    private fun bind(view: View) {
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        newDataProgressBar = view.findViewById(R.id.newDataProgressBar)
        progress = view.findViewById(R.id.progressBar)
        recycler = view.findViewById(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)

    }


}