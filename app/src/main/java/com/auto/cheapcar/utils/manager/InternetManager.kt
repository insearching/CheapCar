package com.auto.cheapcar.utils.manager

import android.net.ConnectivityManager
import com.auto.cheapcar.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class InternetManager @Inject constructor(private val connectivityManager: ConnectivityManager) {
    fun connectionAvailable() = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
}