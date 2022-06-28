package com.example.asd_side.screen

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asd_side.adapters.RecAllMalAdapter
import com.example.asd_side.databinding.ActivityProfileBinding
import com.example.asd_side.screen.AddScreens.AddCategoryActivity
import com.example.asd_side.screen.AddScreens.AddMealActivity
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var MealsArr = SplachActivity().getMyMeals(SplachActivity.currentAccount!!.id)


        var allMalAdapterAdapter = RecAllMalAdapter(MealsArr)
        binding.RecViewAllMeal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.RecViewAllMeal.adapter = allMalAdapterAdapter


    }


    override fun onStart() {
        super.onStart()
        // btn back clicked
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }
        // end back clicked
        binding.numberMeals.text = SplachActivity.currentAccount!!.numberOfRecipes
        binding.numberOfFollowes.text = SplachActivity.currentAccount!!.numberOfFollower





        if (SplachActivity.currentAccount!!.accountType == "chef") {
            binding.leanerForChef.visibility = View.VISIBLE
            binding.btnADDMEAL.visibility = View.VISIBLE
            binding.RecViewAllMeal.visibility = View.VISIBLE
            binding.btnADDCategory.visibility = View.VISIBLE
            binding.numberOfFollowes.visibility= View.VISIBLE

        } else {
            binding.btnADDCategory.visibility = View.INVISIBLE
            binding.RecViewAllMeal.visibility = View.GONE
            binding.leanerForChef.visibility = View.INVISIBLE
            binding.btnADDMEAL.visibility = View.GONE
            binding.numberOfFollowes.visibility= View.INVISIBLE
            binding.numberMeals.visibility= View.INVISIBLE

        }



        binding.btnADDMEAL.setOnClickListener {
            startActivity(Intent(this, AddMealActivity::class.java))
        }
        binding.btnADDCategory.setOnClickListener {
            startActivity(Intent(this, AddCategoryActivity::class.java))
        }

//Start Code initialized Items
        binding.tvAccountName.text = SplachActivity.currentAccount!!.name
        Picasso.get().load(SplachActivity.currentAccount!!.imageUri).into(binding.imgUserImageView)
        binding.btnEditProfile.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        binding.btnEditProfile.setOnClickListener {
            var i = Intent(this, EditProfileActivity::class.java)
            startActivity(i)

        }
//end Code initialized Items

        binding.btnLogOut.setOnClickListener {
            var pref = getSharedPreferences("my", Context.MODE_PRIVATE)
            pref.edit().clear().commit()
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            this.finish()
        }
    }


}