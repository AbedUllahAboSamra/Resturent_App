package com.example.asd_side.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.asd_side.model.CategoryModel
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.MainActivity
import com.example.asd_side.screen.SplachActivity


class MyDataBase(var context: Context) : SQLiteOpenHelper(context, name, null, version) {

    companion object {
        var version = 1
        var name = "MySqlDataBase"
        var arrCategorys = ArrayList<CategoryModel>()
        var arrMeals = ArrayList<MealModle>()
        var database = this

    }

    private lateinit var db: SQLiteDatabase

    init {
        db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE MEAL(id TEXT PRIMARY KEY)"
        )
        db.execSQL(
            "CREATE TABLE CATEGORY(id TEXT PRIMARY KEY)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS MEAL")
        db.execSQL("DROP TABLE IF EXISTS CATEGORY")
        onCreate(db)
    }


    fun getMallItems() {
        arrMeals.clear()

        var arrayList = ArrayList<MealModle>()
        var c = db.rawQuery("SELECT * FROM MEAL", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            var id = c.getString(0)
            for (item in SplachActivity.arrMeal) {
                if (item.id == id) {
                    arrayList.add(item)
                }
            }
            c.moveToNext()
        }
        c.close()
        arrMeals = arrayList
    }

    fun getCategoryItems() {
        arrCategorys.clear()

        var arrayList1 = ArrayList<CategoryModel>()
        var c = db.rawQuery("SELECT * FROM CATEGORY", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            var id = c.getString(0)
            for (item in SplachActivity.arrCategorys) {
                if (item.id == id) {
                    arrayList1.add(item)
                }
            }
            c.moveToNext()
        }
        c.close()
        arrCategorys = arrayList1
    }

    fun addMeal(id: String): Boolean {
        var c = ContentValues()
        c.put("id", id)
        var bool = db.insert("MEAL", null, c) > 0
        if (bool) {
            MainActivity.numberOfitems.value?.plus(1)

        }
        return bool

    }

    fun addCategory(id: String): Boolean {
        var c = ContentValues()
        c.put("id", id)
        var bool = db.insert("CATEGORY", null, c) > 0
        if (bool) {
            MainActivity.numberOfitems.value?.plus(1)
        }
        return bool

    }

    fun deleteMealItem(id: String): Boolean {
        var bool = db.delete("MEAL", "id=$id", null) > 0
        if (bool) {
            MainActivity.numberOfitems.value?.minus(1)
        }
        return bool
    }

    fun deleteCategoryItem(id: String): Boolean {

        return db.delete("CATEGORY", "id=$id", null) > 0

    }
}
