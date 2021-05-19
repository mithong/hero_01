package com.sun.hero_01.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL

class LoadImageBitmap(
    private val imageView: ImageView
) : AsyncTask<String?, Void?, Bitmap?>() {

    override fun doInBackground(vararg params: String?): Bitmap? {
        val url = URL(params[0].toString())
        val connection = url.openConnection() as HttpURLConnection
        connection.apply {
            connectTimeout = TIME_OUT
            readTimeout = TIME_OUT
            requestMethod = METHOD_GET
            connect()
        }
        return BitmapFactory.decodeStream(url.openStream())
    }

    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }

    companion object {
        const val TIME_OUT = 15000
        const val METHOD_GET = "GET"
    }
}
