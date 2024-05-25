package com.bharath.swipeandroidassignment.presentation.fragments.home

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
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
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * The HomeFragment class represents the home screen of the application,
 * displaying a list of products and handling user interactions.
 */
class HomeFragment : Fragment(), OnClickListener {

    private val viewModel: HomeViewModel by sharedViewModel()

    private val adapter: ProductsListAdapter = ProductsListAdapter(this, true)

    // UI components
    private lateinit var recycler: RecyclerView
    private lateinit var searchIcon: ImageView
    private lateinit var swipeLogo: ImageView
    private lateinit var progress: ShimmerFrameLayout
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        showSnackBar = ShowSnackBar(view.context)
        bind(view)

        // Collecting the state of the products list
        lifecycleScope.launch {
            viewModel.productsListState.collectLatest { state ->
                setListState(state)
            }
        }

        // Collecting the network connection status
        lifecycleScope.launch {
            viewModel.connectedToNet.collectLatest { connected ->
                if (!connected) {
                    showSnackBar.showErrorSnack(
                        view.rootView,
                        "Not Connected To Internet!! Loading Offline Content"
                    )
                }
            }
        }

        setOnClicks()
        setLogo(view.context)
        return view
    }

    /**
     * Sets the appropriate logo based on the current theme (light or dark mode).
     *@param context The context used to access resources.
     */
    private fun setLogo(context: Context) {
        val currentNightMode =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                swipeLogo.setImageResource(R.drawable.swipe_logo_light_small)
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                swipeLogo.setImageResource(R.drawable.swipe_logo_night)
            }
        }
    }

    /**
     * Updates the UI based on the state of the products list.
     * @param state The current state of the products list.
     */
    private fun setListState(state: ListState<ProductEntity>) {
        when {
            state.isNotCached -> {
                progress.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                progress.startShimmer()
            }

            state.isLoading -> {
                progress.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                progress.startShimmer()
            }

            !state.error.isNullOrBlank() -> {
                view?.let {
                    showSnackBar.showErrorSnack(it, state.error)

                }
            }

            else -> {
                progress.visibility = View.GONE
                progress.stopShimmer()
                recycler.visibility = View.VISIBLE
                adapter.submitNewList(state.list)
            }
        }
    }

    /**
     * Sets click listeners for the floating action button and search icon.
     */
    private fun setOnClicks() {
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bottomSheetFrag)
        }
        searchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchScreen)
        }
    }

    /**
     * Binds the UI components to their respective views.
     *
     * @param view The view containing the UI components.
     */
    private fun bind(view: View) {
        swipeLogo = view.findViewById(R.id.SwipeLogo)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        searchIcon = view.findViewById(R.id.searchIcon)
        progress = view.findViewById(R.id.progressBar)
        recycler = view.findViewById(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)
        recycler.setItemViewCacheSize(25)
    }

    /**
     * Handles the click events for the items in the RecyclerView.
     *
     * @param index The id of the clicked item.
     */
    override fun onClick(index: Int) {
        val navController = findNavController()
        val bundle = Bundle().apply {
            putInt("Id", index)
        }
        navController.navigate(R.id.action_homeFragment_to_productDetailFragment, bundle)
    }
}