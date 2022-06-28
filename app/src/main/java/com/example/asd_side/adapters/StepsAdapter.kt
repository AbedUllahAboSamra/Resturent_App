package com.example.asd_side.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.StepsDesignBinding
import com.example.asd_side.screen.AddScreens.AddMealActivity

class StepsAdapter() :
    RecyclerView.Adapter<StepsAdapter.myViewHolder>() {
    var count = 1
    lateinit var context: Context


    class myViewHolder(var binding: StepsDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding = StepsDesignBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return myViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.binding.numberOfStep.text = (position + 1).toString() + "."

        var step = holder.binding.edtStep.text.toString()
        var a = position

        holder.binding.edtStep.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    step = s.toString()
                }

            }

            override fun afterTextChanged(s: Editable?) {
             }
        })


        if (AddMealActivity.stepsArray.isNotEmpty()) {
            holder.binding.edtStep.setText(AddMealActivity.stepsArray[a.toString()])
        }

        holder.binding.btnPluss.setOnClickListener {

            if (step.isNotEmpty()) {
                AddMealActivity.stepsArray["$a"] = "$step"

                count += 1
                notifyDataSetChanged()
            } else {
                Toast.makeText(context, "cont be empty", Toast.LENGTH_SHORT).show()
            }

        }
        holder.binding.btnMins.setOnClickListener {

            if (count > 1) {
                AddMealActivity.stepsArray.remove(position.toString())
                count -= 1
                notifyDataSetChanged()
            }
        }


    }

    override fun getItemCount(): Int {
        return count
    }


}