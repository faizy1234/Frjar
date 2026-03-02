package com.example.frjarcustomer.core.di

import android.content.Context
import coil3.ImageLoader
import coil3.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageCacheWarmer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) {

    suspend fun preloadUrls(urls: List<String>) = withContext(Dispatchers.IO) {
        urls
            .filter { it.isNotBlank() }
            .map { url ->
                async {
                    runCatching {
                        val request = ImageRequest.Builder(context)
                            .data(url)
                            .build()
                        imageLoader.execute(request)
                    }
                }
            }
            .awaitAll()
    }

    suspend fun preloadUrl(url: String) = withContext(Dispatchers.IO) {
        if (url.isBlank()) return@withContext
        runCatching {
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()
            imageLoader.execute(request)
        }
    }
}
