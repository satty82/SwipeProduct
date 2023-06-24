package com.example.swipeproduct.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.swipeproduct.R
import com.example.swipeproduct.model.ProductListModel
import com.squareup.picasso.Picasso

class ProductRecyclerView(val context: Context, val productListModel: ProductListModel) : RecyclerView.Adapter<ProductRecyclerView.ProductViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_layout,parent,false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productListModel.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.productNametv.text = productListModel[position].product_name
        holder.productTypetv.text = productListModel[position].product_type
        holder.taxtv.text = productListModel[position].tax.toString()

        if(productListModel[position].image.equals(""))
        {
            holder.productImage.setImageDrawable(ContextCompat.getDrawable(context,
                R.drawable.imageplaceholder
            ))
        }else{

            Picasso.get()
                .load(productListModel[position].image)
                .placeholder(R.drawable.imageplaceholder)
                .fit()
                .noFade()
                .error(R.drawable.no_network)
                .into(holder.productImage)

        }



        holder.cardView.setBackgroundResource(R.drawable.rv_background_gradient)

    }

    class ProductViewHolder( itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val productNametv = itemView.findViewById<TextView>(R.id.productnametxt)
        val productTypetv = itemView.findViewById<TextView>(R.id.productType)
        val taxtv = itemView.findViewById<TextView>(R.id.productTax)
        val productImage = itemView.findViewById<ImageView>(R.id.productimage)
        val cardView = itemView.findViewById<LinearLayout>(R.id.rootview)



    }
}