package com.example.asd_side.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdMealTringingNowdesignBinding
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.ShowMealActivity
import com.squareup.picasso.Picasso

class RecTrindingNowAdapter(var context: Context,var arr: ArrayList<MealModle>) :
    RecyclerView.Adapter<RecTrindingNowAdapter.myViewHolder>() {

     var db = MyDataBase(context)

    class myViewHolder(var binding: AsdMealTringingNowdesignBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding =
            AsdMealTringingNowdesignBinding.inflate(
                LayoutInflater.from(parent.context),
                null,
                false
            )
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        Picasso.get().load(arr[position].imvMealImage).into(holder.binding.imvMealImage)
        holder.binding.tvChefNema.text = arr[position].sheName
        holder.binding.tvMealName.text = arr[position].name

        // set On Item Click List
        holder.binding.rrr.setOnClickListener {
            var i = Intent(context, ShowMealActivity::class.java)
            i.putExtra("MealId", arr[position].id)
            context.startActivity(i)
        }

        holder.binding.btnArchive.setOnClickListener {
            var i = db.addMeal(arr[position].id)
            if (!i) {
                Toast.makeText(context, "item in the bag", Toast.LENGTH_SHORT).show()
            }
        }
        holder.binding.btnShow.setOnClickListener {
            var i = Intent(context, ShowMealActivity::class.java)
            i.putExtra("MealId", arr[position].id)
            context.startActivity(i)
        }


    }

    override fun getItemCount(): Int {
        return arr.size
    }


}