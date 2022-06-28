package com.example.asd_side.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.asd_side.R
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.AccountModel
import com.example.asd_side.model.CategoryModel
import com.example.asd_side.model.CommentModle
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.AddScreens.AddMealActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SplachActivity : AppCompatActivity() {

    companion object {
        var arrCategorys = ArrayList<CategoryModel>()
        var arrMeal = ArrayList<MealModle>()
        var arrMealTrindingNow = ArrayList<MealModle>()
        var arrAllAccounts = ArrayList<AccountModel>()
        var currentAccount: AccountModel? = null
    }

    var fireStore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        var pref = getSharedPreferences("my", Context.MODE_PRIVATE)
        var id = pref.getString("id", null)
        getAllCategory()
        getAllMeals()
        getAllAccounts()

        var db = MyDataBase(this)

        Handler().postDelayed({
            db.getMallItems()
            db.getCategoryItems()
            MainActivity.numberOfitems.value = MyDataBase.arrCategorys.size + MyDataBase.arrMeals.size

            if (id == null) {
                Log.e("ASD","nul== id ")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            } else {
                Log.e("ASD","nul=/ id ")
                Log.e("ASD","$id ")

                for (item in arrAllAccounts) {
                    if (item.id == id) {
                        currentAccount = item
                        Log.e("ASD","${currentAccount!!.name} ")

                        break
                    }
                }
                if (currentAccount != null) {

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                } else {

                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                }
            }

        }, 3000)
    }

    fun getAllCategory() {
        arrCategorys.clear()

        fireStore.collection("category")
            .get()
            .addOnSuccessListener { it ->
                var allCategorys = it.documents
                for (item in allCategorys) {
                    var ff = (Math.random() * (5 - .5) + .5).toFloat()
                    var modle = CategoryModel(

                        name = item["name"].toString(),
                        discribtion = item["description"].toString(),
                        imageUrl = item["uri"].toString(),
                        id = item.id,
                        reting = ff
                    )

                    arrCategorys.add(modle)

                }


            }

    }

    fun getAllMeals() {
        arrMeal.clear()
        arrMealTrindingNow.clear()

        fireStore.collection("meal")
            .get()
            .addOnSuccessListener { it ->

                for (item in it) {
                    var stepsArr = ArrayList<String>()
                    var commentsArr = ArrayList<CommentModle>()
                    var intelgemt = ArrayList<String>()


//start  get all steps to one meal
                    fireStore.collection("meal")
                        .document(item.id)
                        .collection("steps")
                        .get()
                        .addOnSuccessListener { steps ->
                            for (stp in steps) {
                                for (s in 0 until stp.data.size) {
                                    stepsArr.add(stp.get("$s").toString())
                                }


                            }
                        }
//end  get all steps to one meal

                    fireStore.collection("meal")
                        .document(item.id)
                        .collection("Ingredients")
                        .get()
                        .addOnSuccessListener { Ingredients ->

                            for (stp in Ingredients) {
                                for (s in 0 until stp.data.size) {
                                    intelgemt.add(stp.get("$s").toString())
                                }
                            }

                        }
//start  get all comment to one meal

                    fireStore.collection("meal")
                        .document(item.id)
                        .collection("comment")
                        .get()
                        .addOnSuccessListener { comments ->

                            for (comment in comments) {
                                var commentModle = CommentModle(
                                    id = comment.id,
                                    commentDate = comment["commentDate"].toString(),
                                    commentContent = comment["commentContent"].toString(),
                                    userName = comment["userName"].toString(),
                                    userImageUri = comment["userImageUri"].toString(),
                                    commentImageUri = comment["commentImageUri"].toString(),
                                )
                                commentsArr.add(commentModle)

                            }
                        }
                    //end  get all comment to one meal

                    var i = MealModle(
                        id = item.id,
                        name = item["name"].toString(),
                        imageChefUir = item["imageChefUir"].toString(),
                        imvMealImage = item["imvMealImage"].toString(),
                        category = item["category"].toString(),
                        sheName = item["sheName"].toString(),
                        ratingg = item["ratingg"].toString().toFloat(),
                        desribtion = item["desribtion"].toString(),
                        cheefId = item["cheefId"].toString(),
                        ingredients = intelgemt,
                        time = item["time"].toString(),
                        steps = stepsArr,
                        comments = commentsArr,
                    )

                    if (i.category == "Trending Now") {
                        arrMealTrindingNow.add(i)
                    }
                    arrMeal.add(i)

                }
            }.addOnFailureListener { it ->

            }

    }

    fun getAllAccounts() {
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnSuccessListener { accounts ->
                for (account in accounts) {
                    var accountModel = AccountModel(
                        id = account["id"].toString(),
                        imageUri = account["imageUri"].toString(),
                        numberOfFollower = account["numberOfFollower"].toString(),
                        numberOfRecipes = account["numberOfRecipes"].toString(),
                        bio = account["bio"].toString(),
                        name = account["name"].toString(),
                        accountType = account["accountType"].toString(),
                        description = account["description"].toString(),
                    )
                    arrAllAccounts.add(accountModel)
                }

            }
    }

    fun getMyMeals(id: String): ArrayList<MealModle> {
        var arrChefMeals = ArrayList<MealModle>()

        for (i in arrMeal) {
            if (i.cheefId == id) {
                arrChefMeals.add(i)
            }

        }
        return arrChefMeals
    }

}
