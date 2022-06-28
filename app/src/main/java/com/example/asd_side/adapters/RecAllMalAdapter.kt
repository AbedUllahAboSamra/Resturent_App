package com.example.asd_side.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdAllMealDesignBinding
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.ShowMealActivity
import com.squareup.picasso.Picasso

class RecAllMalAdapter(var arr: ArrayList<MealModle>) :
    RecyclerView.Adapter<RecAllMalAdapter.myViewHolder>() {
    lateinit var context: Context

    class myViewHolder(var binding: AsdAllMealDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding =
            AsdAllMealDesignBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        Picasso.get().load(arr[position].imvMealImage).into(holder.binding.imvMealImage)
        Picasso.get().load(arr[position].imageChefUir).into(holder.binding.imgCheffImage)
        holder.binding.tvChefNema.text = arr[position].sheName
        holder.binding.tvMealName.text = arr[position].name
        holder.binding.tvCategoryName.text = arr[position].category


        holder.binding.rrrr.setOnClickListener {
            var i = Intent(context, ShowMealActivity::class.java)
            i.putExtra("MealId", arr[position].id)

            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }
    fun notifyData(){
        notifyDataSetChanged()
    }
}