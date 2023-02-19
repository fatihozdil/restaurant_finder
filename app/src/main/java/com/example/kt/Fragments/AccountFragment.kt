package com.example.kt.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kt.FavoritesRestaurantModel
import com.example.kt.R
import com.example.kt.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(layoutInflater)
        val bundle = this.arguments
        val name = bundle!!.getString("name")
        val email = bundle!!.getString("email")
        val favoritesArrayList = bundle!!.getParcelableArrayList<FavoritesRestaurantModel>("favorites")

        binding.accountName.text = name
        binding.accountEmail.text = email

        binding.accountFavoritesButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putParcelableArrayList("favorites", favoritesArrayList)
            val nextFrag = FavoritesFragment()
            nextFrag.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.AccountPage, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()
            requireActivity().supportFragmentManager.executePendingTransactions();

        }

        return binding.root
    }
}