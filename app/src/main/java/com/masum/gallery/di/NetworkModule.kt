package com.masum.gallery.di


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.masum.gallery.BuildConfig
import com.masum.gallery.network.HttpParam.CLIENT_ID
import com.masum.gallery.network.NetworkCallbackApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    val token: String = ""

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->

                // Request customization: add request query params
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(CLIENT_ID, BuildConfig.ACCESS_KEY)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()


    @Provides
    @Singleton
    fun provideNetworkCallback(okHttpClient: OkHttpClient, moshi: Moshi): NetworkCallbackApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(NetworkCallbackApi::class.java)
    }

}