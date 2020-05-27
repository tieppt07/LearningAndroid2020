package com.example.learningandroid2020

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    val CONNECTON_TIMEOUT_MILLISECONDS = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGetInfo.setOnClickListener {
            var city = edtCityName.text.toString()
            val url =
                "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + city + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys"
            GetWeatherAsyncTask().execute(url)
        }
    }

    inner class GetWeatherAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            // Before doInBackground
        }

        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val url = URL(urls[0])

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = CONNECTON_TIMEOUT_MILLISECONDS
                urlConnection.readTimeout = CONNECTON_TIMEOUT_MILLISECONDS

                var inString = streamToString(urlConnection.inputStream)
                Log.i("string", inString);

                publishProgress(inString)
            } catch (ex: Exception) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json = JSONObject(values[0])

                val query = json.getJSONObject("query")
                val results = query.getJSONObject("results")
                val channel = results.getJSONObject("channel")

                val location = channel.getJSONObject("location")
                val city = location.get("city")
                val country = location.get("country")

                val humidity = channel.getJSONObject("atmosphere").get("humidity")

                val condition = channel.getJSONObject("item").getJSONObject("condition")
                val temp = condition.get("temp")
                val text = condition.get("text")

                tvWeatherInfo.text =
                    "Location: " + city + " - " + country + "\n" +
                            "Humidity: " + humidity + "\n" +
                            "Temperature: " + temp + "\n" +
                            "Status: " + text

            } catch (ex: Exception) {

            }
        }

        override fun onPostExecute(result: String?) {
            // Done
        }
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                Log.i("line", line)
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return result
    }
}
