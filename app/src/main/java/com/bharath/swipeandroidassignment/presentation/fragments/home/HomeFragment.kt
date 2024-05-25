package com.bharath.swipeandroidassignment.presentation.fragments.home

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.data.entity.local.ProductEntity
import com.bharath.swipeandroidassignment.helpers.ShowSnackBar
import com.bharath.swipeandroidassignment.presentation.adapters.OnClickListener
import com.bharath.swipeandroidassignment.presentation.adapters.ProductsListAdapter
import com.bharath.swipeandroidassignment.presentation.states.ListState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), OnClickListener {

    private val viewModel: HomeViewModel by sharedViewModel()
    private val adapter: ProductsListAdapter = ProductsListAdapter(this)
    private lateinit var recycler: RecyclerView
    private lateinit var searchIcon: ImageView
    private lateinit var swipeLogo: ImageView
    private lateinit var progress: LinearProgressIndicator
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var showSnackBar: ShowSnackBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Home", "onCreate: Called")
        context?.let {
            viewModel.getData(true, it)
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            viewModel.getData(true, it)
        }
    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        showSnackBar = ShowSnackBar(v.context)
        bind(v)
        lifecycleScope.launch {
            viewModel.productsListState.collectLatest { state ->
                setListState(state)
            }
        }

        lifecycleScope.launch {
            viewModel.connectedToNet.collectLatest { connected ->
                if (connected.not()) {
                    showSnackBar.showErrorSnack(
                        v.rootView,
                        "Not Connected To Internet!! Loading Offline Content"
                    )
                }
            }
        }

        setOnClicks()
        setLogo(v.context)
        return v
    }

    private fun setLogo(context: Context) {
        val currentNightMode =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {

                swipeLogo.setImageDrawable(resources.getDrawable(R.drawable.swipe_logo_light_small))
            }

            Configuration.UI_MODE_NIGHT_YES -> {

                swipeLogo.setImageDrawable(resources.getDrawable(R.drawable.swipe_logo_night))
            }
        }
    }


    private fun setListState(state: ListState<ProductEntity>) {
        if (state.isNotCached) {
            progress.visibility = VISIBLE
        } else if (state.isLoading) {
            progress.visibility = VISIBLE
        } else if (state.error.isNullOrBlank().not()) {


            view?.let {
                showSnackBar.showErrorSnack(
                    it,
                    "${state.error}"
                )

            }
        } else {
            progress.visibility = GONE
            adapter.submitNewList(state.list)
        }
    }

    private fun setOnClicks() {
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottomSheetFrag)
        }
        searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchScreen)
        }
    }

    private fun bind(view: View) {
        swipeLogo = view.findViewById(R.id.SwipeLogo)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        searchIcon = view.findViewById(R.id.searchIcon)
        progress = view.findViewById(R.id.progressBar)
        recycler = view.findViewById(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)

    }

    override fun onClick(index: Int) {
        val saved = SavedStateHandle()
        saved.set<Int>("Id", index)
        findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment)
    }
}