package com.example.asd_side.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.AsdStepsDesignBinding

class RecStepsAdapter(var context: Context, var arr: ArrayList<String>) :
    RecyclerView.Adapter<RecStepsAdapter.myViewHolder>() {

    class myViewHolder(var binding: AsdStepsDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var binding = AsdStepsDesignBinding.inflate(LayoutInflater.from(context), null, false)
        return myViewHolder(binding)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
     holder.binding.tvStepNumber.text = "Step ${position+1}"
     holder.binding.tvStepText.text = arr[position]
        if(position==arr.size-1){
            holder.binding.frmBorder.visibility=View.INVISIBLE
        }

    }

    override fun getItemCount(): Int {
        return arr.size
    }


}