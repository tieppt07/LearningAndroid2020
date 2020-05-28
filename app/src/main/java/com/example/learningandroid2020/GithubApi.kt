package com.example.learningandroid2020

import android.os.AsyncTask

class GithubApi() : AsyncTask<Void, Void, String>() {
    override fun doInBackground(vararg params: Void?): String? {
        return "tiep"
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // ...
    }

}