package be.mathias.thebible.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://bible-api.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply{level = HttpLoggingInterceptor.Level.BASIC}

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface VerseApiService {

}

object VerseApi {
    val retrofitService: VerseApiService by lazy {
        retrofit.create(VerseApiService::class.java)
    }
}