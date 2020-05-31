package com.example.learningandroid2020

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class GithubAdapter(private val context: Context,
                    private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]

        val rowView = inflater.inflate(R.layout.list_row, parent, false)
        rowView.findViewById<TextView>(R.id.row_name).text = dataitem.get("name")
        rowView.findViewById<TextView>(R.id.row_description).text = dataitem.get("description")

        Picasso.get()
            .load(dataitem.get("avatar_url"))
            .resize(50, 50)
            .centerCrop()
            .into(rowView.findViewById<ImageView>(R.id.row_image))

        rowView.tag = position
        return rowView
    }
}