package com.example.swipeproduct.UI.fragment

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swipeproduct.R
import com.example.swipeproduct.UI.ProductRecyclerView
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.Utils.setDefaultColor
import com.example.swipeproduct.Utils.setGone
import com.example.swipeproduct.Utils.setGreyColor
import com.example.swipeproduct.Utils.setVisible
import com.example.swipeproduct.Utils.toast
import com.example.swipeproduct.databinding.FragmentListingProductBinding
import com.example.swipeproduct.model.ProductListModel
import com.example.swipeproduct.repository.ProductRepository
import com.example.swipeproduct.viewmodels.MainViewModel
import com.example.swipeproduct.viewmodels.MainViewModelFactory
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.koin.android.ext.android.inject


class ListingProduct : Fragment() {

    private val fragmentListingProductBinding by lazy {
        FragmentListingProductBinding.inflate(
            layoutInflater
        )
    }
    lateinit var viewModel: MainViewModel
    lateinit var productListModel: ProductListModel
    private val viewModelFactory: ViewModelProvider.Factory by inject()
    lateinit var internetLiveData: MutableLiveData<Boolean>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        internetLiveData = MutableLiveData<Boolean>()
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[MainViewModel::class.java]


        checkNetwork()

        return fragmentListingProductBinding.root
    }

    private fun checkNetwork() {
        viewModel.observeNetworkConnectivity(this) { isConnected ->
            if (isConnected) {

                viewModel.callApi()
                viewModel.uiState().observe(viewLifecycleOwner) {

                    Log.d("Listing","viewmodel data check $it")

                    if (it != null) {
                        render(it)
                    }
                }
            } else {
                with(fragmentListingProductBinding) {
                    includeInternetLayout.noInternetParentLayout.setVisible()
                    AddProductButton.isEnabled = false
                    AddProductButton.setGreyColor()
                    rootView.setGone()

                }
                requireActivity().toast("Check for Internet Connection!!")

            }
        }
    }


    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }

            is UiState.Success -> {
                onSuccess(uiState.modelClass)
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

    private fun onSuccess(data: Any) = with(fragmentListingProductBinding) {

        progressBarListingProduct.setGone()
        AddProductButton.isEnabled = true
        AddProductButton.setDefaultColor()
        includeInternetLayout.noInternetParentLayout.setGone()
        rootView.setVisible()
        AddProductButton.setDefaultColor()
        initRecyclerView(data)

    }

    private fun onLoad() = with(fragmentListingProductBinding) {
        progressBarListingProduct.setVisible()
        AddProductButton.isEnabled = false
        rootView.setGone()
        AddProductButton.setGreyColor()
    }

    private fun initRecyclerView(data: Any) {

        fragmentListingProductBinding.productListRV.apply {

            val modelClass = data as MutableLiveData<*>

            modelClass.observe(requireActivity()) {
                adapter = ProductRecyclerView(requireContext(), it as ProductListModel)
                setUpList(it)

            }

            hasFixedSize()
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setUpList(it: ProductListModel?) {

        val listOfOption = it?.map {
            it.product_type
        }
        val fragment = AddProduct()
        val bundle = Bundle()
        bundle.putStringArrayList("listOption", listOfOption!!.toCollection(ArrayList<String>()))
        fragment.arguments = bundle

        fragmentListingProductBinding.AddProductButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_ListingProduct_fragment_to_AddProduct_fragment,
                bundle
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {

            requireActivity().finish()
        }
    }


}

