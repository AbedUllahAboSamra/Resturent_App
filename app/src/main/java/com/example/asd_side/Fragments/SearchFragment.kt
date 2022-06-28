package com.example.asd_side.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.R
import com.example.asd_side.adapters.RecAllMalAdapter
import com.example.asd_side.adapters.RecFeatuedCulinaryAdapter
import com.example.asd_side.databinding.FragmentSearchBinding
import com.example.asd_side.model.AccountModel
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.SplachActivity
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {


    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        var arr1 = ArrayList<MealModle>()
        var arr2 = ArrayList<AccountModel>()
        var accounts = ArrayList<AccountModel>()

        var recFeatuedCulinaryAdapter = RecFeatuedCulinaryAdapter(arr2)

        for (i in SplachActivity.arrAllAccounts) {
            if (i.accountType == "chef") {
                accounts.add(i)
            }
        }

        for (it in accounts) {
            arr2.add(it)
        }



        for (it in SplachActivity.arrMeal) {
            arr1.add(it)
        }

        var allMalAdapterAdapter = RecAllMalAdapter(arr1)

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s?.isNotEmpty() == true) {
                    arr1.clear()
                    arr2.clear()
                    for (it in SplachActivity.arrMeal) {
                        if (it.name.toLowerCase(Locale.getDefault()).contains(
                                s.toString().toLowerCase(
                                    Locale.getDefault()
                                )
                            ) || it.category.toLowerCase(Locale.getDefault()).contains(
                                s.toString().toLowerCase(
                                    Locale.getDefault()
                                )
                            ) || it.sheName.toLowerCase(Locale.getDefault())
                                .contains(s.toString().toLowerCase(Locale.getDefault()))
                            || it.desribtion.toLowerCase(Locale.getDefault())
                                .contains(s.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            arr1.add(it)
                            allMalAdapterAdapter.notifyData()

                        }


                    }
                    allMalAdapterAdapter.notifyData()

                    for (it in accounts) {
                        if (it.name.toLowerCase(Locale.getDefault()).contains(
                                s.toString().toLowerCase(
                                    Locale.getDefault()
                                )
                            ) || it.bio!!.toLowerCase(Locale.getDefault()).contains(
                                s.toString().toLowerCase(
                                    Locale.getDefault()
                                )
                            ) || it.description!!.toLowerCase(Locale.getDefault())
                                .contains(s.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            arr2.add(it)


                        }


                    }
                    recFeatuedCulinaryAdapter.dateNotyfi()


                } else {
                    arr1.clear()
                    arr2.clear()
                    for (it in SplachActivity.arrMeal) {
                        arr1.add(it)
                    }
                    allMalAdapterAdapter.notifyData()
                    for (it in accounts) {
                        arr2.add(it)
                    }
                    recFeatuedCulinaryAdapter.dateNotyfi()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // start Code meal trending recycle

        binding.RecViewAllMeal.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RecViewAllMeal.adapter = allMalAdapterAdapter

        // end code meal trending


        //start Code Featuerd culinary paterns

        binding.RecViewFeaturedCulinaryPartners.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RecViewFeaturedCulinaryPartners.adapter = recFeatuedCulinaryAdapter

        //end Code Featuerd culinary paterns


        return binding.root
    }
}