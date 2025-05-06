package com.snehonir.bstest.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BSTestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here
    }
}