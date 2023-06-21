package com.example.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ImageWithText(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {
    private val attributes: TypedArray
    lateinit var  imageView: ImageView
    lateinit var textViw:TextView

    init {
        inflate(context, R.layout.image_view_with_text, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView)
        imageView = findViewById(R.id.customImage)
        textViw = findViewById(R.id.customText)

        imageView.setImageResource(attributes.getResourceId(R.styleable.ImageTextView_image,R.color.black))
//        textViw.setText(attributes.getString(R.styleable.ImageTextView_text))
    }

    fun getString():String{
//        return attributes.getString(R.styleable.ImageTextView_text).toString
        return  ""
    }
}
