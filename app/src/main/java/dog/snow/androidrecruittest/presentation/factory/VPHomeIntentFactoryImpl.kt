package dog.snow.androidrecruittest.presentation.factory

import android.app.Application
import android.content.Intent
import dog.snow.androidrecruittest.presentation.view.VPHomeActivity
import dog.snow.androidrecruittest.common.intentfactory.VPHomeIntentFactory
import javax.inject.Inject

class VPHomeIntentFactoryImpl
@Inject constructor(
    private val context: Application
): VPHomeIntentFactory {

    override fun forHomeActivity(): Intent = Intent(context, VPHomeActivity::class.java)
}