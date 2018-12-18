package com.example.initialproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.initialproject.R
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


@SuppressLint("Registered")
//显示日报详情的activity
class ContentActivity : AppCompatActivity() {

    private var ID: Int = 0
    private var responseData: String? = null
    private var imageView: ImageView? = null
    private var webView: WebView? = null
    private var textView: TextView? = null
    private var title: String? = null

    fun init() {
        Thread(Runnable {
            val client = OkHttpClient()
            val request = Request.Builder().url("https://news-at.zhihu.com/api/3/news/$ID").build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                if (response != null) {
                    if (response.body() != null) {
                        responseData = response.body()!!.string()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            show(responseData)
        }).start()
    }

    fun show(str: String?) {
        runOnUiThread(Runnable {
            if (str == null) {
                Toast.makeText(this@ContentActivity, "没有网络 (≧ω≦)", Toast.LENGTH_SHORT).show()
                return@Runnable
            }
            try {
                val `object` = JSONObject(str)
                val imageArray = `object`.getJSONArray("images")
                imageView = findViewById(R.id.contentImageView)
                webView = findViewById(R.id.webView)
                textView = findViewById(R.id.textView)
                var content = `object`.getString("body")
                content = content.replace("<img", "<img style=max-width:100%;height:auto")
                //这一步使图片适应屏幕，用户体验++
                Glide.with(this@ContentActivity).load(imageArray.getString(0)).into(imageView!!)
                webView!!.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
                textView!!.text = title
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contentlayout)
        val intent = intent
        title = intent.getStringExtra("title")
        ID = intent.getIntExtra("id", 233)
        init()
    }
}