package com.example.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.customview.R
import kotlin.math.pow

class CustomView : View {
    val SQARE_SIZE_DEF = 200
    lateinit var mReactSquare: Rect
    lateinit var mPaintSquare: Paint
    lateinit var mPaintCircle: Paint
    var mSquareColor: Int = Color.GREEN
    var mCircleX: Float = 0f
    var mCircleY: Float = 0f
    var mCircleRadius = 100f

    var mSquareSize: Int = 0

    lateinit var mImageBitmap: Bitmap

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        mPaintSquare = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintCircle = Paint()
        mPaintCircle.isAntiAlias = true
        mPaintCircle.color = Color.parseColor("#00ccff")
        mReactSquare = Rect()

        mImageBitmap = BitmapFactory.decodeResource(resources,R.drawable.img_no_internet_connection)
        if (set == null) return

        val ta: TypedArray = context.obtainStyledAttributes(set, R.styleable.CustomView)
        mSquareColor = ta.getColor(R.styleable.CustomView_square_color, Color.GREEN)
        mSquareSize = ta.getDimensionPixelSize(R.styleable.CustomView_square_size, SQARE_SIZE_DEF)
        mPaintSquare.color = mSquareColor

    }

    override fun onDraw(canvas: Canvas?) {
//       canvas?.drawColor(Color.RED)

        mReactSquare.left = 50
        mReactSquare.top = 50
        mReactSquare.right = mReactSquare.left + mSquareSize
        mReactSquare.bottom = mReactSquare.top + mSquareSize


        canvas?.drawRect(mReactSquare, mPaintSquare)
        /* val radius = 100f
         val cx:Float = width - radius - 50f
         val cy:Float =  mReactSquare.top +( mReactSquare.height() /2).toFloat()
         */

        mImageBitmap = getResizeImage(mImageBitmap,width,height)

        if (mCircleX == 0f || mCircleY == 0f) {
            mCircleX = (width / 2).toFloat();
            mCircleY = (height / 2).toFloat();
        }
        canvas?.drawCircle(mCircleX, mCircleY, mCircleRadius, mPaintCircle)

        canvas?.drawBitmap(mImageBitmap,0f,0f,null)
    }


    public fun swapColor() {
        if (mPaintSquare.color == mSquareColor)
            mPaintSquare.color = Color.RED
        else mPaintSquare.color = mSquareColor

        postInvalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val value = super.onTouchEvent(event)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {

                if (mReactSquare.left < x && mReactSquare.right >x){
                    if (mReactSquare.top < y && mReactSquare.bottom > y){
                        mCircleRadius+=10;
                        postInvalidate()
                    }
                }
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                // we have x and y value from the event it is the position of touch
                val x = event.x
                val y = event.y

                // to deduct this touch is inside our circle
                val dx = ((x - mCircleX.toDouble()).pow(2))
                val dy = ((y - mCircleY.toDouble()).pow(2))

                if (dx + dy < mCircleRadius.toDouble().pow(2)){
                    mCircleX = x
                    mCircleY = y
                    postInvalidate()
                    return true
                }
                return true
            }
        }

        return value
    }

    private fun getResizeImage(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val matrix = Matrix()
        val src = RectF(0f,0f,bitmap.width.toFloat(),bitmap.height.toFloat())
        val dst = RectF(0f,0f,reqWidth.toFloat(),reqHeight.toFloat())
        matrix.setRectToRect(src,dst,Matrix.ScaleToFit.CENTER)

        return Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height)
    }

}
