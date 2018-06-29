package com.openweathermap.app.weatherapp.search


import android.os.Bundle
import android.support.annotation.StringRes
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
import com.openweathermap.app.weatherapp.common.RequestCodes


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

    @BindView(R.id.btn_search_for_current_location)
    lateinit var btnSearchCurrentLocation: Button

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subs.add(viewModel.findWeatherByRecentSearch()
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe { state -> handleState(state) })
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

    @OnClick(R.id.btn_search_for_current_location)
    fun searchForCurrentLocation() {
        appComponent.permissions().checkLocationPermission(this) {
            getCurrentLocationAndFindWeather()
        }
    }

    @OnClick(R.id.btn_recent_queries)
    fun recentQueriesHandler() {
        appComponent.navigator().goToRecentQueries(activity!!)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RequestCodes.LOCATION_PERMISSION) getCurrentLocationAndFindWeather()
    }

    private fun handleState(state: SearchState) {
        when {
            state.type == SearchState.Type.IDLE -> {
                weatherContainer.visibility = View.GONE
                textStatus.visibility = View.GONE
                btnSearchCity.isEnabled = true
                btnSearchZip.isEnabled = true
                btnSearchCurrentLocation.isEnabled = true
                textStatus.text = ""
            }
            state.type == SearchState.Type.PROGRESS -> {
                weatherContainer.visibility = View.GONE
                textStatus.visibility = View.VISIBLE
                btnSearchCity.isEnabled = false
                btnSearchZip.isEnabled = false
                btnSearchCurrentLocation.isEnabled = false
                textStatus.setText(R.string.looking_for_weather)
            }
            state.type == SearchState.Type.RESULT -> {
                weatherContainer.visibility = View.VISIBLE
                textStatus.visibility = View.GONE
                textLocationName.text = state.weather.name
                btnSearchCity.isEnabled = true
                btnSearchZip.isEnabled = true
                btnSearchCurrentLocation.isEnabled = true
                textTemp.text = state.weather.temperature.toString()
            }
            state.type == SearchState.Type.NOT_FOUND -> {
                setErrorState(R.string.not_found_weather)
            }
            state.type == SearchState.Type.NO_LOCATION_ERROR -> {
                setErrorState(R.string.location_is_unavailable)
            }
            state.type == SearchState.Type.NETWORK_ERROR -> {
                setErrorState(R.string.network_error)
            }
        }
    }

    private fun setErrorState(@StringRes message: Int) {
        weatherContainer.visibility = View.GONE
        textStatus.visibility = View.VISIBLE
        btnSearchCity.isEnabled = true
        btnSearchZip.isEnabled = true
        btnSearchCurrentLocation.isEnabled = true
        textStatus.setText(message)
    }

    private fun getCurrentLocationAndFindWeather() {
        subs.add(viewModel.findWeatherAtCurrentLocation()
                .subscribeOn(appComponent.schedulers().background())
                .observeOn(appComponent.schedulers().ui())
                .subscribe { state -> handleState(state) })
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
