package com.example.asd_side.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.asd_side.R
import com.example.asd_side.databinding.ActivitySignUpBinding
import com.example.asd_side.model.AccountModel
import com.example.asd_side.model.CustomProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    //start declare variables
    lateinit var binding: ActivitySignUpBinding
    var btnTrue = true
    private val progressDialog = CustomProgressDialog()

    //end declare variables


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //start initialization
        var opra = intent.getIntExtra("opera", 0)
        if (opra == 1) {
            binding.tvScreenText.text =
                "Welcome back! You're just a tap away from something delicious."
            binding.layDisplayName.visibility = View.GONE
            binding.layConfirmPassword.visibility = View.GONE
            binding.layout.visibility = View.GONE
            binding.tvBtn.text = "START COOKING!"
            binding.tvScreenName.text = "Log In"


        } else {
            binding.tvScreenText.text = "Save delicious recipes and get personalized content."
            binding.tvScreenName.text = "Sign Up"
            binding.layDisplayName.visibility = View.VISIBLE
            binding.layConfirmPassword.visibility = View.VISIBLE
            binding.layout.visibility = View.VISIBLE
            binding.tvBtn.text = "FINISH"

        }
        //end initialization


        // back to login Activity
        binding.btnBack.setOnClickListener {
            this.onBackPressed()
        }

        //start btn Save receive
        binding.btnTrue.setOnClickListener {
            if (btnTrue) {
                btnTrue = false
                binding.imgDone.setImageResource(R.drawable.ic_baseline_brightness_1_24)
            } else {
                binding.imgDone.setImageResource(R.drawable.ic_outline_done_24)
                btnTrue = true
            }
        }
        //end btn Save receive

        binding.btnCreate.setOnClickListener {
            var email = binding.edtEmail.text.toString()
            var password = binding.edtPassword.text.toString()

            if (opra == 0) {
                var confrmPassword = binding.edtConfirmPassword.text.toString()
                var name = binding.edtDisplayName.text.toString()

                if (email.isNotEmpty() && (password.length >= 6) && password == confrmPassword && name.isNotEmpty()) {

                    progressDialog.show(this, "LOADING...")
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { it ->
                            var map = HashMap<String, Any>()
                            map.put("id", it.user!!.uid)
                            map.put("name", name)
                            map.put(
                                "imageUri",
                                "https://lh3.googleusercontent.com/-cXXaVVq8nMM/AAAAAAAAAAI/AAAAAAAAAKI/_Y1WfBiSnRI/photo.jpg?sz=500"
                            )
                            map.put("bio", "")
                            map.put("description", "")
                            map.put("password", password)
                            map.put("numberOfFollower", "")
                            map.put("numberOfRecipes", "")
                            map.put("accountType", "user")

                            var accountModel=AccountModel(id=it.user!!.uid,name=name,
                                imageUri = "https://lh3.googleusercontent.com/-cXXaVVq8nMM/AAAAAAAAAAI/AAAAAAAAAKI/_Y1WfBiSnRI/photo.jpg?sz=500",
                                bio = "",
                                description="",
                                numberOfFollower="",
                                numberOfRecipes="",
                                accountType="user",

                                )

                            FirebaseFirestore.getInstance().collection("users")
                                .document(it.user!!.uid)
                                .set(map)
                                .addOnSuccessListener {
                                    SplachActivity.currentAccount = accountModel
                                    progressDialog.dialog.dismiss()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }.addOnFailureListener {
                                    progressDialog.dialog.dismiss()
                                    Toast.makeText(
                                        this,
                                        "Conform your phone connect with internet ",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }


                        }.addOnFailureListener {
                            progressDialog.dialog.dismiss()
                            Toast.makeText(
                                this,
                                "Conform your phone connect with internet ",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                } else {
                    Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show()
                }

            } else {
                if (email.isNotEmpty() && (password.length >= 6)) {

                    progressDialog.show(this, "LOADING...")
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)

                        .addOnSuccessListener { it ->
                            for (item in SplachActivity.arrAllAccounts) {
                                if (item.id == it.user!!.uid) {
                                    SplachActivity.currentAccount = item
                                }
                            }
                            progressDialog.dialog.dismiss()

                            var diloge = AlertDialog.Builder(this)

                            diloge.setTitle("Save Login")
                            diloge.setNegativeButton("cancel") { d, _ ->
                                d.dismiss()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                            diloge.setPositiveButton("save") { d, _ ->
                                var pref = getSharedPreferences("my",Context.MODE_PRIVATE)
                                var edit = pref.edit()
                                edit.putString("id",it.user!!.uid)
                                edit.apply()

                                d.dismiss()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }


                            diloge.show()


                        }
                        .addOnFailureListener {
                            progressDialog.dialog.dismiss()
                            Toast.makeText(
                                this,
                                "email or password is incorrect ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }


                } else {
                    Toast.makeText(this, "email or password is incorrect ", Toast.LENGTH_SHORT)
                        .show()

                }

            }


        }


    }


}