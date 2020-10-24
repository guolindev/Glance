package com.glance.guolindev.sample.model

import org.litepal.crud.LitePalSupport

/**
 * Model class for test.
 * @author guolin
 * @since 2020/9/2
 */
class Reader(val name: String, val age: Int) : LitePalSupport() {

    var magazines: MutableList<Magazine> = ArrayList()

}