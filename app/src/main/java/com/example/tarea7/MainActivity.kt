package com.example.tarea7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewBreedsButton: Button = findViewById(R.id.viewBreedsButton)

        viewBreedsButton.setOnClickListener {
            val intent = Intent(this, DogBreedsActivity::class.java)
            startActivity(intent)
        }
    }
}