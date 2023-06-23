package com.example.customview

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.progressindicator.LinearProgressIndicator

class SwipeBarGraph : ConstraintLayout {
    lateinit var attrArray: TypedArray
    var cvGraph: CardView
    var progressGraph: LinearProgressIndicator
    var tvProgressCount: TextView
    var tvProgressDec: TextView
    private var progressCount = -1
    private var graphColor = -1
    private var graphCountTxtColor = -1
    private var graphCountDecColor = -1
    private var progressDec = ""
    private var enableSwipe = false
    private var progressCountVisible = true
    private var progressDecVisible = true
    private var progressAnimation = true

    constructor(context: Context) : super(context) {
        getStuffFromXml(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getStuffFromXml(context, attrs)
    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        getStuffFromXml(context, attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        getStuffFromXml(context, attrs)
    }

    init {
        inflate(context, R.layout.custom_bar_graph, this)

        cvGraph = findViewById(R.id.cvGraph)
        progressGraph = findViewById(R.id.progressGraph)
        tvProgressCount = findViewById(R.id.tvProgressCount)
        tvProgressDec = findViewById(R.id.tvProgressDec)
    }

    private fun getStuffFromXml(context: Context, attrs: AttributeSet?) {
        attrArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeBarGraph)

        progressCount = attrArray.getInteger(R.styleable.SwipeBarGraph_progressCount, 0)
        progressDec = attrArray.getString(R.styleable.SwipeBarGraph_progressDesc).toString()
        graphColor = attrArray.getColor(R.styleable.SwipeBarGraph_graphColor, -1)
        graphCountTxtColor = attrArray.getColor(R.styleable.SwipeBarGraph_graphCountTxtColor, -1)
        graphCountDecColor = attrArray.getColor(R.styleable.SwipeBarGraph_graphDecTxtColor, -1)
        enableSwipe = attrArray.getBoolean(R.styleable.SwipeBarGraph_enableSwipe, false)
        progressCountVisible =
            attrArray.getBoolean(R.styleable.SwipeBarGraph_progressCountVisible, true)
        progressDecVisible =
            attrArray.getBoolean(R.styleable.SwipeBarGraph_progressDecVisible, true)

        progressAnimation = attrArray.getBoolean(R.styleable.SwipeBarGraph_progressAnimation,true)

        setGraphInfo()
    }

    private fun setGraphInfo() {
        tvProgressCount.text = "$progressCount %"

        if (progressDec != "null") tvProgressDec.text = progressDec

        progressGraph.setProgress(progressCount, progressAnimation)
        if (graphColor != -1) {
            setProgressColor(graphColor)
        }

        if (graphCountTxtColor != -1) setProgressCountTextColor(graphCountTxtColor)

        if (graphCountDecColor != -1) setProgressDecTextColor(graphCountDecColor)

        showProgressCount(progressCountVisible)
        showProgressDesc(progressDecVisible)
    }

    fun setProgressCountTextColor(color: Int) {
        tvProgressCount.setTextColor(color)
    }

    fun setProgressDecTextColor(color: Int) {
        tvProgressDec.setTextColor(color)
    }

    fun setProgressColor(color: Int) {
        cvGraph.backgroundTintList = ColorStateList.valueOf(color)
        progressGraph.setIndicatorColor(color)
    }

    fun showProgressCount(isVisible: Boolean) {
        if (isVisible)
            tvProgressCount.visibility = View.VISIBLE
        else tvProgressCount.visibility = View.GONE
    }

    fun showProgressDesc(isVisible: Boolean) {
        if (isVisible)
            tvProgressDec.visibility = View.VISIBLE
        else tvProgressDec.visibility = View.GONE
    }
}