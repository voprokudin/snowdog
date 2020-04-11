package dog.snow.androidrecruittest.presentation.factory

import android.app.Application
import android.content.Intent
import dog.snow.androidrecruittest.presentation.view.HomeActivity
import dog.snow.androidrecruittest.common.intentfactory.HomeIntentFactory
import javax.inject.Inject

class HomeIntentFactoryImpl
@Inject constructor(
    private val context: Application
): HomeIntentFactory {

    override fun forHomeActivity(): Intent = Intent(context, HomeActivity::class.java)
}