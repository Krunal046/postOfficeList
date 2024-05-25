package com.ersiver.test_krunal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ersiver.test_krunal.R
import com.ersiver.test_krunal.databinding.ActivityLoginBinding
import com.ersiver.test_krunal.utils.SharedPreferencesHelper

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNullOrEmpty() && password.isNullOrEmpty()){
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter Email and Password.",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                val sharedPreferences = SharedPreferencesHelper(this)
                sharedPreferences.saveLoginDetails(email,password)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                sharedPreferences.setLogin(true)
                Toast.makeText(
                    this@LoginActivity,
                    "Login Successfully.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }
}
