package com.glance.guolindev.sample

import android.app.Application
import org.litepal.LitePal
import org.litepal.LitePalDB
import org.litepal.tablemanager.Connector

/**
 * Custom application class for test.
 * @author guolin
 * @since 2020/8/24
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
//        val db = LitePalDB("test", 30)
//        LitePal.use(db)
        Connector.getDatabase()
    }

}