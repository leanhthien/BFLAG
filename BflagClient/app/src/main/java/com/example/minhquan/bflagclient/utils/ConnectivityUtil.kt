package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectivityUtil {

    companion object {

        /**
         * Check network connection
         */
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                val activeNetworks = cm.allNetworks
                for (n in activeNetworks) {
                    val nInfo = cm.getNetworkInfo(n)
                    if (nInfo.isConnected)
                        return true
                }

            } else {
                val info = cm.allNetworkInfo
                if (info != null)
                    for (anInfo in info)
                        if (anInfo.state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
            }

            return false

        }
    }

}