package com.example.swipeproduct.UI

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.swipeproduct.R
import com.example.swipeproduct.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }
    lateinit var navController : NavController

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = binding.root
        setContentView(view)


        // inflating FragmentViewContainer
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

        val title = view.findViewById<TextView>(R.id.toolbarTitle)
        title.text = getString(R.string.swipe_product)

        //Hide statusBar
        window.decorView.windowInsetsController!!.hide(WindowInsets.Type.statusBars())




    }
}