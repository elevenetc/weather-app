package com.openweathermap.app.weatherapp.queries

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.openweathermap.app.weatherapp.R
import com.openweathermap.app.weatherapp.common.BaseFragment

class QueriesFragment : BaseFragment() {

    @BindView(R.id.list_queries)
    lateinit var listQueries: RecyclerView

    @BindView(R.id.text_status)
    lateinit var textStatus: TextView

    private lateinit var binder: Unbinder
    private val viewModel: QueriesViewModel by lazy { appComponent.queriesComponent().viewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result = inflater.inflate(R.layout.fragment_queries, container, false)
        binder = ButterKnife.bind(this, result)
        listQueries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return result
    }

    override fun onDestroyView() {
        binder.unbind()
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        subs.add(viewModel.getQueries()
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe(handleState())
        )
    }

    @OnClick(R.id.btn_delete_all)
    fun deleteAllHandler() {
        subs.add(viewModel.deleteAllQueries()
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe(handleState())
        )
    }

    private fun handleState(): (QueriesState) -> Unit {
        return { state ->
            when {
                state.type == QueriesState.Type.PROGRESS -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    textStatus.setText(R.string.getting_recent_queries)
                }
                state.type == QueriesState.Type.EMPTY -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    textStatus.setText(R.string.no_recent_queries)
                }
                state.type == QueriesState.Type.RESULT -> {
                    listQueries.visibility = View.VISIBLE
                    textStatus.visibility = View.INVISIBLE
                    textStatus.setText(R.string.no_recent_queries)
                    listQueries.adapter = QueriesAdapter(state.queries, context!!)
                }
                state.type == QueriesState.Type.DELETING_QUERIES -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    textStatus.setText(R.string.deleting_queries)
                }
                state.type == QueriesState.Type.ALL_DELETED -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    textStatus.setText(R.string.all_queries_delete)
                }
            }
        }
    }


    companion object {
        fun newInstance() = QueriesFragment()
    }
}