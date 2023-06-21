package com.example.swipeproduct.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swipeproduct.R
import com.example.swipeproduct.databinding.FragmentAddProductBinding


class AddProduct : Fragment() {

    lateinit var fragment_addProductBinding : FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragment_addProductBinding= FragmentAddProductBinding.
        inflate(inflater, container, false)

        return fragment_addProductBinding.root
    }


}