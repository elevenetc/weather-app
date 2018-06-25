package com.openweathermap.app.weatherapp.search


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.openweathermap.app.weatherapp.R
import com.openweathermap.app.weatherapp.common.BaseFragment


class SearchFragment : BaseFragment() {

    @BindView(R.id.weather_container)
    lateinit var weatherContainer: View

    @BindView(R.id.edit_search_city)
    lateinit var editSearchCity: EditText

    @BindView(R.id.edit_search_zip)
    lateinit var editSearchZip: EditText

    @BindView(R.id.text_location_name)
    lateinit var textLocationName: TextView

    @BindView(R.id.text_status)
    lateinit var textStatus: TextView

    @BindView(R.id.btn_search_city)
    lateinit var btnSearchCity: Button

    @BindView(R.id.btn_search_zip)
    lateinit var btnSearchZip: Button

    @BindView(R.id.text_temp)
    lateinit var textTemp: TextView

    private lateinit var binder: Unbinder

    private val viewModel: SearchViewModel by lazy { appComponent.searchComponent().viewModel() }

    init {
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result = inflater.inflate(R.layout.fragment_search, container, false)
        binder = ButterKnife.bind(this, result)
        return result
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.latestState()
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe({ state ->
                    handleState(state)
                })
    }

    override fun onDestroyView() {
        binder.unbind()
        super.onDestroyView()
    }

    @OnClick(R.id.btn_search_city)
    fun searchCityHandler() {

        val query = editSearchCity.text.toString()

        subs.add(viewModel.findWeatherByCity(query)
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe { state -> handleState(state) })
    }

    @OnClick(R.id.btn_search_zip)
    fun searchZipCodeHandler() {

        val query = editSearchZip.text.toString()

        subs.add(viewModel.findWeatherByZip(query)
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe { state -> handleState(state) })
    }


    private fun handleState(state: SearchState) {
        when {
            state.type == SearchState.Type.NOT_FOUND -> {
                weatherContainer.visibility = View.GONE
                textStatus.visibility = View.VISIBLE
                btnSearchCity.isEnabled = true
                btnSearchZip.isEnabled = true
                textStatus.setText(R.string.not_found_weather)
            }
            state.type == SearchState.Type.PROGRESS -> {
                weatherContainer.visibility = View.GONE
                textStatus.visibility = View.VISIBLE
                btnSearchCity.isEnabled = false
                btnSearchZip.isEnabled = false
                textStatus.setText(R.string.looking_for_weather)
            }
            state.type == SearchState.Type.RESULT -> {
                weatherContainer.visibility = View.VISIBLE
                textStatus.visibility = View.GONE
                textLocationName.text = state.weather.name
                btnSearchCity.isEnabled = true
                btnSearchZip.isEnabled = true
                textTemp.text = state.weather.temperature.toString()
            }
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
