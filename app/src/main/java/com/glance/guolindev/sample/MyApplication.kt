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
        var db = LitePalDB("test", 30)
        LitePal.use(db)
        Connector.getDatabase()
        db = LitePalDB("test2", 30)
        LitePal.use(db)
        Connector.getDatabase()
        db = LitePalDB("test3", 30)
        LitePal.use(db)
        Connector.getDatabase()
        db = LitePalDB("test4", 30)
        LitePal.use(db)
        Connector.getDatabase()
        db = LitePalDB("test5", 30)
        LitePal.use(db)
        Connector.getDatabase()
        db = LitePalDB("test6", 30)
        LitePal.use(db)
        Connector.getDatabase()
        db = LitePalDB("test7", 30)
        LitePal.use(db)
        Connector.getDatabase()
        LitePal.useDefault()
        Connector.getDatabase()
    }

}