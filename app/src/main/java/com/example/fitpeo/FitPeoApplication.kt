package com.example.fitpeo

import android.app.Application
import android.content.Context
import com.example.fitpeo.util.PicassoInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitPeoApplication : Application() {
    override fun getApplicationContext(): Context {
        return super.getApplicationContext()

    }
}