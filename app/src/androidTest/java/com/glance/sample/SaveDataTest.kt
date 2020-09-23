package com.glance.sample

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.glance.guolindev.sample.model.Magazine
import com.glance.guolindev.sample.model.Reader
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 *
 *
 * @author guolin
 * @since 2020/9/23
 */
@RunWith(AndroidJUnit4::class)
class SaveDataTest {

    @Test
    fun saveTestRecords() {
        val reader1 = Reader("reader1", 10, mutableListOf())
        val reader2 = Reader("reader2", 20, mutableListOf())
        repeat(50) {
            val magazine = Magazine("magazine${it}", 10.0, Date(), 1f, 100, true, ByteArray(10))
            reader1.magazines.add(magazine)
            magazine.saveThrows()
        }
        repeat(50) {
            val magazine = Magazine("magazine${it + 50}", 20.0, Date(), 0.5f, 200, false, ByteArray(20))
            reader2.magazines.add(magazine)
            magazine.saveThrows()
        }
        reader1.saveThrows()
        reader2.saveThrows()
    }

}