package com.example.asd_side.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.example.asd_side.Fragments.HomeFragment
import com.example.asd_side.databinding.CategoryDesignBinding
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.CategoryModel
import com.squareup.picasso.Picasso

class CategoryPagerAdapter(var context: Context, var arr: ArrayList<CategoryModel>) :
    PagerAdapter() {

    var db = MyDataBase(context)

    override fun getCount(): Int {
        return arr.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var binding =
            CategoryDesignBinding.inflate(LayoutInflater.from(container.context), null, false)
        container.addView(binding.root)

        binding.ratingPar.rating = arr[position].reting
        Picasso.get().load(arr[position].imageUrl).into(binding.imgCategoryImage)
        binding.tvCategoryName.text = arr[position].name
        binding.btnAdd.setOnClickListener {
            var i = db.addCategory(arr[position].id)
            if (!i) {
                Toast.makeText(context, "item in the bag", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnArchive.setOnClickListener {
            var i = db.addCategory(arr[position].id)
            if (!i) {
                Toast.makeText(context, "item in the bag", Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }


}