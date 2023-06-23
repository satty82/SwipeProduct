package com.example.swipeproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ProductRecyclerView : RecyclerView.Adapter<ProductRecyclerView.ProductViewHolder>(){

    val types = Types.types

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

//        val binding = ProductListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_layout,parent,false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

//        holder.productNametv.text = types[0]
//        holder.productOfftv.text = types[1]
//        holder.cardView.setBackgroundResource(R.drawable.rv_background_gradient)

    }

    class ProductViewHolder( itemView : View) : RecyclerView.ViewHolder(itemView)
    {
//        val productNametv = itemView.findViewById<TextView>(R.id.productnametxt)
        val productOfftv = itemView.findViewById<TextView>(R.id.productnametxt)
        val cardView = itemView.findViewById<CardView>(R.id.card_view)



    }
}