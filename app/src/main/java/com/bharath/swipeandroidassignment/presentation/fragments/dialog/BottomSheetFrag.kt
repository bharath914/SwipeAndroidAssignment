package com.bharath.swipeandroidassignment.presentation.fragments.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bharath.swipeandroidassignment.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFrag : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)


        return v
    }


}