package com.example.swipeproduct.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.swipeproduct.GradientItemDecoration
import com.example.swipeproduct.ProductRecyclerView
import com.example.swipeproduct.R
import com.example.swipeproduct.databinding.FragmentListingProductBinding
import com.example.swipeproduct.setDefaultColor


class ListingProduct : Fragment() {

    lateinit var fragment_ListingProductBinding : FragmentListingProductBinding
    private val adapter = ProductRecyclerView()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragment_ListingProductBinding =
            FragmentListingProductBinding.inflate(inflater,container,false)

        fragment_ListingProductBinding.productListRV.adapter = adapter
        fragment_ListingProductBinding.productListRV.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

//        fragment_ListingProductBinding.productListRV.addItemDecoration(GradientItemDecoration(requireContext()))
        fragment_ListingProductBinding.AddProductButton.setDefaultColor()

        fragment_ListingProductBinding.AddProductButton.setOnClickListener {
            findNavController().navigate(R.id.action_ListingProduct_fragment_to_AddProduct_fragment)
        }

        return fragment_ListingProductBinding.root
    }


}

