package dog.snow.androidrecruittest.presentation.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.base.VPApplication
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.util.network.VPConnectivityReceiver
import dog.snow.androidrecruittest.util.network.VPConnectivityReceiver.ConnectivityReceiverListener
import kotlinx.android.synthetic.main.layout_appbar.offlineMode
import javax.inject.Inject

class VPHomeActivity : VPActivity(), ConnectivityReceiverListener {

    @Inject
    lateinit var navigator: VPNavigator

    @Inject
    lateinit var application: VPApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        registerConnectivityReceiver()
        navigator.showDataListFragment()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        setOfflineMode(isConnected)
    }

    override fun onResume() {
        super.onResume()
        VPConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun registerConnectivityReceiver() {
        registerReceiver(VPConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setOfflineMode(connected: Boolean) {
        offlineMode.visibility = if(connected) View.GONE else View.VISIBLE
    }
}
