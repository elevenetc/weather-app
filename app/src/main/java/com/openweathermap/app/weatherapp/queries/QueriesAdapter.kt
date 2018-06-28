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
import butterknife.OnClick
import com.openweathermap.app.weatherapp.R

class QueriesAdapter(
        private val queries: List<SearchQuery>,
        private val context: Context,
        private val deleteItemHandler: (SearchQuery) -> Unit) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): VH {
        return VH(
                context,
                LayoutInflater.from(viewGroup.context).inflate(R.layout.view_search_query, viewGroup, false),
                deleteItemHandler
        )
    }

    override fun getItemCount(): Int {
        return queries.size
    }

    override fun onBindViewHolder(vh: VH, position: Int) {
        vh.bindView(queries[position])
    }

}

class VH(
        val context: Context,
        view: View,
        private val deleteItemHandler: (SearchQuery) -> Unit) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.text_date)
    lateinit var textDate: TextView

    @BindView(R.id.text_name)
    lateinit var textName: TextView

    @BindView(R.id.text_zip_code)
    lateinit var textZip: TextView

    @BindView(R.id.text_location)
    lateinit var textLocation: TextView

    lateinit var query: SearchQuery

    init {
        ButterKnife.bind(this, view)
    }

    fun bindView(query: SearchQuery) {
        this.query = query
        setOnVisible(textDate, "", query.createdAt.toString())
        setOnVisible(textName, context.getString(R.string.name), query.name)
        setOnVisible(textZip, context.getString(R.string.zip_code), query.zipCode)
        setOnVisible(textLocation, context.getString(R.string.location),
                if (query.hasLocation()) {
                    "${query.lat}, ${query.lon}"
                } else {
                    ""
                }
        )
    }

    @OnClick(R.id.btn_delete)
    fun deleteHandler() {
        deleteItemHandler(query)
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