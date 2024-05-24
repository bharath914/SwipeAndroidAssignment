package com.bharath.swipeandroidassignment.presentation.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.presentation.adapters.ProductsListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchScreen : Fragment() {


    private val viewModel: SearchScreenViewModel by viewModel()
    private val adapter: ProductsListAdapter = ProductsListAdapter()
    private lateinit var recycler: RecyclerView
    private lateinit var searchText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val v = inflater.inflate(R.layout.fragment_search_screen, container, false)
        setViews(v)
        searchText.text = viewModel.searchText.value
        setTextViews()
        lifecycleScope.launch {
            viewModel.filteredItems.collectLatest {
                adapter.submitNewList(it)
            }
        }

        return v
    }

    private fun setTextViews() {
        searchText.addTextChangedListener(afterTextChanged = {
            viewModel.onSearch(it.toString())
        })
    }

    private fun setViews(view: View) {
        recycler = view.findViewById(R.id.searchRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)
        searchText = view.findViewById(R.id.searchText)
    }
}