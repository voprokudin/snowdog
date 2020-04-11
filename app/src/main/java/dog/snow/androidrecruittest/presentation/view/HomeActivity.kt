package dog.snow.androidrecruittest.presentation.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.BaseActivity
import dog.snow.androidrecruittest.base.App
import dog.snow.androidrecruittest.presentation.navigation.Navigator
import dog.snow.androidrecruittest.util.network.ConnectivityReceiver
import dog.snow.androidrecruittest.util.network.ConnectivityReceiver.ConnectivityReceiverListener
import kotlinx.android.synthetic.main.layout_appbar.offlineMode
import javax.inject.Inject

class HomeActivity : BaseActivity(), ConnectivityReceiverListener {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var application: App

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
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun registerConnectivityReceiver() {
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setOfflineMode(connected: Boolean) {
        offlineMode.visibility = if(connected) View.GONE else View.VISIBLE
    }
}
