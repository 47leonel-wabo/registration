package com.wbt.registrationapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.wbt.registrationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        binding.spinnerJobPosition.adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Android Engineer", "Student", "Nurse", "Kotlin Programmer")
        )

        binding.buttonPreview.setOnClickListener {
            val intent = Intent(this@MainActivity, PreviewActivity::class.java)
            intent.putExtra(
                "PROFILE_DATA", Profile(
                    binding.firstNameText.text.toString(),
                    binding.lastNameText.text.toString(),
                    binding.emailText.text.toString(),
                    binding.passwordText.text.toString(),
                    binding.phoneNumberText.text.toString(),
                    binding.spinnerJobPosition.selectedItem?.toString()
                )
            )
            startActivity(intent)
        }
    }
}