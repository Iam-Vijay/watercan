package com.watercanedelivery.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.watercanedelivery.app.webservice.APIServices

import com.watercanedelivery.utils.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Karthikeyan on 27-01-2021.
 */
@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1200, TimeUnit.SECONDS)
            .connectTimeout(1200, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @Singleton
    @Named("interceptor")
    fun getOkHttpCleint(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient? {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)

            .build()
    }


    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                return httpLoggingInterceptor
    }
    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASEURL)
            .addConverterFactory(gsonConverterFactory)

            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): APIServices {
        return retrofit.create(APIServices::class.java)
    }

       /* @Provides
        fun provideSignupRepository(apiServices: APIServices): RegisterRepository {
            return RegisterRepository(
                apiServices
            )
        }*/
}