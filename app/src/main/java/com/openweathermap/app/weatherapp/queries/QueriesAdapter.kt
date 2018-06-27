package com.openweathermap.app.weatherapp.queries

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.openweathermap.app.weatherapp.R

class QueriesAdapter(val queries: List<SearchQuery>, val context: Context) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): VH {
        return VH(LayoutInflater.from(viewGroup.context).inflate(R.layout.view_search_query, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return queries.size
    }

    override fun onBindViewHolder(vh: VH, position: Int) {
        val query = queries[position]
        setOnVisible(vh.textDate, "", query.createdAt.toString())
        setOnVisible(vh.textName, context.getString(R.string.name), query.name)
        setOnVisible(vh.textZip, context.getString(R.string.zip_code), query.zipCode)
        setOnVisible(vh.textLocation, context.getString(R.string.location),
                if (query.hasLocation()) {
                    "${query.lat}, ${query.lon}"
                } else {
                    ""
                }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setOnVisible(textView: TextView, prefix: String, value: String) {
        if (value.isEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = "$prefix: $value"
        }
    }
}

class VH(view: View) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.text_date)
    lateinit var textDate: TextView

    @BindView(R.id.text_name)
    lateinit var textName: TextView

    @BindView(R.id.text_zip_code)
    lateinit var textZip: TextView

    @BindView(R.id.text_location)
    lateinit var textLocation: TextView

    init {
        ButterKnife.bind(this, view)
    }
}