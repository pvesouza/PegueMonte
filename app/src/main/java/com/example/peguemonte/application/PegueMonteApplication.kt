package com.example.peguemonte.application

import android.app.Application
import com.example.peguemonte.helpers.HelperDB

class PegueMonteApplication: Application() {

    var helperDB:HelperDB? = null
        private set

    companion object{
        lateinit var instance:PegueMonteApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }

}