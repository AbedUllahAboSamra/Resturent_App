package com.example.asd_side.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.R
import com.example.asd_side.databinding.AsdMealTringingNowdesignBinding
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.ShowMealActivity
import com.squareup.picasso.Picasso

class RecSaveMeal(var context: Context, var arr: ArrayList<MealModle>) :
    RecyclerView.Adapter<RecSaveMeal.myViewHolder>() {

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
        holder.binding.imagemiasd.setImageResource(R.drawable.ic_baseline_unarchive_24)
        holder.binding.btnArchive.setOnClickListener {


            var dialog = AlertDialog.Builder(context)
                .setCancelable(true)
            dialog.setMessage("Are you sure to delete ${arr[position].name}?")
            dialog.setNegativeButton("Delete") { di, _ ->
                var i = db.deleteMealItem(arr[position].id)
                if (i) {
                    arr.removeAt(position)
                    notifyDataSetChanged()
                    di.dismiss()
                }
            }
            dialog.setPositiveButton("Cancel") { di, ix ->
                di.dismiss()
            }
            dialog.show()
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