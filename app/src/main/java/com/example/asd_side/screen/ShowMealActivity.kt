package com.example.asd_side.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asd_side.Fragments.HomeFragment
import com.example.asd_side.R
import com.example.asd_side.adapters.RecCommentsAdapter
import com.example.asd_side.adapters.RecStepsAdapter
import com.example.asd_side.adapters.RecViewMealPlansAdapter
import com.example.asd_side.adapters.RecycleIngredientsAdapter
import com.example.asd_side.databinding.ActivityShowMealBinding
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.CommentModle
import com.example.asd_side.model.CustomProgressDialog
import com.example.asd_side.model.MealModle
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.type.DateTime
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShowMealActivity : AppCompatActivity() {

    lateinit var mealModle: MealModle
    private val progressDialog = CustomProgressDialog()

    private val reqPickImageCode = 4150
    var imageUri: Uri? = null
    lateinit var binding: ActivityShowMealBinding


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMealBinding.inflate(layoutInflater)


        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()


        //start code  initialized mealItem
        var id = intent.getStringExtra("MealId")

        SplachActivity.arrMeal.forEach { it ->
            if (it.id == id) {
                mealModle = it
            }

        }
        var recCommentsAdapter = RecCommentsAdapter(this, mealModle.comments)
        binding.recComments.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recComments.adapter = recCommentsAdapter

        //end code  initialized mealItem

        val stroge = Firebase.storage
        val strogeRef = stroge.reference.child("images")


        // action bar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.title = null

        binding.cardImageView.visibility = View.GONE

        binding.toolbar

        Picasso.get().load(mealModle.imvMealImage).into(binding.appBarImage)
        binding.tvMealName.text = mealModle.name
        binding.tvSheffName.text = mealModle.sheName
        binding.ratingPar.rating = mealModle.ratingg
        binding.tvRatingAvg.text = mealModle.ratingg.toString()
        binding.tvDescribtion.text = mealModle.desribtion
        binding.tvTime.text = mealModle.time

        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }

        binding.btnTackImage.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, reqPickImageCode)

        }

        binding.btnPostComment.setOnClickListener {

            var date = "${Date().date}-${Date().month}-2022"
            var commentText = binding.edtCommentContent.text.toString()

            if ((imageUri != null && commentText.isNotEmpty()) || (imageUri != null)) {
                progressDialog.show(this)
                val bitmap = (binding.imageTacken.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
                val data = baos.toByteArray()
                val childref =
                    strogeRef.child(
                        System.currentTimeMillis().toString() + "ASDImages"
                    )
                var uploadTask = childref.putBytes(data)
                uploadTask.addOnFailureListener {
                    progressDialog.dialog.dismiss()
                    Toast.makeText(this, "cheek your network connect", Toast.LENGTH_SHORT)
                        .show()
                }.addOnSuccessListener { taskSnapshot ->
                    childref.downloadUrl.addOnSuccessListener { ittt ->
                        var commentModle = CommentModle(
                            id = "aasd",
                            commentDate = date,
                            commentContent = commentText,
                            commentImageUri = ittt.toString(),
                            userName = SplachActivity.currentAccount!!.name,
                            userImageUri = SplachActivity.currentAccount!!.imageUri,
                        )

                        var map = HashMap<String, Any>()
                        map["commentImageUri"] = ittt.toString()
                        map["commentContent"] = commentText
                        map["userName"] = "abed "
                        map["commentDate"] = date
                        map["userImageUri"] =
                            "https://images.unsplash.com/photo-1606829192980-9a547ffc918c?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=900&ixid=MnwxfDB8MXxyYW5kb218MHx8fHx8fHx8MTY1MDU4Nzc0MA&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1600"
                        FirebaseFirestore.getInstance().collection("meal")
                            .document(mealModle.id)
                            .collection("comment")
                            .add(map)
                            .addOnSuccessListener { it ->


                                SplachActivity().getAllCategory()
                                SplachActivity().getAllMeals()


                                Handler().postDelayed({
                                    HomeFragment.allMalAdapterAdapter.notifyDataSetChanged()
                                    HomeFragment.categoryAdapter.notifyDataSetChanged()
                                    HomeFragment.mealTrindingNowAdapter.notifyDataSetChanged()
                                    HomeFragment.recMealPlainAdapter.notifyDataSetChanged()

                                    recCommentsAdapter.notyfiDataSetChang(commentModle)
                                }, 0)




                                progressDialog.dialog.dismiss()
                                binding.cardImageView.visibility = View.GONE
                                imageUri = null
                                binding.edtCommentContent.setText("")
                            }
                            .addOnFailureListener {
                                progressDialog.dialog.dismiss()
                                Toast.makeText(
                                    this,
                                    "cheek your connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }


            } else if (imageUri == null && commentText.isNotEmpty()) {
                progressDialog.show(this)

                var commentModle = CommentModle(
                    id = "xxx",
                    commentDate = date,
                    commentContent = commentText,
                    commentImageUri = "",
                    userName = SplachActivity.currentAccount!!.name,
                    userImageUri = SplachActivity.currentAccount!!.imageUri,
                )

                var map = HashMap<String, Any>()
                map["commentImageUri"] = ""
                map["commentContent"] = commentText
                map["userName"] = "ahmed"
                map["commentDate"] = date
                map["userImageUri"] =
                    "https://images.unsplash.com/photo-1606829192980-9a547ffc918c?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=900&ixid=MnwxfDB8MXxyYW5kb218MHx8fHx8fHx8MTY1MDU4Nzc0MA&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1600"
                FirebaseFirestore.getInstance().collection("meal")
                    .document(mealModle.id)
                    .collection("comment")
                    .add(map)
                    .addOnSuccessListener {

                        SplachActivity().getAllCategory()
                        SplachActivity().getAllMeals()


                        Handler().postDelayed({
                            HomeFragment.allMalAdapterAdapter.notifyDataSetChanged()
                            HomeFragment.categoryAdapter.notifyDataSetChanged()
                            HomeFragment.mealTrindingNowAdapter.notifyDataSetChanged()
                            HomeFragment.recMealPlainAdapter.notifyDataSetChanged()

                            recCommentsAdapter.notyfiDataSetChang(commentModle)
                        }, 0)

                        binding.cardImageView.visibility = View.GONE
                        imageUri = null
                        binding.edtCommentContent.setText("")
                        progressDialog.dialog.dismiss()

                    }
                    .addOnFailureListener {

                        progressDialog.dialog.dismiss()

                        Toast.makeText(
                            this,
                            "cheek your connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else if (imageUri == null && commentText.isEmpty()) {
                Toast.makeText(this, "cant be empty", Toast.LENGTH_SHORT).show()
            }
        }

        //---------


        //initialized views

        Picasso.get().load(mealModle.imvMealImage).into(binding.appBarImage)



        binding.appbar.addOnOffsetChangedListener(
            object : AppBarLayout.OnOffsetChangedListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                    if (verticalOffset == 0) {
                        binding.btnBack.setCardBackgroundColor(Color.parseColor("#A4000000"))
                        binding.btnSave.setCardBackgroundColor(Color.parseColor("#A4000000"))
                        binding.btnShare.setCardBackgroundColor(Color.parseColor("#A4000000"))
                        binding.imgBack.setImageResource(R.drawable.ic_baseline_arrow_back_24)
                        binding.imgShare.setImageResource(R.drawable.ic_outline_ios_share_24)
                        binding.imgSave.setImageResource(R.drawable.ic_outline_archive_24)

                    } else if (Math.abs(verticalOffset) >= appBarLayout!!.getTotalScrollRange()) {
                        binding.btnBack.setCardBackgroundColor(Color.TRANSPARENT)
                        binding.btnSave.setCardBackgroundColor(Color.TRANSPARENT)
                        binding.btnShare.setCardBackgroundColor(Color.TRANSPARENT)

                        binding.imgBack.setImageResource(R.drawable.ic_round_arrow_back_24)
                        binding.imgShare.setImageResource(R.drawable.ic_outline_ios_share_24black)
                        binding.imgSave.setImageResource(R.drawable.ic_outline_archive_24black)


                    } else {
                        binding.btnBack.setCardBackgroundColor(Color.parseColor("#A4000000"))
                        binding.btnSave.setCardBackgroundColor(Color.parseColor("#A4000000"))
                        binding.btnShare.setCardBackgroundColor(Color.parseColor("#A4000000"))
                        binding.imgBack.setImageResource(R.drawable.ic_baseline_arrow_back_24)
                        binding.imgShare.setImageResource(R.drawable.ic_outline_ios_share_24)
                        binding.imgSave.setImageResource(R.drawable.ic_outline_archive_24)
                    }
                }

            })

        //start Code initialzed recycleIngredients
        var recycleIngredientsAdapter = RecycleIngredientsAdapter(this, mealModle.ingredients)
        binding.recycleIngredients.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recycleIngredients.adapter = recycleIngredientsAdapter
//start Code initialzed recycleIngredients


//start Code initialzed recSteps

        var recSteps = RecStepsAdapter(this, mealModle.steps)
        binding.recSteps.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recSteps.adapter = recSteps

//end Code initialzed recSteps


//start Code initialzed recComments


        // end Code initialzed reComments


        // start Coder initalized recyCleViewShowMoreMeal
        var array = ArrayList<MealModle>()
        SplachActivity.arrMeal.forEach { it ->
            if (it.category == mealModle.category) {
                array.add(it)
            }

        }

        binding.recyCleViewShowMoreMeal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var recMealPlainAdapter = RecViewMealPlansAdapter(this, array)
        binding.recyCleViewShowMoreMeal.adapter = recMealPlainAdapter


        // end Coder initalized recyCleViewShowMoreMeal

        var db = MyDataBase(this)
        binding.btnSave.setOnClickListener {
            var i = db.addMeal(id!!)
            if (!i) {
                Toast.makeText(this, "item in the bag", Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqPickImageCode && resultCode == RESULT_OK) {
            imageUri = data?.data
            if (imageUri != null) {
                binding.cardImageView.visibility = View.VISIBLE
                binding.imageTacken.setImageURI(imageUri)
            } else {
                binding.cardImageView.visibility = View.GONE

            }
        }

    }
}
