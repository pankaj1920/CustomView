package com.example.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class ButtonWithProgressBar : ConstraintLayout {
    private lateinit var mRootLayout: ConstraintLayout
    private lateinit var tvText: TextView
    private lateinit var mProgressBar: ProgressBar
    private var text = ""
    private var size = ButtonWithProgressBarSize.DEFAULT
    private var isButtonEnable = true
    private var backgroundColor = 0
    private var textColor = 0
    private var showingLoader = 0

    constructor(context: Context) : super(context) {
        initButton(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getStuffFromXml(attrs, context)
        initButton(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getStuffFromXml(attrs, context)
        initButton(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        getStuffFromXml(attrs, context)
        initButton(context, attrs)
    }

    private fun getStuffFromXml(attrs: AttributeSet?,context:Context){
        val data = context.obtainStyledAttributes(attrs,R.styleable.ButtonWithProgressBar)
        text = data.getString(R.styleable.ButtonWithProgressBar_text).toString()
        isButtonEnable = data.getBoolean(R.styleable.ButtonWithProgressBar_enable,true)
        backgroundColor = data.getColor(R.styleable.ButtonWithProgressBar_backgroundClor,context.getColor(R.color.black))
        textColor =  data.getColor(R.styleable.ButtonWithProgressBar_custom_txt_color,context.getColor(R.color.white))

        data.recycle()

    }
    enum class ButtonWithProgressBarSize {
        DEFAULT, MINI, LARGE, LARGEST
    }


    private fun initButton(context: Context, set: AttributeSet?) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.button_with_progess_bar,this,true)
        mRootLayout = findViewById(R.id.tvText)
        mProgressBar = findViewById(R.id.mProgress)

        if(text.isNotEmpty()){
            tvText.text = text
        }

        minimumWidth = 80

        mRootLayout.setPadding(10,0,10,0)

        refreshDrawableState()
    }

}