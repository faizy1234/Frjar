package com.example.frjarcustomer.core.di

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import com.example.frjarcustomer.R
import com.example.frjarcustomer.core.config.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    private const val DISK_CACHE_DIR = "coil_image_cache"
    private const val MEMORY_CACHE_PERCENT = 0.25
    private const val DISK_CACHE_PERCENT = 0.02

    @OptIn(DelicateCoilApi::class)
    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
        appConfig: AppConfig
    ): ImageLoader {
        val loader = ImageLoader.Builder(context)
            .crossfade(!appConfig.isDebug)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, MEMORY_CACHE_PERCENT)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(File(context.cacheDir, DISK_CACHE_DIR))
                    .maxSizePercent(DISK_CACHE_PERCENT)
                    .build()
            }
            .components {
                add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
                add(SvgDecoder.Factory())
                if (SDK_INT >= 28) {
                    add(AnimatedImageDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }

            .build()
        SingletonImageLoader.setUnsafe { loader }
        return loader
    }
}
