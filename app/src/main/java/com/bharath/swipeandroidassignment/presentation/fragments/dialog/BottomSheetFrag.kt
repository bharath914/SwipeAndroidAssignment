package com.bharath.swipeandroidassignment.presentation.fragments.dialog

import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
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
import com.bharath.swipeandroidassignment.helpers.ProductCategories
import com.bharath.swipeandroidassignment.helpers.toIndianNumberSystem
import com.bharath.swipeandroidassignment.presentation.adapters.ImageListAdapter
import com.bharath.swipeandroidassignment.presentation.adapters.OnClickListener
import com.bharath.swipeandroidassignment.presentation.events.InputFieldsEvents
import com.bharath.swipeandroidassignment.presentation.fragments.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFrag : BottomSheetDialogFragment(), OnClickListener {
    private val viewModel: BottomSheetFragViewModel by viewModel()
    private val productCategories = ProductCategories()
    private val typesList = productCategories.productTypes
    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var name: EditText
    private lateinit var price: EditText
    private lateinit var type: TextView
    private lateinit var tax: EditText
    private lateinit var selectTypeCard: RelativeLayout
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
        val onEvent: (event: InputFieldsEvents) -> Unit = viewModel::onEvent
        bind(v)
        setEditText(onEvent = onEvent)
        onClickListeners(v, onEvent)
        // when user rotates the screen views will automatically restore
        viewModel.uiState.apply {
            name.setText(this.value.productName)
            price.setText(this.value.price)
            type.text = this.value.productType.ifBlank { "Select Product" }
            tax.setText(this.value.tax)
        }

        // observe the values.
        observers(v)
        return v
    }


    /**
     * Observes changes in the ViewModel's state flows and updates the UI accordingly.
     *
     */
    private fun observers(v: View) {
        // Observes changes in the list of images and updates the adapter.
        lifecycleScope.launch {
            viewModel.imageList.collectLatest { images ->
                adapter.submitList(images)
            }
        }

        // Observes changes in the send state (e.g., sending progress, success, or error) and handles UI updates and navigation.
        lifecycleScope.launch {
            viewModel.sendState.collectLatest { sendState ->
                // if sending, show the progress indicator and hide the button
                if (sendState.isSending) {
                    button.visibility = View.GONE
                    sendingProgress.visibility = View.VISIBLE
                } else if (sendState.sentData != null && sendState.sentData.success) {
                    // if success, reset the isCalled flag and navigate back and update the recyclerview in home fragment.
                    homeViewModel.resetIsCalled()
                    homeViewModel.getData(true, v.context)
                    findNavController().navigateUp()
                    Toast.makeText(v.context, "Inserted Successfully", Toast.LENGTH_SHORT).show()
                } else if (sendState.error.isNullOrBlank().not()) {
                    // if there is any error while sending then show the button again and toast the error
                    button.visibility = View.VISIBLE
                    sendingProgress.visibility = View.GONE
                    Toast.makeText(v.context, "Cannot Post Now!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observes changes in the UI state (e.g., product type) and updates the UI.
        lifecycleScope.launch(IO) {
            viewModel.uiState.collectLatest { uiState ->
                if (uiState.productType != type.text.toString()) {
                    type.text = uiState.productType
                }
            }
        }
    }


    private fun onClickListeners(view: View, onEvent: (event: InputFieldsEvents) -> Unit) {
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
                // if permission is granted then open the gallery
                startActivityForResult(pickMultipleMediaIntent, 22)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Grant Media Permission in settings",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        selectTypeCard.setOnClickListener {
            alertDialog(onEvent = onEvent)
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


    private fun alertDialog(onEvent: (event: InputFieldsEvents) -> Unit) {
// dialog for selecting product type
        val alertDialog = MaterialAlertDialogBuilder(requireContext())
        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            lifecycleScope.launch {
                type.text = typesList[i]
                onEvent(InputFieldsEvents.OnProductTypeEnter(typesList[i]))
            }.invokeOnCompletion {
                lifecycleScope.launch(Main) {
                    dialogInterface.dismiss()
                }
            }
        }
        /**
         *after selecting the choice [listener] will be called
         */
        alertDialog.setSingleChoiceItems(
            typesList,
            0, listener
        )
        // show the alert dialog asynchronously14
        lifecycleScope.launch(Main) {
            alertDialog.show()
        }
    }

    // populate the edittext with their respective fields.
    private fun setEditText(onEvent: (event: InputFieldsEvents) -> Unit) {

        lifecycleScope.launch {
            viewModel.price.collectLatest { text ->
                if (price.text.toString() != text) {
                    price.setText(text)
                    price.setSelection(price.text.length.coerceAtMost(text.length))
                }
            }
        }
        // call the onEvent function with respected UI event to update the UI.

        name.addTextChangedListener(afterTextChanged = {
            onEvent(InputFieldsEvents.OnProductNameEnter(it.toString()))
        })
        price.addTextChangedListener(afterTextChanged = {
            if (it.isNullOrBlank().not() && viewModel.price.value != it.toString()) {
                onEvent(InputFieldsEvents.OnProductPriceEnter(it.toString().toIndianNumberSystem()))
            }
        })
        tax.addTextChangedListener(
            afterTextChanged = {
                onEvent(InputFieldsEvents.OnTaxEnter(it.toString()))
            }
        )

    }

    override fun onClick(index: Int) {
        // to remove the selected image
        viewModel.onEvent(InputFieldsEvents.RemoveImage(index))
    }
    // bind the Ui Components to the view

    private fun bind(view: View) {
        selectTypeCard = view.findViewById(R.id.selectTypeCard)
        name = view.findViewById(R.id.input_product_name)
        price = view.findViewById(R.id.input_price_of_product)
        type = view.findViewById(R.id.input_type)
        sendingProgress = view.findViewById(R.id.sendingProgress)
        tax = view.findViewById(R.id.input_tax_percentage)
        button = view.findViewById(R.id.postButton)
        addImageButton = view.findViewById(R.id.addImageFiles)
        imagesRecycler = view.findViewById(R.id.imageRecycler)
        imagesRecycler.adapter = adapter
        imagesRecycler.layoutManager = LinearLayoutManager(view.context, HORIZONTAL, false)
    }


}