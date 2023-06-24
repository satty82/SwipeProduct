package com.example.swipeproduct.UI.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.swipeproduct.databinding.FragmentAddProductBinding
import com.example.swipeproduct.model.ProductListModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import java.util.ArrayList


class AddProduct : Fragment() {

    lateinit var fragment_addProductBinding : FragmentAddProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragment_addProductBinding= FragmentAddProductBinding.
        inflate(inflater, container, false)

        val bundle = this.arguments

        bundle?.let {
            val list = it.getStringArrayList("listOption")

            getListOptions(list)
        }


        return fragment_addProductBinding.root
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

       requireActivity().onBackPressedDispatcher.addCallback(this){

            findNavController().navigate(com.example.swipeproduct.R.id.action_AddProduct_fragment_to_ListingProduct_fragment)

        }
    }

}