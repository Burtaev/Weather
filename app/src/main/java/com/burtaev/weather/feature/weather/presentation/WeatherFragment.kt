package com.burtaev.weather.feature.weather.presentation

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.burtaev.weather.R
import com.burtaev.weather.databinding.FragmentWeatherBinding
import com.burtaev.weather.feature.weather.presentation.adapter.DailyAdapter
import com.burtaev.weather.feature.weather.presentation.adapter.HourlyAdapter
import com.burtaev.weather.main.utils.viewBinding
import com.burtaev.weather.main.utils.viewModel

const val ID_LOCATION = "idLocation"
const val DEFAULT_ID = -1L

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModel()

    private val binding by viewBinding<FragmentWeatherBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (val id = viewModel.getIdStorage()) {
            DEFAULT_ID -> navigateToLocation()
            else -> {
                viewModel.getAddressById(id)
                viewModel.addressLiveData.observe(this) { addressEntity ->
                    binding.tvName.text = addressEntity.address
                    viewModel.getWeather("${addressEntity.lat},${addressEntity.lng}")
                }
                setWeather()
                initHourlyRV()
                initDailyRV()
            }
        }
    }

    private fun navigateToLocation() {
        this.findNavController().navigate(R.id.nav_location)
    }

    private fun initDailyRV() {
        val adapterDaily = DailyAdapter()

        binding.rvDaily.adapter = adapterDaily

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            adapterDaily.submitList(weather.forecast.forecastdayResponse)
        }
    }

    private fun initHourlyRV() {
        val adapter = HourlyAdapter()

        binding.rvHours.adapter = adapter

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            adapter.submitList(weather.forecast.forecastdayResponse[0].hours)
        }
    }

    private fun setWeather() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            binding.tvTemp.text = weather.current.temp
            binding.tvDateUpdate.text = weather.location.date
            binding.tvDescription.text = weather.current.condition.description
            binding.tvFeelslike.text = weather.current.feelslike
            binding.tvFeelslikeText.visibility = VISIBLE
            Glide
                .with(this)
                .load(weather.current.condition.iconUrl)
                .into(binding.ivIcon)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_search -> {
                navigateToLocation()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
