package com.heybeach.images

import android.graphics.Bitmap
import android.util.LruCache

const val BYTES_IN_KILOBYTE = 1024

class BitmapMemoryCache {

    private val maxMemory = (Runtime.getRuntime().maxMemory() / BYTES_IN_KILOBYTE).toInt()
    private val cacheSize = maxMemory / 2
    private val bitmapCache = BitmapLruCache(cacheSize)

    fun hasItem(bitmapUrl: String) = bitmapCache[bitmapUrl] != null

    fun cache(bitmapUrl: String, bitmap: Bitmap) {
        bitmapCache.put(bitmapUrl, bitmap)
    }

    fun get(bitmapUrl: String): Bitmap? = bitmapCache[bitmapUrl]

}

/**
 * Measures size in KB instead of number of items
 */
class BitmapLruCache(size: Int) : LruCache<String, Bitmap>(size) {

    override fun sizeOf(key: String, value: Bitmap): Int {
        return value.byteCount / BYTES_IN_KILOBYTE
    }
}
