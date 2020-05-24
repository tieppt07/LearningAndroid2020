package com.example.learningandroid2020

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.github.com/search/repositories?q=tiep&sort=stars&order=desc")
            .build()

        val response: Response = client.newCall(request).execute()

        Log.d("response", response.body().string());
    }
}

