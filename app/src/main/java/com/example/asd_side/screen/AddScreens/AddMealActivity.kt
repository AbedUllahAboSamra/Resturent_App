package com.example.asd_side.screen.AddScreens

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asd_side.Fragments.HomeFragment
import com.example.asd_side.adapters.StepsAdapter
import com.example.asd_side.adapters.ingredientAdapter
import com.example.asd_side.databinding.ActivityAddMealBinding
import com.example.asd_side.model.CustomProgressDialog
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.MainActivity
import com.example.asd_side.screen.ProfileActivity
import com.example.asd_side.screen.SplachActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class AddMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddMealBinding

    companion object {
        var stepsArray = HashMap<String, String>()
        var inteArray = HashMap<String, String>()
    }

    private val progressDialog = CustomProgressDialog()


    private val reqPickImageCode = 4150
    var imageUri: Uri? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imgUserImageView.setOnClickListener {
        }
        val stroge = Firebase.storage
        val strogeRef = stroge.reference.child("images")


        var arrCategorysForSppinner = ArrayList<String>()
        for (i in SplachActivity.arrCategorys) {
            arrCategorysForSppinner.add(i.name)
        }
        var spinnerAdapter = ArrayAdapter<String>(
            this, R.layout.simple_spinner_item, arrCategorysForSppinner
        )
        binding.spnCategory.adapter = spinnerAdapter
        binding.spnCategory.setSelection(0)

        //steps rec

        var stepsAdapter = StepsAdapter()
        binding.recSteps.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recSteps.adapter = stepsAdapter

        var inteAdapter = ingredientAdapter()
        binding.recIngredients.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recIngredients.adapter = inteAdapter

        binding.btnTackImage.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, reqPickImageCode)
        }


        binding.btnAdd.setOnClickListener {

            var name = binding.edtMealName.text.toString()
            var describtion = binding.edtDesribtion.text.toString()
            var time = binding.edtTime.text.toString()
            var category = binding.spnCategory.selectedItem.toString()
            if (imageUri != null && time.isNotEmpty() && inteArray.isNotEmpty() && stepsArray.isNotEmpty() && name.isNotEmpty() && describtion.isNotEmpty()
            ) {


                progressDialog.show(this)

                val bitmap = (binding.imgUserImageView.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                val childref =
                    strogeRef.child(System.currentTimeMillis().toString() + "ASDImages")

                var uploadTask = childref.putBytes(data)
                uploadTask.addOnFailureListener {
                    Toast.makeText(this, "cheek your network connect", Toast.LENGTH_SHORT)
                        .show()

                }.addOnSuccessListener { taskSnapshot ->


                    childref.downloadUrl.addOnSuccessListener { ittt ->

                        var map = HashMap<String, Any>()
                        map["imvMealImage"] = ittt.toString()
                        map["name"] = name
                        map["desribtion"] = describtion
                        map["time"] = time
                        map["sheName"] = SplachActivity.currentAccount!!.name
                        map["imageChefUir"] = SplachActivity.currentAccount!!.imageUri
                        map["ratingg"] = "0"
                        map["category"] = category
                        map["cheefId"] = SplachActivity.currentAccount!!.id

                        FirebaseFirestore.getInstance().collection("meal")
                            .add(map)
                            .addOnSuccessListener {

                                FirebaseFirestore.getInstance().collection("meal")
                                    .document(it.id)
                                    .collection("steps")
                                    .add(stepsArray)



                                FirebaseFirestore.getInstance().collection("meal")
                                    .document(it.id)
                                    .collection("Ingredients")
                                    .add(inteArray)

                                SplachActivity().getAllMeals()
                                SplachActivity().getAllCategory()



                                Handler().postDelayed({
                                    HomeFragment.allMalAdapterAdapter.notifyDataSetChanged()
                                    HomeFragment.categoryAdapter.notifyDataSetChanged()
                                    HomeFragment.mealTrindingNowAdapter.notifyDataSetChanged()
                                    HomeFragment.recMealPlainAdapter.notifyDataSetChanged()
                                    progressDialog.dialog.dismiss()
                                    inteArray.clear()
                                    stepsArray.clear()
                                    startActivity(Intent(this, ProfileActivity::class.java))
                                    finish()
                                }, 2000)
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
            }else {
                Toast.makeText(this, "fill all field", Toast.LENGTH_SHORT).show()
            }


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqPickImageCode && resultCode == RESULT_OK) {
            imageUri = data?.data
            if (imageUri != null) {
                binding.imgUserImageView.setImageURI(imageUri)
            }
        }

    }
}