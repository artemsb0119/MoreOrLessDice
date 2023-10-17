package com.example.moreorlessdice

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import coil.load

class MenuActivity : AppCompatActivity() {

    private lateinit var buttonPlay: AppCompatButton
    private lateinit var buttonRule: AppCompatButton
    private lateinit var buttonSettings: AppCompatButton
    private lateinit var buttonPlus: AppCompatButton
    private lateinit var imageViewFon2: ImageView
    private lateinit var textViewScore: TextView
    private lateinit var sharedPreferences: SharedPreferences

    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        buttonPlay = findViewById(R.id.buttonPlay)
        buttonRule = findViewById(R.id.buttonRule)
        buttonSettings = findViewById(R.id.buttonSettings)
        buttonPlus = findViewById(R.id.buttonPlus)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        textViewScore = findViewById(R.id.textViewScore)

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        score = sharedPreferences.getInt("score", 5)
        textViewScore.text = score.toString()

        imageViewFon2.load(ResourceConstants.FON_2)

        buttonPlay.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
            finish()
        }
        buttonRule.setOnClickListener {
            val intent = Intent(this, RuleActivity::class.java)
            startActivity(intent)
        }
        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        buttonPlus.setOnClickListener {
            if (score == 0) {
                score = 5
                textViewScore.text = score.toString()
                sharedPreferences.edit().putInt("score", score).apply()
            }
        }
    }
}