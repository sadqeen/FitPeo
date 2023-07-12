package com.example.fitpeo.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.fitpeo.network.ApiConstant
import com.example.fitpeo.network.ApiService
import com.example.fitpeo.respository.MainRepository
import com.example.fitpeo.util.SharedPreferenceClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("IO")
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Named("MAIN")
    fun provideMainCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Singleton
    @Provides
    @Named("Application_Context")
    fun provideApplicationContext(@ApplicationContext context: Application): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("FitPeo", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceClient(
        sharedPreferences: SharedPreferences
    ): SharedPreferenceClient {
        return SharedPreferenceClient(sharedPreferences)
    }


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        return OkHttpClient
            .Builder()
            .dispatcher(dispatcher)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(UrlDecoderInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BASIC

                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        sharedPreferenceClient: SharedPreferenceClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        @Named("IO") dispatcher: CoroutineDispatcher
    ): MainRepository {
        return MainRepository(dispatcher, apiService)
    }


    @Singleton
    internal object UrlDecoderInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val original: Request = chain.request()
            var url: String = original.url.toString()
            url = java.net.URLDecoder.decode(url, StandardCharsets.UTF_8.name())
            val requestBuilder: Request.Builder = original.newBuilder().url(url)
            val request: Request = requestBuilder.build()
            return chain.proceed(request)
        }
    }
}