package com.example.asd_side.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdFeaturedCulinaryPartnersDesignBinding
import com.example.asd_side.model.AccountModel
import com.squareup.picasso.Picasso

class RecFeatuedCulinaryAdapter(var arr: ArrayList<AccountModel>) :
    RecyclerView.Adapter<RecFeatuedCulinaryAdapter.myViewHolder>() {

    lateinit var context: Context

    class myViewHolder(var binding: AsdFeaturedCulinaryPartnersDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding = AsdFeaturedCulinaryPartnersDesignBinding.inflate(
            LayoutInflater.from(parent.context),
            null,
            false
        )
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.tvDescribtion.text = arr[position].description
        holder.binding.tvAccountName.text = arr[position].name
        holder.binding.tvBio.text = arr[position].bio
        Picasso.get().load(arr[position].imageUri).into(holder.binding.imgAccountImage)



    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun dateNotyfi() {
        notifyDataSetChanged()
    }

}