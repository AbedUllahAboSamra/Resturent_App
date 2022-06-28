package com.example.asd_side.screen.AddScreens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.asd_side.Fragments.HomeFragment
import com.example.asd_side.databinding.ActivityAddCategoryBinding
import com.example.asd_side.model.CustomProgressDialog
import com.example.asd_side.screen.MainActivity
import com.example.asd_side.screen.ProfileActivity
import com.example.asd_side.screen.SplachActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class AddCategoryActivity : AppCompatActivity() {

    //start declare variables

    lateinit var binding: ActivityAddCategoryBinding
    private val progressDialog = CustomProgressDialog()


    private val reqPickImageCode = 4150
    var imageUri: Uri? = null

    //end declare variables


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stroge = Firebase.storage
        val strogeRef = stroge.reference.child("images")

        binding.btnFinish.setOnClickListener {
            var neme = binding.edtCategoryName.text.toString()
            var description = binding.edtCategoryDiscribtion.text.toString()
            if (neme != null && description != null && imageUri != null) {
                progressDialog.show(this)

                if (imageUri != null) {
                    val bitmap = (binding.imgImageView.drawable as BitmapDrawable).bitmap
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
                            map["uri"] = ittt.toString()
                            map["description"] = description
                            map["name"] = neme
                            FirebaseFirestore.getInstance().collection("category")
                                .add(map)
                                .addOnSuccessListener {
                                    SplachActivity().getAllMeals()
                                    SplachActivity().getAllCategory()



                                    Handler().postDelayed({
                                        HomeFragment.allMalAdapterAdapter.notifyDataSetChanged()
                                        HomeFragment.categoryAdapter.notifyDataSetChanged()
                                        HomeFragment.mealTrindingNowAdapter.notifyDataSetChanged()
                                        HomeFragment.recMealPlainAdapter.notifyDataSetChanged()

                                        progressDialog.dialog.dismiss()
                                        startActivity(Intent(this, ProfileActivity::class.java))
                                        finish()
                                    },2000)

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


                }

            }
        }
        binding.fabAddORDelete.setOnClickListener {
           var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, reqPickImageCode)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqPickImageCode && resultCode == RESULT_OK) {
            imageUri = data?.data
            if (imageUri != null) {
                binding.imgImageView.setImageURI(imageUri)
                binding.imgImageView.visibility = View.VISIBLE
                binding.tvCardTxt.visibility = View.INVISIBLE
            }
        }

    }
}

