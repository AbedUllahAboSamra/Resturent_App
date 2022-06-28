package com.example.asd_side.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.asd_side.databinding.IngredientsDesignBinding
import com.example.asd_side.databinding.StepsDesignBinding
import com.example.asd_side.screen.AddScreens.AddMealActivity

class ingredientAdapter() : RecyclerView.Adapter<ingredientAdapter.myViewHolder>() {
    var count = 1
    lateinit var context: Context


    class myViewHolder(var binding: IngredientsDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context = parent.context
        var binding =
            IngredientsDesignBinding.inflate(LayoutInflater.from(parent.context), null, false)

        return myViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var a = position
        holder.binding.numberOfStep.text = (a + 1).toString() + "."



        if (AddMealActivity.inteArray.isNotEmpty()) {
            holder.binding.edtStep.setText(
                AddMealActivity.inteArray[a.toString()]?.split(",")?.get(1) ?: ""
            )
            holder.binding.Quantity.setText(
                AddMealActivity.inteArray[a.toString()]?.split(",")?.get(0) ?: ""
            )
        }


        var quntty = holder.binding.Quantity.text.toString()
        var step = holder.binding.edtStep.text.toString()

        holder.binding.edtStep.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    step = s.toString()
                }

            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("ASD", AddMealActivity.inteArray.toString())
            }
        })

        holder.binding.Quantity.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    quntty = s.toString()

                }


            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        holder.binding.btnPluss.setOnClickListener {

            if (quntty.isNotEmpty() && step.isNotEmpty()) {
                AddMealActivity.inteArray["$a"] = "$quntty,$step"
                count += 1
                notifyDataSetChanged()
            } else {
                Toast.makeText(context, "cont be empty", Toast.LENGTH_SHORT).show()
            }

        }
        holder.binding.btnMins.setOnClickListener {

            if (count > 1) {
                AddMealActivity.inteArray.remove(position.toString())
                count -= 1
                notifyDataSetChanged()
            }
        }


    }

    override fun getItemCount(): Int {
        return count
    }


}