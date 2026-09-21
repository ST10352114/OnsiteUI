package com.example.onsite_mockups.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Singleton object providing the Retrofit instance for API communication.
 * Configures authentication headers, logging, and JSON serialization.
 */
object RetrofitClient {
    private const val BASE_URL = "https://onsiteapi-9n82.onrender.com/api/v1/"
    
    private var authToken: String? = null

    // JSON configuration for Kotlinx Serialization
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
    }

    /**
     * Sets the Bearer token used for authenticated requests.
     */
    fun setToken(token: String?) {
        authToken = token
    }

    // Interceptor to inject the Authorization header into every request
    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        authToken?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        chain.proceed(requestBuilder.build())
    }

    // Interceptor for logging HTTP request/response details
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient with interceptors and timeouts
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // The Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    /**
     * The API service implementation used by the app.
     */
    val apiService: OnSiteApiService = retrofit.create(OnSiteApiService::class.java)
}
