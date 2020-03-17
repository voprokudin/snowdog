package dog.snow.androidrecruittest.util.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class VPNetworkStateProvider
@Inject constructor(
    private val context: Context
) {
    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }
}