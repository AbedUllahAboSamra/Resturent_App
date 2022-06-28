package com.example.asd_side.screen

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.asd_side.R
import com.example.asd_side.databinding.ActivityEditProfileBinding
import com.example.asd_side.model.CustomProgressDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {

    private val reqPickImageCode = 4150
    var imageUri: Uri? = null
    private val progressDialog = CustomProgressDialog()

    lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stroge = Firebase.storage
        val strogeRef = stroge.reference.child("images")


        Picasso.get().load(SplachActivity.currentAccount!!.imageUri).into(binding.imgUserImageView)
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }

        binding.edtDisplayName.setText(SplachActivity.currentAccount!!.name.toString())

        binding.layPio.visibility = View.GONE

        binding.btnToogleBotton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.layPio.visibility = View.VISIBLE
                binding.btnSave.setTextColor(Color.BLACK)
            } else {
                binding.layPio.visibility = View.GONE
            }

        }

        binding.btnTackImage.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, reqPickImageCode)
        }

        binding.btnSave.setOnClickListener {
            var newName = binding.edtDisplayName.text.toString()
            var newPio = binding.edtBio.text.toString()
            var newAbout = binding.edtAboutMe.text.toString()

            if (binding.btnToogleBotton.isChecked)
            {

                SplachActivity.currentAccount!!.accountType = "chef"
                SplachActivity.currentAccount!!.bio = newPio

                if (newName.isNotEmpty()  &&
                    newPio.isNotEmpty() &&
                    newAbout.isNotEmpty()
                ) {
                    progressDialog.show(this, "UPDATING")
                    SplachActivity.currentAccount!!.name = newName
                    SplachActivity.currentAccount!!.description = newAbout

                    if (imageUri != null) {

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
                                SplachActivity.currentAccount!!.imageUri = ittt.toString()

                                var map = HashMap<String, Any>()
                                map["imageUri"] = SplachActivity.currentAccount!!.imageUri
                                map["accountType"] = SplachActivity.currentAccount!!.accountType
                                map["description"] =
                                    SplachActivity.currentAccount!!.description ?: ""
                                map["bio"] = SplachActivity.currentAccount!!.bio ?: ""
                                map["id"] = SplachActivity.currentAccount!!.id
                                map["name"] = SplachActivity.currentAccount!!.name
                                map["numberOfFollower"] = "0"
                                map["numberOfRecipes"] = "0"



                                FirebaseFirestore.getInstance().collection("users")
                                    .document(SplachActivity.currentAccount!!.id)
                                    .set(map)
                                    .addOnSuccessListener {
                                        progressDialog.dialog.dismiss()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
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
                    else {


                        var map = HashMap<String, Any>()
                        map["imageUri"] = SplachActivity.currentAccount!!.imageUri
                        map["accountType"] = SplachActivity.currentAccount!!.accountType
                        map["description"] =
                            SplachActivity.currentAccount!!.description ?: ""
                        map["bio"] = SplachActivity.currentAccount!!.bio ?: ""
                        map["id"] = SplachActivity.currentAccount!!.id
                        map["name"] = SplachActivity.currentAccount!!.name
                        map["numberOfFollower"] = ""
                        map["numberOfRecipes"] = ""
                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(SplachActivity.currentAccount!!.id)
                            .update(map)
                            .addOnSuccessListener {
                                progressDialog.dialog.dismiss()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()

                            }.addOnFailureListener {
                                progressDialog.dialog.dismiss()
                                Toast.makeText(
                                    this,
                                    "cheek your connection",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                    }


                }


            } else {
                SplachActivity.currentAccount!!.accountType = "user"

                if (newName.isNotEmpty() && newName != SplachActivity.currentAccount!!.name ||
                    newAbout.isNotEmpty()
                ) {

                    progressDialog.show(this, "UPDATING")
                    SplachActivity.currentAccount!!.name = newName
                    SplachActivity.currentAccount!!.description = newAbout

                    if (imageUri != null) {

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
                                SplachActivity.currentAccount!!.imageUri = ittt.toString()

                                var map = HashMap<String, Any>()
                                map["imageUri"] = SplachActivity.currentAccount!!.imageUri
                                map["accountType"] = SplachActivity.currentAccount!!.accountType
                                map["description"] =
                                    SplachActivity.currentAccount!!.description ?: ""
                                map["bio"] = SplachActivity.currentAccount!!.bio ?: ""
                                map["id"] = SplachActivity.currentAccount!!.id
                                map["name"] = SplachActivity.currentAccount!!.name
                                map["numberOfFollower"] = "0"
                                map["numberOfRecipes"] = "0"



                                FirebaseFirestore.getInstance().collection("users")
                                    .document(SplachActivity.currentAccount!!.id)
                                    .set(map)
                                    .addOnSuccessListener {
                                        progressDialog.dialog.dismiss()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
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
                    else {


                        var map = HashMap<String, Any>()
                        map["imageUri"] = SplachActivity.currentAccount!!.imageUri
                        map["accountType"] = SplachActivity.currentAccount!!.accountType
                        map["description"] =
                            SplachActivity.currentAccount!!.description ?: ""
                        map["bio"] = SplachActivity.currentAccount!!.bio ?: ""
                        map["id"] = SplachActivity.currentAccount!!.id
                        map["name"] = SplachActivity.currentAccount!!.name
                        map["numberOfFollower"] = ""
                        map["numberOfRecipes"] = ""
                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(SplachActivity.currentAccount!!.id)
                            .update(map)
                            .addOnSuccessListener {
                                progressDialog.dialog.dismiss()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()

                            }.addOnFailureListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reqPickImageCode && resultCode == RESULT_OK) {
            imageUri = data?.data
            if (imageUri != null) {
                binding.imgUserImageView.setImageURI(imageUri)
                binding.btnSave.setTextColor(Color.BLACK)
            }
        }
    }

}