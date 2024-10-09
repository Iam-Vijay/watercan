package com.watercanedelivery.app.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.watercanedelivery.utils.Constant
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Karthikeyan on 01/04/2021.
 */
@Module
public class SharedPreferenceModule {
@Singleton
@Provides
fun provideSharedPreferences(application: Application): SharedPreferences {
    return application.getSharedPreferences(
        Constant.SESSION_NAME,
        Context.MODE_PRIVATE
    )
}
}