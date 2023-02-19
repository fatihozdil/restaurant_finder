package com.example.kt.Adapters

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kt.Api.Business
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.Fragments.RestaurantDetailFragment
import com.example.kt.R
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class RestaurantSearchAdapter(
    val businessesList: MutableList<Business>,
    val favoritesArrayList: ArrayList<FavoritesRestaurantModel>
) :
    RecyclerView.Adapter<RestaurantSearchAdapter.ModelViewHolder>() {

    class ModelViewHolder(view: View, val favoritesArrayList: ArrayList<FavoritesRestaurantModel>) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.name)
        private val rating: TextView = view.findViewById(R.id.rating)
        private val flagImage: ImageView = view.findViewById(R.id.ver_img)
        private val address: TextView = view.findViewById(R.id.timing)
        private val view = view

        fun bindItems(item: Business) {
            name.setText(item.name)
            rating.setText("${item.rating}")
            val url = URL(item.image_url)
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            flagImage.setImageBitmap(bmp)
            address.text = item.location.address1
            view.setOnClickListener {
                val activity = view.context as AppCompatActivity
                val nextFrag = RestaurantDetailFragment()
                val bundle = Bundle()

                bundle.putDouble("latitude", item.coordinates.latitude)
                bundle.putDouble("longitude", item.coordinates.longitude)
                bundle.putString("name", item.name)
                bundle.putString("address", item.location.address1)
                bundle.putString("id", item.id)
                bundle.putString("image_url", item.image_url)
                bundle.putDouble("rating", item.rating)
                bundle.putParcelableArrayList("favorites",favoritesArrayList)


                nextFrag.arguments = bundle

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.SearchPage, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit()
                activity.supportFragmentManager.executePendingTransactions();

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_vertical_item, parent, false)
        return ModelViewHolder(view ,favoritesArrayList)
    }

    override fun getItemCount(): Int {
        return businessesList.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(businessesList.get(position))
    }
}