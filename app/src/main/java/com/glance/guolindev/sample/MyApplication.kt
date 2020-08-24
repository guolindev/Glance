package com.glance.guolindev.sample

import android.app.Application
import org.litepal.LitePal
import org.litepal.tablemanager.Connector

/**
 *
 * @author guolin
 * @since 2020/8/24
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        Connector.getDatabase()
    }

}