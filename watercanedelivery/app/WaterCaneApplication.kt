package com.watercanedelivery.app

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.watercanedelivery.app.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class WaterCaneApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        val appComponent = DaggerAppComponent.builder().application(this).build()
//        appComponent.inject(this)
        val defaultOptions = DisplayImageOptions.Builder()
            .cacheOnDisk(true).cacheInMemory(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .displayer(FadeInBitmapDisplayer(300)).build()

        val imageLoaderConfiguration = ImageLoaderConfiguration.Builder(
            applicationContext
        )
            .defaultDisplayImageOptions(defaultOptions)
            .memoryCache(WeakMemoryCache())
            .diskCacheSize(100 * 1024 * 1024).build()

        ImageLoader.getInstance().init(imageLoaderConfiguration)
        return appComponent
    }

}