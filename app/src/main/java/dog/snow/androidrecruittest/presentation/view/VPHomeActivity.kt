package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import android.view.View
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.util.network.VPNetworkStateProvider
import kotlinx.android.synthetic.main.layout_appbar.offlineMode
import javax.inject.Inject

class VPHomeActivity : VPActivity() {

    @Inject
    lateinit var navigator: VPNavigator

    @Inject
    lateinit var networkStateProvider: VPNetworkStateProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        offlineMode.visibility = if (networkStateProvider.isNetworkConnected()) View.GONE else View.VISIBLE
        navigator.showDataListFragment()
    }
}