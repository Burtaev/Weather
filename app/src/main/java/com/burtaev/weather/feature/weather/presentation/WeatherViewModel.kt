package com.burtaev.weather.feature.weather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import com.burtaev.weather.feature.weather.domain.WeatherUseCase
import com.burtaev.weather.feature.weather.domain.models.Weather
import com.burtaev.weather.main.sheduler.SchedulerProvider
import com.burtaev.weather.main.utils.subscribeSuccess
import io.reactivex.rxjava3.disposables.CompositeDisposable

class WeatherViewModel(
    private val useCase: WeatherUseCase,
    private val scheduler: SchedulerProvider,
) : ViewModel() {
    private val disposables = CompositeDisposable()

    private val weatherLiveDataMutable = MutableLiveData<Weather>()
    val weatherLiveData: LiveData<Weather> = weatherLiveDataMutable

    private val isLoading = MutableLiveData<Boolean>()

    private val addressLiveDataMutable = MutableLiveData<AddressEntity>()
    val addressLiveData: LiveData<AddressEntity> = addressLiveDataMutable

    fun getWeather(latLng: String) =
        disposables.add(
            useCase.getResponse(latLng)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doOnSubscribe {
                    isLoading.postValue(true)
                }
                .doOnError {
                    isLoading.postValue(true)
                }
                .subscribeSuccess {
                    weatherLiveDataMutable.postValue(it)
                }
        )

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun getAddressById(id: Long) {
        disposables.add(
            useCase.getAddressById(id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeSuccess {
                    addressLiveDataMutable.postValue(it)
                }
        )
    }

    fun getIdStorage() = useCase.getIdInStorage()
}
