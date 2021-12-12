package com.example.carsrental.di

import android.content.Context
import androidx.room.Room
import com.example.carsrental.api.AuthService
import com.example.carsrental.api.CarService
import com.example.carsrental.api.UnsafeOkHttpClient.getUnsafeOkHttpClient
import com.example.carsrental.persistance.CarDatabase
import com.example.carsrental.utils.PreferenceHelper
import com.example.carsrental.utils.PreferenceHelper.get
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Array.get
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*
private const val BASE_URL = "https://10.0.2.2:44325/api/"

val networkModule = module {

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun provideOkHttpClient(context: Context): OkHttpClient? {
        return getUnsafeOkHttpClient(context)?.build()
    }

    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    fun provideCarService(retrofit: Retrofit): CarService =
        retrofit.create(CarService::class.java)


    single { provideOkHttpClient(androidContext()) }

    single { provideRetrofit(get()) }
    single{ provideAuthService(get()) }
    single{ provideCarService(get()) }
}