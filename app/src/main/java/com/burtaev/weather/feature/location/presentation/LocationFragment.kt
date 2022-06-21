package com.burtaev.weather.feature.location.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.burtaev.weather.R
import com.burtaev.weather.databinding.FragmentLocationBinding
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import com.burtaev.weather.feature.weather.presentation.ID_LOCATION
import com.burtaev.weather.main.utils.hideKeyboard
import com.burtaev.weather.main.utils.showKeyboard
import com.burtaev.weather.main.utils.viewBinding
import com.burtaev.weather.main.utils.viewModel

class LocationFragment : Fragment(R.layout.fragment_location) {

    private val viewModel: LocationViewModel by viewModel()

    private val binding by viewBinding<FragmentLocationBinding>()

    private val searchViewListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(locationName: String) = false

        override fun onQueryTextChange(locationName: String): Boolean {
            if (locationName.isNotBlank()) {
                viewModel.searchAddressFromLocationName(locationName)
            }
            return false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSaveAddressRV()
        initSearchAddressRV()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun navigateToWeather(idAddress: Long) {
        viewModel.saveIdStorage(idAddress)
        this.findNavController().navigate(R.id.nav_weather, bundleOf(ID_LOCATION to idAddress))
    }

    private fun initSaveAddressRV() {
        val adapter = SearchAddressListAdapter(
            { listenerSelect ->
                navigateToWeather(listenerSelect.id)
            },
            { listenerDelete ->
                viewModel.delAddressInDB(listenerDelete)
            }
        )
        binding.rvLocalAddressesList.adapter = adapter

        viewModel.addressListLocalLiveData.observe(this) {

            adapter.submitList(it)
        }
    }

    private fun initSearchAddressRV() {
        val adapter = LocalAddressAdapter {
            val address =
                AddressEntity(
                    address = it.getAddressLine(0),
                    lat = it.latitude,
                    lng = it.longitude
                )
            saveAddressToDB(address)
        }
        binding.rvSearchAddress.adapter = adapter
        viewModel.addressLiveData.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun saveAddressToDB(addressEntity: AddressEntity) {
        viewModel.saveAddressToDB(addressEntity)
        viewModel.idNewAddressLiveData.observe(this) { id ->
            navigateToWeather(idAddress = id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchItem = menu.findItem(R.id.mi_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(searchViewListener)
        searchView.isFocusable = true
        searchView.isIconified = false
        searchItem.apply {
            actionView = searchView
            expandActionView()
        }
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchView.showKeyboard()
            } else {
                searchView.hideKeyboard()
            }
        }

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                searchView.isIconified = false
                searchView.requestFocusFromTouch()
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                searchView.setQuery("", true)
                return true
            }
        })
    }
}
