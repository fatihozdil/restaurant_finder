package com.example.kt.Fragments

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.R
import com.example.kt.databinding.FragmentRestaurantDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase


class RestaurantDetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var _binding: FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!
    var database: FirebaseDatabase? =
        FirebaseDatabase.getInstance("https://restayrantado-default-rtdb.firebaseio.com")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestaurantDetailBinding.inflate(layoutInflater)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.googleMap) as SupportMapFragment


        if (mapFragment is SupportMapFragment) {
            mapFragment.getMapAsync(this)
        }

        mapFragment.getMapAsync(this)


        val bundle = this.arguments

        val latitude = bundle!!.getDouble("latitude")
        val longitude = bundle!!.getDouble("longitude")
        val id = bundle!!.getString("id")
        val name = bundle!!.getString("name")
        val address = bundle!!.getString("address")
        val rating = bundle!!.getDouble("rating")
        val image_url = bundle!!.getString("image_url")
        val favoritesArrayList =
            bundle!!.getParcelableArrayList<FavoritesRestaurantModel>("favorites")!!
        val favoritesRestaurantModel =
            FavoritesRestaurantModel(
                name!!,
                longitude,
                latitude,
                address!!,
                id!!,
                rating!!,
                image_url!!
            )

        val check = checkIfAlreadyInFavorites(favoritesArrayList, id)
        if (check > -1) {
            binding.addFavotites.setImageResource(R.drawable.ic_baseline_favorite_24)

        } else {
            binding.addFavotites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

        binding.addFavotites.setOnClickListener {
            addFavorites(favoritesArrayList, id, favoritesRestaurantModel)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun addFavorites(
        favoritesArrayList: ArrayList<FavoritesRestaurantModel>,
        id: String,
        element: FavoritesRestaurantModel
    ) {
        val data = checkIfAlreadyInFavorites(favoritesArrayList, id)
        if (data > -1) {
            favoritesArrayList.removeAt(data)
            binding.addFavotites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            return
        }
        favoritesArrayList.add(element)
        binding.addFavotites.setImageResource(R.drawable.ic_baseline_favorite_24)
    }

    private fun checkIfAlreadyInFavorites(
        favoritesArrayList: ArrayList<FavoritesRestaurantModel>,
        id: String
    ): Int {
        for (item in favoritesArrayList.indices) {
            if (favoritesArrayList[item].id == id) {
                return item
            }

        }
        return -1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val bundle = this.arguments

        val latitude = bundle!!.getDouble("latitude")
        val longitude = bundle!!.getDouble("longitude")
        binding.RestaurantDetailTitle.text = bundle!!.getString("name")!!.uppercase()
        val zoomLevel = 15f
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))
    }
}