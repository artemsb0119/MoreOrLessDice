package com.example.moreorlessdice

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest

class PlayActivity : AppCompatActivity() {

    private lateinit var buttonMore: AppCompatButton
    private lateinit var buttonLess: AppCompatButton
    private lateinit var imageViewFon2: ImageView
    private lateinit var imageDice1: ImageView
    private lateinit var imageDice2: ImageView
    private lateinit var editTextValue: EditText
    private lateinit var textViewScore: TextView
    private lateinit var textViewResult: TextView
    private lateinit var sharedPreferences: SharedPreferences

    val handler = Handler()

    val diceImages = arrayOf(
        ResourceConstants.DICE_1_URL,
        ResourceConstants.DICE_2_URL,
        ResourceConstants.DICE_3_URL,
        ResourceConstants.DICE_4_URL,
        ResourceConstants.DICE_5_URL,
        ResourceConstants.DICE_6_URL
    )

    var score = 0
    var stavka = 0
    var current_sum = 0
    var next_sum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        buttonMore = findViewById(R.id.buttonMore)
        buttonLess = findViewById(R.id.buttonLess)
        imageDice1 = findViewById(R.id.imageDice1)
        imageDice2 = findViewById(R.id.imageDice2)
        imageViewFon2 = findViewById(R.id.imageViewFon2)
        editTextValue = findViewById(R.id.editTextValue)
        textViewScore = findViewById(R.id.textViewScore)
        textViewResult = findViewById(R.id.textViewResult)
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        score = sharedPreferences.getInt("score", 5)
        textViewScore.text = score.toString()

        imageViewFon2.load(ResourceConstants.FON_2)
        imageDice1.load(ResourceConstants.DICE_4_URL)
        imageDice2.load(ResourceConstants.DICE_3_URL)

        current_sum = 8
        next_sum = 8

        buttonMore.setOnClickListener {
            textViewResult.text = ""
            buttonMore.isClickable = false
            buttonLess.isClickable = false
            current_sum = next_sum
            generateNext(object : OnGenerationCompleteListener {
                override fun onGenerationComplete() {
                    if (TextUtils.isEmpty(editTextValue.text)) {
                        stavka = 1
                    } else {
                        stavka = editTextValue.text.toString().toInt()
                    }
                    if (stavka <= 50 && stavka > score) {
                        stavka = score
                    }
                    if (stavka > 50 && stavka <= score) {
                        stavka = 50
                    }
                    if (stavka > 50 && stavka > score) {
                        if (score > 50) {
                            stavka = 50
                        } else {
                            stavka = score
                        }
                    }
                    editTextValue.setText(stavka.toString())
                    if (current_sum < next_sum) {
                        score += stavka
                        textViewResult.text = "You Win!"
                        textViewResult.setTextColor(getResources().getColor(R.color.green))
                    } else {
                        score -= stavka
                        textViewResult.text = "You Lose!"
                        textViewResult.setTextColor(getResources().getColor(R.color.red))
                    }

                    textViewScore.text = score.toString()
                    sharedPreferences.edit().putInt("score", score).apply()

                    buttonMore.isClickable = true
                    buttonLess.isClickable = true
                }
            })
        }
        buttonLess.setOnClickListener {
            textViewResult.text = ""
            buttonMore.isClickable = false
            buttonLess.isClickable = false
            current_sum = next_sum
            generateNext(object : OnGenerationCompleteListener {
                override fun onGenerationComplete() {
                    if (TextUtils.isEmpty(editTextValue.text)) {
                        stavka = 1
                    } else {
                        stavka = editTextValue.text.toString().toInt()
                    }
                    if (stavka <= 50 && stavka > score) {
                        stavka = score
                    }
                    if (stavka > 50 && stavka <= score) {
                        stavka = 50
                    }
                    if (stavka > 50 && stavka > score) {
                        if (score > 50) {
                            stavka = 50
                        } else {
                            stavka = score
                        }
                    }
                    editTextValue.setText(stavka.toString())
                    if (current_sum > next_sum) {
                        score += stavka
                        textViewResult.text = "You Win!"
                        textViewResult.setTextColor(getResources().getColor(R.color.green))
                    } else {
                        score -= stavka
                        textViewResult.text = "You Lose!"
                        textViewResult.setTextColor(getResources().getColor(R.color.red))
                    }

                    textViewScore.text = score.toString()
                    sharedPreferences.edit().putInt("score", score).apply()

                    buttonMore.isClickable = true
                    buttonLess.isClickable = true
                }
            })
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
        return super.onKeyDown(keyCode, event)
    }

    fun generateNext(callback: OnGenerationCompleteListener) {
        updateDiceImagesRandomly()

        handler.postDelayed({
            handler.removeCallbacksAndMessages(null)
            callback.onGenerationComplete()
        }, 3000)
    }

    fun updateDiceImagesRandomly() {
        val randomIndex1 = (1..6).random()
        val randomIndex2 = (1..6).random()

        val randomDice1 = diceImages[randomIndex1 - 1]
        val randomDice2 = diceImages[randomIndex2 - 1]

        val imageLoader = ImageLoader(this)

        val requestDice1 = ImageRequest.Builder(this)
            .data(randomDice1)
            .target(imageDice1)
            .build()

        val requestDice2 = ImageRequest.Builder(this)
            .data(randomDice2)
            .target(imageDice2)
            .build()

        imageLoader.enqueue(requestDice1)
        imageLoader.enqueue(requestDice2)

        next_sum = randomIndex1 + randomIndex2

        handler.postDelayed({
            updateDiceImagesRandomly()
        }, 50)
    }
}