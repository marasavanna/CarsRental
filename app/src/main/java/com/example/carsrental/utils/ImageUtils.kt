package com.example.carsrental.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Base64
import com.example.carsrental.R
import java.io.ByteArrayOutputStream


object ImageUtils {

    fun String?.toBitmap(context: Context): Bitmap {
        this?.let {
            val base64image = Base64.decode(this.substring(this.indexOf(",")  + 1), Base64.DEFAULT)
            if (base64image != null && base64image.isNotEmpty()) {
                return BitmapFactory.decodeByteArray(base64image, 0, base64image.size)
            }
        }
        return BitmapFactory.decodeResource(context.resources, R.drawable.image_placeholder)
    }

    fun Uri?.toBase64Image(context: Context): String {
        this?.let {
            val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, this))
            return encodeImage(bitmap)
        }
        return ""
    }

    fun encodeImage(bitmap: Bitmap): String {
        var options: BitmapFactory.Options? = null
        options = BitmapFactory.Options()
        options.inSampleSize = 3
        var encodedString = ""
        val stream = ByteArrayOutputStream()
        // Must compress the Image to reduce image size to make upload
        // easy
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        } catch (e1: Exception) {
            println("Err")
            // TODO Auto-generated catch block
        }
        val byte_arr = stream.toByteArray()
        // Encode Image to String
        try {
            encodedString = Base64.encodeToString(byte_arr, 0)
        } catch (e: Exception) {
            println("Err")
        }
        return encodedString.replace("\n", "")
    }
}