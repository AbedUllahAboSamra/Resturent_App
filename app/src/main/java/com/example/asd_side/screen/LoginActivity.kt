package com.example.asd_side.screen

import android.content.Intent
import android.graphics.PaintFlagsDrawFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.asd_side.R
import com.example.asd_side.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    //start declare variables

    lateinit var binding: ActivityLoginBinding

    //end declare variables

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start initialization code
        binding.btnLogin.paintFlags = TextPaint.UNDERLINE_TEXT_FLAG

        // end initialization code


    }

    override fun onStart() {
        super.onStart()
        // move to sign up activity
        binding.btnSignUp.setOnClickListener {
            var i = Intent(this, SignUpActivity::class.java)
            i.putExtra("opera",0)
            startActivity(i)

        }
        binding.btnLogin.setOnClickListener {
            var i = Intent(this, SignUpActivity::class.java)
            i.putExtra("opera",1)
            startActivity(i)

        }


    }
}