package com.bharath.swipeandroidassignment.presentation.fragments.dialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bharath.swipeandroidassignment.R
import com.bharath.swipeandroidassignment.helpers.PermissionCheckers
import com.bharath.swipeandroidassignment.presentation.adapters.ImageListAdapter
import com.bharath.swipeandroidassignment.presentation.adapters.OnClickListener
import com.bharath.swipeandroidassignment.presentation.events.InputFieldsEvents
import com.bharath.swipeandroidassignment.presentation.fragments.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFrag : BottomSheetDialogFragment(), OnClickListener {
    private val viewModel: BottomSheetFragViewModel by viewModel()
    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var type: TextView
    private lateinit var tax: TextView
    private lateinit var button: MaterialButton
    private lateinit var sendingProgress: CircularProgressIndicator
    private lateinit var imagesRecycler: RecyclerView
    private val adapter = ImageListAdapter(this@BottomSheetFrag)
    private lateinit var addImageButton: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        bind(v)
        setEditText()
        onClickListeners(v)
        viewModel.uiState.apply {
            name.text = this.value.productName
            price.text = this.value.price
            type.text = this.value.productType
            tax.text = this.value.tax
        }
        lifecycleScope.launch {
            viewModel.imageList.collectLatest {
                adapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.sendState.collectLatest {

                if (it.isSending) {
                    button.visibility = View.GONE
                    sendingProgress.visibility = View.VISIBLE

                } else if (it.sentData != null && it.sentData.success) {
                    homeViewModel.resetIsCalled()
                    homeViewModel.getData(true, v.context)


                    findNavController().navigateUp()
                    Toast.makeText(view?.context, "Inserted Successfully", Toast.LENGTH_SHORT)
                        .show()


                } else if (it.error.isNullOrBlank().not()) {
                    button.visibility = View.VISIBLE
                    sendingProgress.visibility = View.GONE
                    Toast.makeText(v.context, "Cannot Post Now!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return v
    }


    private fun onClickListeners(view: View) {
        val pickMultipleMediaIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickMultipleMediaIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

        button.setOnClickListener {
            viewModel.onEvent(InputFieldsEvents.OnSentClick(view.context))
        }
        val permissionUtil = PermissionCheckers()
        addImageButton.setOnClickListener {
            if (
                permissionUtil.checkPermissionImagePermssion(requireContext())
            ) {
                startActivityForResult(pickMultipleMediaIntent, 22)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Grant Media Permission in settings",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 22 && resultCode == RESULT_OK && data != null) {
            if (data.clipData != null) {
                val count = data.clipData!!.itemCount
                val list = arrayListOf<Uri>()
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }
                viewModel.onEvent(InputFieldsEvents.OnFilesAdd(list))

            } else {
                val imageUri = data.data!!
                viewModel.onEvent(InputFieldsEvents.OnFileAdd(imageUri))
            }
        }
    }

    private fun setEditText() {
        val onEvent: (event: InputFieldsEvents) -> Unit = viewModel::onEvent
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
//                name.text = it.productName
//                price.text = it.price
//                type.text = it.productType
//                tax.text = it.tax
            }
        }

        name.addTextChangedListener(afterTextChanged = {
            onEvent(InputFieldsEvents.OnProductNameEnter(it.toString()))

        })
        price.addTextChangedListener(afterTextChanged = {
            onEvent(InputFieldsEvents.OnProductPriceEnter(it.toString()))
        })
        type.addTextChangedListener(afterTextChanged = {
            onEvent(InputFieldsEvents.OnProductTypeEnter(it.toString()))
        })
        tax.addTextChangedListener(afterTextChanged = {

            onEvent(InputFieldsEvents.OnTaxEnter(it.toString()))
        })

    }

    override fun onClick(index: Int) {
        viewModel.onEvent(InputFieldsEvents.RemoveImage(index))
    }

    private fun bind(view: View) {
        name = view.findViewById(R.id.input_product_name)
        price = view.findViewById(R.id.input_price_of_product)
        type = view.findViewById(R.id.input_type_of_product)
        sendingProgress = view.findViewById(R.id.sendingProgress)
        tax = view.findViewById(R.id.input_tax_percentage)
        button = view.findViewById(R.id.postButton)
        addImageButton = view.findViewById(R.id.addImageFiles)
        imagesRecycler = view.findViewById(R.id.imageRecycler)
        imagesRecycler.adapter = adapter
        imagesRecycler.layoutManager = LinearLayoutManager(view.context, HORIZONTAL, false)
    }


}