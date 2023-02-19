package com.example.kt.Adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.Fragments.AccountFragment
import com.example.kt.Fragments.HomeFragment
import com.example.kt.Fragments.SearchFragment

class TabPageAdapter(
    activity: FragmentActivity,
    private val tabCount: Int,
    private val name: String,
    private val email: String,
    private val favoritesArrayList : ArrayList<FavoritesRestaurantModel>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabCount
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> {
                val bundle = Bundle()
                val nextFrag = SearchFragment()
                bundle.putParcelableArrayList("favorites", favoritesArrayList)
                nextFrag.arguments = bundle
                return nextFrag
            }
            2 -> {

                val bundle = Bundle()
                val nextFrag = AccountFragment()
                bundle.putString("name", name)
                bundle.putString("email", email)
                bundle.putParcelableArrayList("favorites", favoritesArrayList)
                nextFrag.arguments = bundle
                return nextFrag
            }
            else -> HomeFragment()
        }
    }

}