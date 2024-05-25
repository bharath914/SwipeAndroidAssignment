package com.bharath.swipeandroidassignment.presentation.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.presentation.adapters.OnClickListener
import com.bharath.swipeandroidassignment.presentation.adapters.ProductsListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchScreen : Fragment(), OnClickListener {


    private val viewModel: SearchScreenViewModel by viewModel()
    private val adapter: ProductsListAdapter = ProductsListAdapter(this)
    private lateinit var recycler: RecyclerView
    private lateinit var searchText: TextView
    private lateinit var searchLabel: TextView
    private lateinit var searchIllustration: RelativeLayout
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
                if (it.isEmpty()) {
                    animateSearchLabel()
                    searchIllustration.visibility = View.VISIBLE
                } else {
                    searchIllustration.visibility = View.GONE
                }
                adapter.submitNewList(it)
            }
        }
        return v
    }

    private var index = 0

    /**
     * [animateSearchLabel] used for simply animating the search label in the placeholder view
     *
     */
    private fun animateSearchLabel() {
        searchIllustration.visibility = View.VISIBLE
        lifecycleScope.launch {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_fade_in)
            val labels = listOf("Product Name", "Product Type", "Product Price")

            while (index < labels.size) {

                // set the text of the label
                searchLabel.text = labels[index++]
                searchLabel.startAnimation(animation)

                delay(300)
                // wait for the text animation to finish before animating the next label
                delay(1000)

            }

        }


    }

    private fun setTextViews() {
        searchText.addTextChangedListener(afterTextChanged = {
            viewModel.onSearch(it.toString())

        })
    }

    private fun setViews(view: View) {
        searchIllustration = view.findViewById(R.id.searchIllustrationPlaceHolder)
        searchLabel = view.findViewById(R.id.search_label)
        recycler = view.findViewById(R.id.searchRecycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)
        searchText = view.findViewById(R.id.searchText)
    }

    override fun onClick(index: Int) {
        val bundle = Bundle()
        bundle.putInt("Id", index)
        findNavController().navigate(R.id.action_searchScreen_to_productDetailFragment, bundle)
    }
}