package com.example.kt.Activities

import android.os.Bundle
import android.os.Parcelable
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kt.APIRequest
import com.example.kt.Adapters.RestauratAdapter
import com.example.kt.Adapters.TabPageAdapter
import com.example.kt.Api.Business
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.R
import com.example.kt.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity() {

    private val token =
        "DXBnyG5b38MyGVVvIyphhAdBfKHK0HQHXRXF9A0LUcjT17Ez984-wJSHEcNeT5KL-SRmfDoeIsoIJdvPvjrf-BJGZ5Qm54HgOIEkXuAZMgjN-NHzKABhl1AwD9yEYnYx"


    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val name = bundle!!.getString("name")
        val email = bundle!!.getString("email")
        val favoritesArrayList =
            bundle!!.getParcelableArrayList<FavoritesRestaurantModel>("favorites")
        setUpTabBar(name!!, email!!,favoritesArrayList!!)
        getRestaurants(favoritesArrayList)


    }


    private fun setUpTabBar(name: String, email: String, favoritesArrayList: ArrayList<FavoritesRestaurantModel>) {

        val adapter = TabPageAdapter(this, tabLayout.tabCount, name, email, favoritesArrayList)

        binding.viewpager.isUserInputEnabled = false;
        binding.viewpager.adapter = adapter



        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun getRestaurants(favoritesArrayList: ArrayList<FavoritesRestaurantModel>) {


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
                val response = api.getRestaurant()
                renderElements(response.businesses,favoritesArrayList)


            } catch (e: Exception) {
                Log.d("MainActivity", e.toString())
            }

        }
    }

    private fun renderElements(response: List<Business>, favoritesArrayList: ArrayList<FavoritesRestaurantModel>) {


        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerRetaurants)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = RestauratAdapter(response as MutableList<Business>, favoritesArrayList)

    }
    
}