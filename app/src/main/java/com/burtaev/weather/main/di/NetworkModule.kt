package com.burtaev.weather.main.di

import com.burtaev.weather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "key"

private const val BASE_URL =
    "https://api.weatherapi.com/v1/"

inline fun <reified T> createRetrofitService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

private val networkModule = module {

    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun provideOkHttpClient(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(httpLoggingInterceptor)
    }
    okHttpClient.addInterceptor {
        val request = it.request()
        val url = request.url().newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.OPEN_WEATHER_API_KEY)
            .build()
        it.proceed(request.newBuilder().url(url).build())
    }
    return okHttpClient.build()
}

val NetworkModules = listOf(
    networkModule
)
