package com.example.asd_side.adapters

import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdIngredientsDesignBinding

class RecycleIngredientsAdapter(var context: Context, var arr: ArrayList<String>) :
    RecyclerView.Adapter<RecycleIngredientsAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: AsdIngredientsDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = AsdIngredientsDesignBinding.inflate(LayoutInflater.from(context), null, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvCountNote.visibility = View.GONE
        var text = arr[position]
        var arrr = text.split(",")
        var mmm = arrr[0].split(" ")
        if (mmm.size == 1) {
            holder.binding.tvCount.text = mmm[0]
            holder.binding.tvName.text = arrr[1]
        } else if (mmm.size == 2) {
            holder.binding.tvCount.text = mmm[0] + " " + mmm[1]
            holder.binding.tvName.text = arrr[1]

        } else if (mmm.size ==3) {
            holder.binding.tvCountNote.visibility = View.VISIBLE
            holder.binding.tvCount.text = mmm[0] + " " + mmm[1]
            holder.binding.tvCountNote.text = "( ${mmm[2]} )"
            holder.binding.tvName.text = arrr[1]

        }



    }

    override fun getItemCount(): Int {
        return arr.size
    }


}