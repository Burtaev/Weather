package com.burtaev.weather.main.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { getViewModel(T::class, qualifier, parameters) }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(T::class.java, this)

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding == null)
            binding = bindingClass.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as T
        return binding!!
    }
}
