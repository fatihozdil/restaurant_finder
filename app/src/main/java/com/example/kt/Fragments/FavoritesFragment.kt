package com.example.kt.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kt.Adapters.FavoritesAdapter

import com.example.kt.FavoritesRestaurantModel
import com.example.kt.R


class FavoritesFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = this.arguments
        val inflate = inflater.inflate(R.layout.fragment_favorites, container, false)
        val recyclerView = inflate.findViewById<RecyclerView>(R.id.RecyclerFavoritesRestaurants)
        val favoritesArrayList = bundle!!.getParcelableArrayList<FavoritesRestaurantModel>("favorites")
       if(favoritesArrayList!!.size > 0){
           println(favoritesArrayList)

           renderSearchedElements(recyclerView, favoritesArrayList!!)
       }
        // Inflate the layout for this fragment
        return inflate
    }
    private fun renderSearchedElements(recyclerView: RecyclerView, favoritesArrayList: ArrayList<FavoritesRestaurantModel>) {

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = FavoritesAdapter( favoritesArrayList)
    }


}