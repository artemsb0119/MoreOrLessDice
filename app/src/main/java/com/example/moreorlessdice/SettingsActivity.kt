package com.example.moreorlessdice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.load

class SettingsActivity : AppCompatActivity() {

    private lateinit var imageViewFon2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        imageViewFon2.load(ResourceConstants.FON_2)
    }
}