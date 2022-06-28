package com.example.asd_side.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.CategoryDesignBinding
import com.example.asd_side.db.MyDataBase
import com.example.asd_side.model.CategoryModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.R

class RecSaveCategoryAdapter(var context: Context, var arr: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<RecSaveCategoryAdapter.myViewHolder>() {

    var db = MyDataBase(context)

    class myViewHolder(var binding: CategoryDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding = CategoryDesignBinding.inflate(LayoutInflater.from(context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        holder.binding.ratingPar.rating = arr[position].reting
        Picasso.get().load(arr[position].imageUrl).into(holder.binding.imgCategoryImage)
        holder.binding.tvCategoryName.text = arr[position].name
        holder.binding.image2.setImageResource(com.example.asd_side.R.drawable.ic_baseline_unarchive_24)
        holder.binding.imageimage.setImageResource(com.example.asd_side.R.drawable.ic_baseline_delete_24)
        holder.binding.texttext.text = "Delete"

        holder.binding.btnAdd.setOnClickListener {
            var dialog = AlertDialog.Builder(context)
                .setCancelable(true)
            dialog.setMessage("Are you sure to delete ${arr[position].name}?")
            dialog.setNegativeButton("Delete") { di, i ->

                var i = db.deleteCategoryItem(arr[position].id)
                if (i) {
                    arr.removeAt(position)
                    notifyDataSetChanged()
                    di.dismiss()
                }
            }
            dialog.setPositiveButton("Cancel") { di, i ->
                di.dismiss()
            }
            dialog.show()
        }

        holder.binding.btnArchive.setOnClickListener {
            var dialog = AlertDialog.Builder(context)
                .setCancelable(true)
            dialog.setMessage("Are you sure to delete ${arr[position].name}?")
            dialog.setNegativeButton("Delete") { di, ix ->

                var i = db.deleteCategoryItem(arr[position].id)
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
    }

    override fun getItemCount(): Int {
        return arr.size
    }
}