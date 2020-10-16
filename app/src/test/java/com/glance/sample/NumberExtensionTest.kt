/*
 * Copyright (C)  guolin, Glance Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glance.sample

import com.glance.guolindev.extension.toNumericString
import org.junit.Assert
import org.junit.Test

/**
 * Test for Number.kt class.
 *
 * @author guolin
 * @since 2020/9/23
 */
class NumberExtensionTest {

    @Test
    fun testToNumericString() {
        val a = 1000
        Assert.assertEquals("1,000", a.toNumericString())

        val b = 952
        Assert.assertEquals("952", b.toNumericString())

        val c = 23
        Assert.assertEquals("23", c.toNumericString())

        val d = 0
        Assert.assertEquals("0", d.toNumericString())

        val e = 19324
        Assert.assertEquals("19,324", e.toNumericString())

        val f = 53923853
        Assert.assertEquals("53,923,853", f.toNumericString())

        val g = Int.MAX_VALUE
        Assert.assertEquals("2,147,483,647", g.toNumericString())

        val h = -534
        Assert.assertEquals("-534", h.toNumericString())

        val i = -16237
        Assert.assertEquals("-16,237", i.toNumericString())

        val j = -53923853
        Assert.assertEquals("-53,923,853", j.toNumericString())

        val k = Int.MIN_VALUE
        Assert.assertEquals("-2,147,483,648", k.toNumericString())
    }

}