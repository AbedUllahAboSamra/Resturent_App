package com.example.asd_side.screen

import android.content.Intent
import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.asd_side.Fragments.*
import com.example.asd_side.R
import com.example.asd_side.adapters.CategoryPagerAdapter
import com.example.asd_side.adapters.RecAllMalAdapter
import com.example.asd_side.adapters.RecTrindingNowAdapter
import com.example.asd_side.adapters.RecViewMealPlansAdapter
import com.example.asd_side.databinding.ActivityMainBinding
import com.example.asd_side.db.MyDataBase
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    //start declare variables
    lateinit var binding: ActivityMainBinding

    companion object {
        var numberOfitems = MutableLiveData<Int>()
    }
    //end declare variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolMyToolBar)

        numberOfitems.value = MyDataBase.arrCategorys.size + MyDataBase.arrMeals.size


        numberOfitems.observe(this, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                binding.numberOfitems.text = t.toString()

            }
        })

        for (item in SplachActivity.arrMeal) {
              }
        binding.bottomNavigation.setOnItemSelectedListener { it ->

            when (it.itemId) {


                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeFragment())
                        .commit()
                    it.setChecked(true)
                }
                R.id.search -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, SearchFragment())
                        .commit()

                    it.setChecked(true)
                }
                R.id.save -> {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, SavedFragment())
                        .commit()

                    it.setChecked(true)
                }
                R.id.Settings -> {
                   startActivity(Intent(this,ProfileActivity::class.java))
                }
            }


            return@setOnItemSelectedListener true
        }
        binding.btnProfile.setOnClickListener {

            startActivity(Intent(this, ProfileActivity::class.java))

        }
        binding.asdasd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SavedFragment())
                .commit()
            binding.bottomNavigation.setSelectedItemId(R.id.save)



        }

    }

    override fun onResume() {
        binding.bottomNavigation.setSelectedItemId(R.id.home)
        Picasso.get().load(SplachActivity.currentAccount!!.imageUri).into(binding.userImageView)

        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        Picasso.get().load(SplachActivity.currentAccount!!.imageUri).into(binding.userImageView)
    }
}