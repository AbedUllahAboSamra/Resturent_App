package com.example.asd_side.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdCommentDesignBinding
import com.example.asd_side.model.CommentModle
import com.squareup.picasso.Picasso

class RecCommentsAdapter(var context: Context, var arr: ArrayList<CommentModle>) :
    RecyclerView.Adapter<RecCommentsAdapter.myViewHolder>() {

    class myViewHolder(var binding: AsdCommentDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding = AsdCommentDesignBinding.inflate(LayoutInflater.from(context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {


        if (position == 0) {
            holder.binding.upDivider.visibility=View.INVISIBLE
        }

        if (arr[position].commentImageUri!!.isEmpty()) {
            holder.binding.cardImageView.visibility = View.GONE
            Picasso.get().load(arr[position].userImageUri).into(holder.binding.imgCommenterImage)
            holder.binding.tvCommentDate.text = arr[position].commentDate
            holder.binding.tvCommenterName.text = arr[position].userName
            holder.binding.tvCommenterContent.text = arr[position].commentContent

        } else if (arr[position].commentImageUri!!.isNotEmpty()) {
            holder.binding.cardImageView.visibility = View.VISIBLE
            Picasso.get().load(arr[position].userImageUri).into(holder.binding.imgCommenterImage)
            Picasso.get().load(arr[position].commentImageUri).into(holder.binding.commentImage)
            holder.binding.tvCommentDate.text = arr[position].commentDate
            holder.binding.tvCommenterContent.text = arr[position].commentContent
            holder.binding.tvCommenterName.text = arr[position].userName

        } else if (arr[position].commentImageUri!!.isNotEmpty()) {
            Picasso.get().load(arr[position].userImageUri).into(holder.binding.imgCommenterImage)
            Picasso.get().load(arr[position].commentImageUri).into(holder.binding.commentImage)
            holder.binding.tvCommentDate.text = arr[position].commentDate
            holder.binding.tvCommenterName.text = arr[position].userName

        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }

    fun notyfiDataSetChang(commentModle: CommentModle) {
        arr.add(commentModle)
        notifyDataSetChanged()
    }

}