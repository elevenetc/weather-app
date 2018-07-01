package com.openweathermap.app.weatherapp.queries

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.openweathermap.app.weatherapp.R
import com.openweathermap.app.weatherapp.common.BaseFragment
import com.openweathermap.app.weatherapp.common.MainActivity

class QueriesFragment : BaseFragment() {

    @BindView(R.id.list_queries)
    lateinit var listQueries: RecyclerView

    @BindView(R.id.text_status)
    lateinit var textStatus: TextView

    @BindView(R.id.btn_delete_all)
    lateinit var btnDeleteAll: Button

    private lateinit var binder: Unbinder
    private val viewModel: QueriesViewModel by lazy { appComponent.queriesComponent().viewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result = inflater.inflate(R.layout.fragment_queries, container, false)
        binder = ButterKnife.bind(this, result)
        listQueries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subs.add(viewModel.getAllQueries()
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe(handleState())
        )
    }

    override fun onDestroyView() {
        binder.unbind()
        super.onDestroyView()
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
                    btnDeleteAll.isEnabled = false
                    textStatus.setText(R.string.getting_recent_queries)
                }
                state.type == QueriesState.Type.EMPTY -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    btnDeleteAll.isEnabled = false
                    textStatus.setText(R.string.no_recent_queries)
                }
                state.type == QueriesState.Type.RESULT -> {
                    listQueries.visibility = View.VISIBLE
                    textStatus.visibility = View.INVISIBLE
                    btnDeleteAll.isEnabled = true
                    textStatus.setText(R.string.no_recent_queries)
                    listQueries.adapter = QueriesAdapter(state.queries, context!!, { deleteItem(it) }, { searchFor(it) })
                }
                state.type == QueriesState.Type.DELETING_QUERIES -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    btnDeleteAll.isEnabled = false
                    textStatus.setText(R.string.deleting_queries)
                }
                state.type == QueriesState.Type.ALL_DELETED -> {
                    listQueries.visibility = View.INVISIBLE
                    textStatus.visibility = View.VISIBLE
                    btnDeleteAll.isEnabled = false
                    textStatus.setText(R.string.all_queries_delete)
                }
            }
        }
    }

    private fun deleteItem(query: SearchQuery) {
        subs.add(viewModel.deleteQuery(query)
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe(handleState()))
    }

    private fun searchFor(query: SearchQuery) {
        appComponent.navigator().goToSearch(activity as MainActivity, queryId = query.id)
    }

    companion object {
        fun newInstance() = QueriesFragment()
    }
}