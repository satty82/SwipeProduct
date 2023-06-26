package com.example.swipeproduct.UI.fragment

import android.R
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.swipeproduct.Utils.UiState
import com.example.swipeproduct.Utils.setDefaultColor
import com.example.swipeproduct.Utils.setGone
import com.example.swipeproduct.Utils.setGreyColor
import com.example.swipeproduct.Utils.setVisible
import com.example.swipeproduct.Utils.toast
import com.example.swipeproduct.databinding.FragmentAddProductBinding
import com.example.swipeproduct.model.AddProductModel
import com.example.swipeproduct.model.ProductAddedSuccessfullyModel
import com.example.swipeproduct.repository.ProductRepository
import com.example.swipeproduct.viewmodels.MainViewModel
import com.example.swipeproduct.viewmodels.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class AddProduct : Fragment() {

    lateinit var fragment_addProductBinding: FragmentAddProductBinding

    private var imageURI: Uri? = null
    private val viewModelFactory: ViewModelProvider.Factory by inject()
    private var internetState = false
    lateinit var addProductModel :AddProductModel
    lateinit var viewModel: MainViewModel


    val changeImage = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val data = it.data
            this.imageURI = data?.data!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        fragment_addProductBinding = FragmentAddProductBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(MainViewModel::class.java)


        viewModel.observeNetworkConnectivity(viewLifecycleOwner) { isConnected ->
            internetState = isConnected
        }


        val bundle = this.arguments

        bundle?.let {
            val list = it.getStringArrayList("listOption")
            getListOptions(list)
        }

        fragment_addProductBinding.submitButton.setOnClickListener {

            validation()
        }

        fragment_addProductBinding.backButton.setOnClickListener {

            findNavController().navigate(com.example.swipeproduct.R.id.action_AddProduct_fragment_to_ListingProduct_fragment)
        }

        fragment_addProductBinding.selectImageButton.setOnClickListener {

            getPhoto()
        }

        return fragment_addProductBinding.root
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

    private fun onError(uiState: UiState.Error) = with(fragment_addProductBinding) {

        progressBarAddProduct.setGone()
        Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()

    }

    private fun onSuccess(data: Any) = with(fragment_addProductBinding) {

//        Log.d("AddProduct","updated data is Success ")

        fragment_addProductBinding.progressBarAddProduct.setGone()
        fragment_addProductBinding.backButton.isEnabled = true
        fragment_addProductBinding.submitButton.isEnabled = true
        backButton.setDefaultColor()
        submitButton.setDefaultColor()


            Log.d("AddProduct","updated data is modelclass $data ")

            data as ProductAddedSuccessfullyModel
            showDialogBoxOnSuccess(data)


        }


    private fun onLoad() = with(fragment_addProductBinding) {
        progressBarAddProduct.setVisible()
        backButton.isEnabled = false
        submitButton.isEnabled = false
        backButton.setGreyColor()
        submitButton.setGreyColor()
    }

    private fun showDialogBoxOnSuccess(data: ProductAddedSuccessfullyModel) {

            val alertDialogBuilder = AlertDialog.Builder(requireActivity())
            alertDialogBuilder.setTitle("Success !!")

            alertDialogBuilder.setMessage(data.message)

            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->

                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }


    private fun getPhoto() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(intent)

    }

    private fun validation() {

        with(fragment_addProductBinding) {

            if (enterProductName.text.toString().trim().equals("")) {
                tienterProductName.error = "Type Product Name"

            } else if (enterSellingPrice.text.toString().trim().equals("")) {

                tienterSellingPrice.error = "Type Selling Price"
            } else if (entertax.text.toString().trim().equals("")) {

                tientertax.error = "Type Product Name"
            } else {

                tienterProductName.error = null
                tienterSellingPrice.error = null
                tientertax.error = null
            }

                addProductModel = AddProductModel(
                enterProductName.text.toString().trim(),
                spinnerProductList.selectedItem.toString(),
                enterSellingPrice.text.toString().trim(),
                entertax.text.toString().trim(),
                imageURI.toString()
            )
        }


                if (internetState) {

                    viewModel.sendPostAPI(addProductModel)

                    viewModel.uiState().observe(viewLifecycleOwner) {

                        Log.d("AddProduct","updated data is $it")

                        if (it != null) {
                            render(it)
                        }

                    }

                } else {
                    requireActivity().toast("Internet Connection Lost !!")

                }



            }


    private fun getListOptions(it: ArrayList<String>?) {

        it?.let {
            val spinnerArrayAdapter = ArrayAdapter(
                requireActivity(), R.layout.simple_spinner_item, it
            )
            spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            fragment_addProductBinding.spinnerProductList.setAdapter(spinnerArrayAdapter)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backNavigate()

    }

    private fun backNavigate() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            findNavController().navigate(com.example.swipeproduct.R.id.action_AddProduct_fragment_to_ListingProduct_fragment)

        }
    }


}