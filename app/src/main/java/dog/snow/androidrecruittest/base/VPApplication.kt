package dog.snow.androidrecruittest.base

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import dog.snow.androidrecruittest.di.components.DaggerApplicationComponent

class VPApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}