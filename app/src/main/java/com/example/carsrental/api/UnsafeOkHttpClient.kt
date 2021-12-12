package com.example.carsrental.api

import android.content.Context
import com.example.carsrental.utils.PreferenceHelper
import com.example.carsrental.utils.PreferenceHelper.get
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object UnsafeOkHttpClient {
    fun getUnsafeOkHttpClient(context: Context): OkHttpClient.Builder? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {

                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val headersInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Authorization", "Bearer ${PreferenceHelper.customPrefs(context, PreferenceHelper.prefsName)[PreferenceHelper.token, ""]}")
                requestBuilder.addHeader("Content-Type", "application/json")
                chain.proceed(requestBuilder.build())
            }

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder().addInterceptor(headersInterceptor)
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}