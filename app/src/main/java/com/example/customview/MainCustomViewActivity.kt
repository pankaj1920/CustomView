package com.example.customview

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.views.CustomView

class MainCustomViewActivity : AppCompatActivity() {
    lateinit var customView: CustomView
    lateinit var swapColor: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_custom_view)

        swapColor = findViewById(R.id.swapColor)
        customView = findViewById(R.id.customView)

        swapColor.setOnClickListener {
            customView.swapColor()
        }
    }
}