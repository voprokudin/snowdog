package dog.snow.androidrecruittest.di.modules

import dagger.Binds
import dagger.Module
import dog.snow.androidrecruittest.presentation.view.VPSplashActivity
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.presentation.view.VPHomeActivity

@Module
abstract class VPSplashActivityModule {

    @Binds
    abstract fun providesSplashActivity(activity: VPSplashActivity) : VPActivity
}