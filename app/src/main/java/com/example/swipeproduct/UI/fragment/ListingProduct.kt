package com.example.swipeproduct.UI.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipeproduct.R
import com.example.swipeproduct.UI.ProductRecyclerView
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.Utils.setDefaultColor
import com.example.swipeproduct.Utils.setGone
import com.example.swipeproduct.Utils.setVisible
import com.example.swipeproduct.databinding.FragmentListingProductBinding
import com.example.swipeproduct.model.ProductListModel
import com.example.swipeproduct.repository.ProductRepository
import com.example.swipeproduct.viewmodels.MainViewModel
import com.example.swipeproduct.viewmodels.MainViewModelFactory


class ListingProduct : Fragment() {

    private val fragmentListingProductBinding by lazy {
        FragmentListingProductBinding.inflate(
            layoutInflater
        )
    }
    lateinit var viewModel : MainViewModel
    lateinit var productListModel: ProductListModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = ProductRepository()

        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        viewModel.callApi()

        viewModel.uiState().observe(viewLifecycleOwner){

            if(it!=null)
            {

                render(it)
            }

        }

        fragmentListingProductBinding.AddProductButton.setDefaultColor()

        return fragmentListingProductBinding.root
    }


    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }
        }
    }


    private fun onError(uiState: UiState.Error) = with(fragmentListingProductBinding) {

        progressBarListingProduct.setGone()
        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()

    }

    private fun onSuccess(uiState: UiState.Success) {

        fragmentListingProductBinding.progressBarListingProduct.setGone()
        fragmentListingProductBinding.AddProductButton.isEnabled = true

        initRecyclerView(uiState.productListModel)


    }

    private fun onLoad() = with(fragmentListingProductBinding) {
        progressBarListingProduct.setVisible()
        AddProductButton.isEnabled = false
    }

    private fun initRecyclerView(productListModel: LiveData<ProductListModel>) {

        fragmentListingProductBinding.productListRV.apply {

            productListModel.observe(requireActivity()) {
                adapter = ProductRecyclerView(requireContext(),it)
                setUpList(it)

            }

                    hasFixedSize()
                    layoutManager = LinearLayoutManager(requireContext())
//                    addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }

    }

    private fun setUpList(it: ProductListModel?) {

        val listOfOption = it?.map {
            it.product_type
        }
        val fragment = AddProduct()
        val bundle = Bundle()
        bundle.putStringArrayList("listOption",listOfOption!!.toCollection(ArrayList<String>()))
        fragment.arguments = bundle

        fragmentListingProductBinding.AddProductButton.setOnClickListener {
            findNavController().navigate(R.id.action_ListingProduct_fragment_to_AddProduct_fragment,bundle)
        }

    }


}

