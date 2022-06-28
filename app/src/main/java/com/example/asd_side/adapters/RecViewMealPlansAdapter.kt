package com.example.asd_side.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdMealDesignBinding
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.ShowMealActivity
import com.squareup.picasso.Picasso

class RecViewMealPlansAdapter( var context: Context,var arr: ArrayList<MealModle>) :
    RecyclerView.Adapter<RecViewMealPlansAdapter.myViewHolder>() {

var db = MyDataBase(context)
    class myViewHolder(var binding: AsdMealDesignBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding = AsdMealDesignBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        Picasso.get().load(arr[position].imageChefUir).into(holder.binding.imgCheffImage)
        Picasso.get().load(arr[position].imvMealImage).into(holder.binding.imvMealImage)

        holder.binding.tvChefNema.text = arr[position].sheName
        holder.binding.tvMealName.text = arr[position].name
        holder.binding.ratingPar.rating = arr[position].ratingg

        if (arr[position].comments?.isNotEmpty() == true) {
            holder.binding.tvNumberOdComment.text =
                arr[position].comments!!.size.toString() + " comment"
        } else {
            holder.binding.tvNumberOdComment.text = "0 comment"
        }


        holder.binding.tvTime.text = arr[position].time.toString() + " min"

        holder.binding.rrr.setOnClickListener {
            var i = Intent(context, ShowMealActivity::class.java)
            i.putExtra("MealId", arr[position].id)
            context.startActivity(i)
        }
        holder.binding.btnAdd.setOnClickListener {
            var i = db.addMeal(arr[position].id)
            if (!i) {
                Toast.makeText(context, "item in the bag", Toast.LENGTH_SHORT).show()
            }
        }
        holder.binding.btnArchive.setOnClickListener {
            var i = db.addMeal(arr[position].id)
            if (!i) {
                Toast.makeText(context, "item in the bag", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }


}