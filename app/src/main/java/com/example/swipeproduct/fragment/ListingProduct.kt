package com.example.swipeproduct.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.swipeproduct.R
import com.example.swipeproduct.databinding.FragmentListingProductBinding


class ListingProduct : Fragment() {

    lateinit var fragment_ListingProductBinding : FragmentListingProductBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragment_ListingProductBinding =
            FragmentListingProductBinding.inflate(inflater,container,false)


        return fragment_ListingProductBinding.root
    }


}