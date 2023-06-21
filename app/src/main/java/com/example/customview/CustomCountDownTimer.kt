package com.example.customview

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CustomCountDownTimer : ConstraintLayout {
    lateinit var attrArray: TypedArray
    var tvTimerDay: TextView
    var tvTimerHour: TextView
    var tvTimerMinute: TextView
    var tvTimerSecond: TextView
    private val timeFormatter: NumberFormat = DecimalFormat("00")

    private var timeDate: String = ""
    private var timerDateFormat: String = ""
    private var timerInMilliSecond: String = ""
    var clDay: ConstraintLayout
    var clHour: ConstraintLayout
    var clMinute: ConstraintLayout
    var clSecond: ConstraintLayout
    var clMain: ConstraintLayout


    constructor(context: Context) : super(context) {
        getStuffFromXml(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getStuffFromXml(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getStuffFromXml(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        getStuffFromXml(context, attrs)
    }

    init {
        inflate(context, R.layout.custom_count_down_timer, this)
        tvTimerDay = findViewById(R.id.tvTimerDay)
        tvTimerHour = findViewById(R.id.tvTimerHour)
        tvTimerMinute = findViewById(R.id.tvTimerMinute)
        tvTimerSecond = findViewById(R.id.tvTimerSecond)
        clDay = findViewById(R.id.clDay)
        clHour = findViewById(R.id.clHour)
        clMinute = findViewById(R.id.clMinute)
        clSecond = findViewById(R.id.clSecond)
        clMain = findViewById(R.id.clMain)


    }

    private fun getStuffFromXml(context: Context, attrs: AttributeSet?) {
        attrArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownTimer)
        timeDate = attrArray.getString(R.styleable.CountDownTimer_timeDate).toString()
        timerDateFormat = attrArray.getString(R.styleable.CountDownTimer_timerDateFormat).toString()


        timerInMilliSecond =
            attrArray.getString(R.styleable.CountDownTimer_timerInMilliSecond).toString()

        val timerBackgroundImg =
            attrArray.getDrawable(R.styleable.CountDownTimer_timerBackgroundImg)

        val timerColor = attrArray.getColor(R.styleable.CountDownTimer_timerBgColor, -1)


        val backgroundImg = attrArray.getDrawable(R.styleable.CountDownTimer_backgroundImg)
        val backgroundColor = attrArray.getColor(R.styleable.CountDownTimer_backgroundColor, -1)


        setTimerBackground(timerBackgroundImg, timerColor)

        setBackground(backgroundImg, backgroundColor)


//        refreshDrawableState()
//
//        attrArray.recycle()

        setCountDownTimer()
    }

    private fun setTimerBackground(
        timerBackgroundImg: Drawable?,
        timerColor: Int
    ) {
        if (timerBackgroundImg != null) {
            clDay.background = timerBackgroundImg
            clHour.background = timerBackgroundImg
            clMinute.background = timerBackgroundImg
            clSecond.background = timerBackgroundImg
            if (timerColor != -1) {
                clDay.backgroundTintList = ColorStateList.valueOf(timerColor)
                clHour.backgroundTintList = ColorStateList.valueOf(timerColor)
                clMinute.backgroundTintList = ColorStateList.valueOf(timerColor)
                clSecond.backgroundTintList = ColorStateList.valueOf(timerColor)
            }
        } else {
            if (timerColor != -1) {
                clDay.setBackgroundColor(timerColor)
                clHour.setBackgroundColor(timerColor)
                clMinute.setBackgroundColor(timerColor)
                clSecond.setBackgroundColor(timerColor)

            }
        }
    }

    private fun setBackground(
        backgroundImg: Drawable?,
        backgroundColor: Int
    ) {
        if (backgroundImg != null) {
            clMain.background = backgroundImg
            if (backgroundColor != -1) {
                clMain.backgroundTintList = ColorStateList.valueOf(backgroundColor)
            }
        } else {
            if (backgroundColor != -1) {
                clMain.setBackgroundColor(backgroundColor)
            }
        }
    }

    private fun setCountDownTimer() {
        Print.log("Time Data $timeDate and format $timerDateFormat")
        if (checkInvalidTime()) {
            return
        }

        val timerInMilli =
            if (timerInMilliSecond.isEmpty() || timerInMilliSecond.equals("null", true)) {
                val dateFormat = SimpleDateFormat(timerDateFormat, Locale.getDefault())
                // Please here set your event date//YYYY-MM-DD
                val futureDate: Date = dateFormat.parse(timeDate) as Date
                val currentDate = Date()
                Print.log("future Date = $futureDate and Current Date $currentDate")
                Print.log("future Date Time = ${futureDate.time} and Current Date Time ${currentDate.time}")
                (futureDate.time - currentDate.time)
            } else {
                timerInMilliSecond.toLong()
            }

        Print.log("Time in Mill $timerInMilli")
        object : CountDownTimer(timerInMilli, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                try {
                    val days = millisUntilFinished / 86400000 % 365
                    val hour = millisUntilFinished / 3600000 % 24
                    val min = millisUntilFinished / 60000 % 60
                    val sec = millisUntilFinished / 1000 % 60
                    setTime(days = days, hour = hour, minute = min, second = sec)

                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFinish() {
                setTime(days = 0, hour = 0, minute = 0, second = 0)
            }
        }.start()

    }

    private fun checkInvalidTime(): Boolean {
        return ((timeDate.isEmpty() || timeDate.equals(
            "null",
            true
        )) && (timerInMilliSecond.isEmpty() || timerInMilliSecond.equals("null", true)))
    }

    private fun setTime(days: Long, hour: Long, minute: Long, second: Long) {
        tvTimerDay.text = timeFormatter.format(days)
        tvTimerHour.text = timeFormatter.format(hour)
        tvTimerMinute.text = timeFormatter.format(minute)
        tvTimerSecond.text = timeFormatter.format(second)
    }
}
