package com.mixedup.win.wins

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.BitmapFactory.Options
import android.graphics.drawable.BitmapDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.*
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mixedup.win.wins.Music.mediaplayer_music
import kotlinx.android.synthetic.main.activity_puzzle.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class PuzzleActivity : AppCompatActivity() {
    var pieces: ArrayList<PuzzlePiece?>? = null
    var mCurrentPhotoPath: String? = null
    var mCurrentPhotoUri: String? = null

    private var mCountDownTimer1: CountDownTimer? = null
    private var mTimerRunning = false
    private var mTimeLeftInMillis: Long = 0
    private var mEndTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)
        val layout = findViewById<RelativeLayout>(R.id.layout)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val intent = intent
        val assetName = intent.getStringExtra("assetName")
        mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath")
        mCurrentPhotoUri = intent.getStringExtra("mCurrentPhotoUri")

        // run image related code after the view was laid out
        // to have all dimensions calculated

        val music = PreferenceManager.getDefaultSharedPreferences(this).getInt("music", 1)
        if(music == 1){
            Music.mediaplayer_music!!.start()
            Music.mediaplayer_music!!.isLooping = true
        }else{

        }

        Handler(Looper.myLooper()!!).postDelayed(
            {
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("onStops", 0).apply()
            }, 1000)

        cardView4.setOnClickListener {
            val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
            if(sound == 1){
                Music.mediaplayer_sound!!.start()
            }else{

            }
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt("onStops", 1).apply()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        val index = intent.getIntExtra("index",0)

        val imageRandom = intent.getStringExtra("image")

        if(imageRandom == "image1.jpg"){
            imageView12.setImageResource(R.drawable.image1)
        }else if(imageRandom == "image2.jpg"){
            imageView12.setImageResource(R.drawable.image2)
        }else if(imageRandom == "image3.jpg"){
            imageView12.setImageResource(R.drawable.image3)
        }else if(imageRandom == "image4.jpg"){
            imageView12.setImageResource(R.drawable.image4)
        }else if(imageRandom == "image5.jpg"){
            imageView12.setImageResource(R.drawable.image5)
        }else if(imageRandom == "image6.jpg"){
            imageView12.setImageResource(R.drawable.image6)
        }

        textView2.setOnClickListener {

        }

        if(index == 1){
            imageView.post {
                if (imageRandom != null) {
                    setPicFromAsset(imageRandom, imageView)
                } else if (mCurrentPhotoPath != null) {
                    setPicFromPath(mCurrentPhotoPath!!, imageView)
                } else if (mCurrentPhotoUri != null) {
                    imageView.setImageURI(Uri.parse(mCurrentPhotoUri))
                }
                pieces = splitImage()
                val touchListener = TouchListener(this@PuzzleActivity)
                // shuffle pieces order
                Collections.shuffle(pieces)
                for (piece in pieces!!) {
                    piece!!.setOnTouchListener(touchListener)
                    layout.addView(piece)
                    // randomize position, on the bottom of the screen
                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams
                    lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                    lParams.topMargin = layout.height - piece.pieceHeight
                    piece.layoutParams = lParams
                }
            }
        }else{
            imageView.post {
                if (assetName != null) {
                    setPicFromAsset(assetName, imageView)
                } else if (mCurrentPhotoPath != null) {
                    setPicFromPath(mCurrentPhotoPath!!, imageView)
                } else if (mCurrentPhotoUri != null) {
                    imageView.setImageURI(Uri.parse(mCurrentPhotoUri))
                }
                pieces = splitImage()
                val touchListener = TouchListener(this@PuzzleActivity)
                // shuffle pieces order
                Collections.shuffle(pieces)
                for (piece in pieces!!) {
                    piece!!.setOnTouchListener(touchListener)
                    layout.addView(piece)
                    // randomize position, on the bottom of the screen
                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams
                    lParams.leftMargin = Random().nextInt(layout.width - piece.pieceWidth)
                    lParams.topMargin = layout.height - piece.pieceHeight
                    piece.layoutParams = lParams
                }
            }
        }
    }

    private fun setPicFromAsset(assetName: String, imageView: ImageView) {
        // Get the dimensions of the View
        val targetW = imageView.width
        val targetH = imageView.height
        val am = assets
        try {
            startTimer()
            val `is` = am.open("img/$assetName")
            // Get the dimensions of the bitmap
            val bmOptions = Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`is`, Rect(-1, -1, -1, -1), bmOptions)
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determine how much to scale down the image
            val scaleFactor = Math.min(photoW / targetW, photoH / targetH)
            `is`.reset()

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor
            bmOptions.inPurgeable = true
            val bitmap = BitmapFactory.decodeStream(`is`, Rect(-1, -1, -1, -1), bmOptions)
            imageView.setImageBitmap(bitmap)
            imageView12.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun splitImage(): ArrayList<PuzzlePiece?> {
        var piecesNumber = 9
        var rows = 3
        var cols = 3
        val level_idex = PreferenceManager.getDefaultSharedPreferences(this).getInt("Games", 1)
        if(level_idex == 1){
            piecesNumber = 16
            rows = 4
            cols = 4
        }else if(level_idex == 2){
            piecesNumber = 28
            rows = 4
            cols = 7
        }else if(level_idex == 3){
            piecesNumber = 36
            rows = 6
            cols = 6
        }else if(level_idex == 4){
            piecesNumber = 64
            rows = 8
            cols = 8
        }else{
            piecesNumber = 16
            rows = 4
            cols = 4
        }
        val imageView = findViewById<ImageView>(R.id.imageView)
        val pieces = ArrayList<PuzzlePiece?>(piecesNumber)

        // Get the scaled bitmap of the source image
        val drawable = imageView.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val dimensions = getBitmapPositionInsideImageView(imageView)
        val scaledBitmapLeft = dimensions[0]
        val scaledBitmapTop = dimensions[1]
        val scaledBitmapWidth = dimensions[2]
        val scaledBitmapHeight = dimensions[3]
        val croppedImageWidth = scaledBitmapWidth - 2 * Math.abs(scaledBitmapLeft)
        val croppedImageHeight = scaledBitmapHeight - 2 * Math.abs(scaledBitmapTop)
        val scaledBitmap =
            Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true)
        val croppedBitmap = Bitmap.createBitmap(
            scaledBitmap,
            Math.abs(scaledBitmapLeft),
            Math.abs(scaledBitmapTop),
            croppedImageWidth,
            croppedImageHeight
        )

        // Calculate the with and height of the pieces
        val pieceWidth = croppedImageWidth / cols
        val pieceHeight = croppedImageHeight / rows

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                // calculate offset for each piece
                var offsetX = 0
                var offsetY = 0
                if (col > 0) {
                    offsetX = pieceWidth / 3
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3
                }

                // apply the offset to each piece
                val pieceBitmap = Bitmap.createBitmap(
                    croppedBitmap,
                    xCoord - offsetX,
                    yCoord - offsetY,
                    pieceWidth + offsetX,
                    pieceHeight + offsetY
                )
                val piece = PuzzlePiece(applicationContext)
                piece.setImageBitmap(pieceBitmap)
                piece.xCoord = xCoord - offsetX + imageView.left
                piece.yCoord = yCoord - offsetY + imageView.top
                piece.pieceWidth = pieceWidth + offsetX
                piece.pieceHeight = pieceHeight + offsetY

                // this bitmap will hold our final puzzle piece image
                val puzzlePiece = Bitmap.createBitmap(
                    pieceWidth + offsetX,
                    pieceHeight + offsetY,
                    Bitmap.Config.ARGB_8888
                )

                // draw path
                val bumpSize = pieceHeight / 4
                val canvas = Canvas(puzzlePiece)
                val path = Path()
                path.moveTo(offsetX.toFloat(), offsetY.toFloat())
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                } else {
                    // top bump
                    path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        offsetY.toFloat()
                    )
                    path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (offsetY - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        offsetY.toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                }
                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // right bump
                    path.lineTo(
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                    )
                    path.cubicTo(
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        (pieceBitmap.width - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        pieceBitmap.width.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                }
                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // bottom bump
                    path.lineTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 3 * 2).toFloat(),
                        pieceBitmap.height.toFloat()
                    )
                    path.cubicTo(
                        (offsetX + (pieceBitmap.width - offsetX) / 6 * 5).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 6).toFloat(),
                        (pieceBitmap.height - bumpSize).toFloat(),
                        (offsetX + (pieceBitmap.width - offsetX) / 3).toFloat(),
                        pieceBitmap.height.toFloat()
                    )
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                }
                if (col == 0) {
                    // left side piece
                    path.close()
                } else {
                    // left bump
                    path.lineTo(
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3 * 2).toFloat()
                    )
                    path.cubicTo(
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6 * 5).toFloat(),
                        (offsetX - bumpSize).toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 6).toFloat(),
                        offsetX.toFloat(),
                        (offsetY + (pieceBitmap.height - offsetY) / 3).toFloat()
                    )
                    path.close()
                }

                // mask the piece
                val paint = Paint()
                paint.color = -0x1000000
                paint.style = Paint.Style.FILL
                canvas.drawPath(path, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)

                // draw a white border
                var border = Paint()
                border.color = -0x7f000001
                border.style = Paint.Style.STROKE
                border.strokeWidth = 8.0f
                canvas.drawPath(path, border)

                // draw a black border
                border = Paint()
                border.color = -0x80000000
                border.style = Paint.Style.STROKE
                border.strokeWidth = 3.0f
                canvas.drawPath(path, border)

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece)
                pieces.add(piece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }

    private fun getBitmapPositionInsideImageView(imageView: ImageView?): IntArray {
        val ret = IntArray(4)
        if (imageView == null || imageView.drawable == null) return ret

        // Get image dimensions
        // Get image matrix values and place them in an array
        val f = FloatArray(9)
        imageView.imageMatrix.getValues(f)

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        val d = imageView.drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        // Calculate the actual dimensions
        val actW = Math.round(origW * scaleX)
        val actH = Math.round(origH * scaleY)
        ret[2] = actW
        ret[3] = actH

        // Get image position
        // We assume that the image is centered into ImageView
        val imgViewW = imageView.width
        val imgViewH = imageView.height
        val top = (imgViewH - actH) / 2
        val left = (imgViewW - actW) / 2
        ret[0] = left
        ret[1] = top
        return ret
    }

    fun checkGameOver(index:Int)  {
        if(index == 1){
            resetTimer()
            constraintLayout.visibility = View.GONE
            textView2.visibility = View.VISIBLE
            textView11.visibility = View.VISIBLE
            textView11.text = "Game Over"
            vibratePhone()
            imageView12.visibility = View.VISIBLE
        }else {
            if (isGameOver) {
                resetTimer()
                vibratePhone()
                constraintLayout.visibility = View.GONE
                textView2.visibility = View.VISIBLE
                textView11.visibility = View.VISIBLE
                textView11.text = "PUZZLE COMPLETED.\nCONGRATULATIONS!"
                imageView12.visibility = View.VISIBLE
            }
        }
    }

    private val isGameOver: Boolean
        private get() {
            for (piece in pieces!!) {
                if (piece!!.canMove) {
                    return false
                }
            }
            return true
        }

    private fun setPicFromPath(mCurrentPhotoPath: String, imageView: ImageView) {
        // Get the dimensions of the View
        val targetW = imageView.width
        val targetH = imageView.height

        // Get the dimensions of the bitmap
        val bmOptions = Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
        var rotatedBitmap = bitmap

        // rotate bitmap if needed
        try {
            val ei = ExifInterface(mCurrentPhotoPath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap = rotateImage(bitmap, 90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap = rotateImage(bitmap, 180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap = rotateImage(bitmap, 270f)
            }
        } catch (e: IOException) {
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
        imageView.setImageBitmap(rotatedBitmap)
    }

    fun vibratePhone() {
        val vibration = PreferenceManager.getDefaultSharedPreferences(this).getInt("vibration", 1)
        if(vibration == 1){
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(200)
            }
        }
    }

    companion object {
        private var START_TIME_IN_MILLIS: Long = 60000 * 4
        fun rotateImage(source: Bitmap, angle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(
                source, 0, 0, source.width, source.height,
                matrix, true
            )
        }
    }

    private fun startTimer(): Boolean {
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
        mCountDownTimer1 = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
            }

        }.start()
        mTimerRunning = true

        return true
    }

    private fun pauseTimer() {
        mCountDownTimer1!!.cancel()
        mTimerRunning = false
    }

    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        pauseTimer()
    }

    private fun updateCountDownText() {

        val hours = ((mTimeLeftInMillis / (1000 * 60 * 60)) % 24)
        val minutes = ((mTimeLeftInMillis / (1000 * 60)) % 60)
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted =
            String.format(Locale.getDefault(), "%2d:%02d", minutes, seconds)
        textView10!!.text = timeLeftFormatted

        if(minutes.toInt() == 0 && seconds == 0){
            checkGameOver(1)
        }
    }

    override fun onBackPressed() {
        val sound = PreferenceManager.getDefaultSharedPreferences(this).getInt("sound", 1)
        if(sound == 1){
            Music.mediaplayer_sound!!.start()
        }else{

        }
        PreferenceManager.getDefaultSharedPreferences(this).edit()
            .putInt("onStops", 1).apply()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        val onStops = PreferenceManager.getDefaultSharedPreferences(this).getInt("onStops", 0)
        if(onStops == 0){
            mediaplayer_music!!.pause()
        }
    }

}