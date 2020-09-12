package com.glance.guolindev.sample.model

import java.util.*

/**
 * Model class for test.
 * @author guolin
 * @since 2020/9/2
 */
data class Magazine(val name: String, val price: Double, val publishDate: Date, val discount: Float, val page: Int, val release: Boolean, val cover: ByteArray)