package com.example.learningandroid2020

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import org.json.JSONObject
import android.widget.ListView

const val REPO_NAME = "com.example.learningandroid2020.REPO_NAME"
const val REPO_DESC = "com.example.learningandroid2020.REPO_DESC"
const val REPO_STAR = "com.example.learningandroid2020.REPO_STAR"

class MainActivity : AppCompatActivity() {
    var dataList = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GithubTask().execute()
    }

    inner class GithubTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            return URL("https://api.github.com/search/repositories?q=tiep").readText(
                Charsets.UTF_8
            )
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

            val jsonObj = JSONObject(result)
            val repositories = jsonObj.getJSONArray("items")

            for (i in 0 until 14) {
                val item = repositories.getJSONObject(i)

                val map = HashMap<String, String>()
                map["name"] = item.getString("name")
                map["star"] = item.getString("stargazers_count")
                map["description"] = item.getString("description")
                map["avatar_url"] = item.getJSONObject("owner").getString("avatar_url")
                dataList.add(i, map)
            }

            val listView = findViewById<ListView>(R.id.listView)
            listView.adapter = GithubAdapter(this@MainActivity, dataList)

            listView.setOnItemClickListener { _, _, position, _ ->
                Log.i("--------dataList", dataList.toString())
                val repository = dataList[position]
                Log.i("--------repository", repository.toString())

                val intent = Intent(this@MainActivity, RepositoryActivity::class.java)

                intent.putExtra(REPO_NAME, repository["name"])
                intent.putExtra(REPO_DESC, repository["description"])
                intent.putExtra(REPO_STAR, repository["star"])

                startActivity(intent)
            }
        }
    }
}
