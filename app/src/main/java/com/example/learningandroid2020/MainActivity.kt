package com.example.learningandroid2020

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object: AsyncTask<Void, Void, String?>() {

            override fun doInBackground(vararg params: Void?): String? {
                return getContent()
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                println("-------------------- result: $result")
            }

        }.execute()
    }

    private fun getContent(): String? {
        return try {
            val request = Request.Builder()
                .url("https://api.github.com/search/repositories?q=tiep")
                .build()
            client.newCall(request).execute().body?.string()
        } catch (e: Exception) {
            e.message
        }
    }
}
