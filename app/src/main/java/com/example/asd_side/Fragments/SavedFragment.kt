package com.example.asd_side.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asd_side.R
import com.example.asd_side.adapters.RecSaveCategoryAdapter
import com.example.asd_side.adapters.RecSaveMeal
import com.example.asd_side.adapters.RecTrindingNowAdapter
import com.example.asd_side.databinding.FragmentSavedBinding
import com.example.asd_side.databinding.FragmentSearchBinding
import com.example.asd_side.db.MyDataBase


class SavedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentSavedBinding.inflate(layoutInflater)


        var adapter = RecSaveMeal(requireContext(), MyDataBase.arrMeals)
        binding.recycleViewMeals.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recycleViewMeals.adapter = adapter


        var add = RecSaveCategoryAdapter(requireContext(), MyDataBase.arrCategorys)

        binding.recycleViewCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.recycleViewCategory.adapter = add




        return binding.root

    }
}