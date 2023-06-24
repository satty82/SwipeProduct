package com.example.swipeproduct.UI.fragment

import android.R
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.swipeproduct.databinding.FragmentAddProductBinding
import com.example.swipeproduct.model.AddProductModel
import com.example.swipeproduct.repository.ProductRepository
import com.example.swipeproduct.viewmodels.MainViewModel
import com.example.swipeproduct.viewmodels.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddProduct : Fragment() {

    lateinit var fragment_addProductBinding : FragmentAddProductBinding
    private val pickImage = 100
    private var imageURI : Uri? = null
    lateinit var viewModel : MainViewModel

    val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data
                this.imageURI = data?.data!!
                Log.d("AddProduct","Image is $imageURI")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragment_addProductBinding= FragmentAddProductBinding.
        inflate(inflater, container, false)
        val repository = ProductRepository()

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(repository)).get(MainViewModel::class.java)

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

    private fun getPhoto() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(intent)

    }

    private fun validation() {

        with(fragment_addProductBinding){

            if(enterProductName.text.toString().trim().equals("")){
                tienterProductName.error ="Type Product Name"

            }else if(enterSellingPrice.text.toString().trim().equals("")){

                tienterSellingPrice.error ="Type Selling Price"
            }else if(entertax.text.toString().trim().equals("")){

                tientertax.error ="Type Product Name"
            }else{

                tienterProductName.error = null
                tienterSellingPrice.error= null
                tientertax.error= null

                Log.d("AddProduct","validation is $imageURI")

                val addProductModel = AddProductModel(enterProductName.text.toString().trim(),
                    spinnerProductList.selectedItem.toString(),
                    enterSellingPrice.text.toString().trim(),
                    entertax.text.toString().trim(),
                    imageURI.toString()
                    )

                       viewModel.sendPostAPI(addProductModel)

            }
        }

    }


    private fun getListOptions(it: ArrayList<String>?) {

        it?.let{
            val spinnerArrayAdapter  = ArrayAdapter(
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
        requireActivity().onBackPressedDispatcher.addCallback(this){

            findNavController().navigate(com.example.swipeproduct.R.id.action_AddProduct_fragment_to_ListingProduct_fragment)

        }    }


}