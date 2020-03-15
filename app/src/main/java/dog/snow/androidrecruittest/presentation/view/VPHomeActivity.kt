package dog.snow.androidrecruittest.presentation.view

import android.os.Bundle
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import javax.inject.Inject

class VPHomeActivity : VPActivity() {

    @Inject
    lateinit var navigator: VPNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        navigator.showDataListFragment()
    }
}