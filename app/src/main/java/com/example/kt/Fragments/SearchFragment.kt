package com.example.kt.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kt.APIRequest
import com.example.kt.Adapters.RestaurantSearchAdapter
import com.example.kt.Adapters.RestauratAdapter
import com.example.kt.Api.Business
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private val token =
        "DXBnyG5b38MyGVVvIyphhAdBfKHK0HQHXRXF9A0LUcjT17Ez984-wJSHEcNeT5KL-SRmfDoeIsoIJdvPvjrf-BJGZ5Qm54HgOIEkXuAZMgjN-NHzKABhl1AwD9yEYnYx"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_search, container, false)
        val button = inflate.findViewById<ImageButton>(R.id.search_button)
        val recyclerView = inflate.findViewById<RecyclerView>(R.id.RecyclerSearchedRestaurants)
        button.setOnClickListener {
            val location = inflate.findViewById<EditText>(R.id.search_edit_text)

            var url = "v3/businesses/search?location=${location.text}&&limit=4"
            println(url)

            val bundle = this.arguments
            var favoritesArrayList = bundle!!.getParcelableArrayList<FavoritesRestaurantModel>("favorites")
            searchRestaurants(recyclerView, url, favoritesArrayList!!)
        }
        return inflate


    }

    private fun searchRestaurants(recyclerView: RecyclerView, url: String, favoritesArrayList: ArrayList<FavoritesRestaurantModel>) {

        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }).build()


        val api = Retrofit.Builder()
            .baseUrl("https://api.yelp.com/")

            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = api.searchRestaurants(url)

                renderSearchedElements(recyclerView, response.businesses, favoritesArrayList)


            } catch (e: Exception) {
                Log.d("MainActivity", e.toString())
            }

        }
    }


    private fun renderSearchedElements(recyclerView: RecyclerView, response: List<Business>, favoritesArrayList: ArrayList<FavoritesRestaurantModel>) {

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = RestaurantSearchAdapter(response as MutableList<Business>, favoritesArrayList)
    }


}