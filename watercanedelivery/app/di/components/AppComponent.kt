package com.watercanedelivery.app.di.components


import android.app.Application
import android.content.SharedPreferences
import com.watercanedeilvery.di.modules.ViewBindingModule
import com.watercanedelivery.app.WaterCaneApplication
import com.watercanedelivery.app.di.modules.SharedPreferenceModule
import com.watercanedelivery.di.modules.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Karthikeyan on 27-01-2021.
 */
@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class/*, AppModule::class*/, ViewBindingModule::class, SharedPreferenceModule::class,
        RetrofitModule::class]
)
interface AppComponent : AndroidInjector<WaterCaneApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun getSharedPrefs(): SharedPreferences?
}