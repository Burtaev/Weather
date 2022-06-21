package com.burtaev.weather.feature.location.presentation

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.burtaev.weather.feature.location.database.domain.entities.AddressEntity
import com.burtaev.weather.feature.location.domain.LocationUseCase
import com.burtaev.weather.main.sheduler.SchedulerProvider
import com.burtaev.weather.main.utils.subscribeSuccess
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

private const val DELAY_MS = 250L

class LocationViewModel(
    private val useCase: LocationUseCase,
    private val scheduler: SchedulerProvider
) :
    ViewModel() {
    private val disposables = CompositeDisposable()
    private val searchPublishSubject: PublishSubject<String> =
        PublishSubject.create()
    private val addressLiveDataMutable: MutableLiveData<List<Address>?> = MutableLiveData()
    val addressLiveData: LiveData<List<Address>?> = addressLiveDataMutable

    private val addressListLocalLiveDataMutable: MutableLiveData<List<AddressEntity>> =
        MutableLiveData()
    val addressListLocalLiveData: LiveData<List<AddressEntity>> = addressListLocalLiveDataMutable

    private val idNewAddressLiveDataMutable: MutableLiveData<Long> = MutableLiveData()
    val idNewAddressLiveData: LiveData<Long> = idNewAddressLiveDataMutable

    init {
        getAddress()
        initSearchPublishSubject()
    }

    private fun getAddress() {
        disposables.add(

            useCase.getAddress()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    addressListLocalLiveDataMutable.postValue(it)
                }, { })
        )
    }

    fun searchAddressFromLocationName(locationName: String) {
        searchPublishSubject.onNext(locationName)
    }

    private fun initSearchPublishSubject() =
        disposables.add(
            searchPublishSubject.debounce(DELAY_MS, TimeUnit.MILLISECONDS)
                .switchMapSingle {
                    useCase.getAddressListByName(it)
                }
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe {
                    addressLiveDataMutable.postValue(it)
                }
        )

    fun saveAddressToDB(
        addressEntity: AddressEntity
    ) {
        disposables.add(
            useCase.setAddress(addressEntity)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribeSuccess { idNewAddressLiveDataMutable.postValue(it) }
        )
    }

    fun delAddressInDB(addressEntity: AddressEntity) {
        disposables.add(
            Single.fromCallable { useCase.delAddress(addressEntity) }
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe()
        )
    }

    fun saveIdStorage(id: Long) {
        useCase.saveIdInStorage(id)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
