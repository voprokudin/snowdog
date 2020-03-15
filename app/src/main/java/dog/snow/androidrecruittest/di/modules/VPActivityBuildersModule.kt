package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dog.snow.androidrecruittest.presentation.view.VPHomeActivity
import dog.snow.androidrecruittest.presentation.view.VPSplashActivity

@Module
abstract class VPActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        VPSplashActivityModule::class,
        VPApplicationModule::class
    ])
    abstract fun contributeSplashActivity(): VPSplashActivity

    @ContributesAndroidInjector(modules = [
        VPHomeActivityModule::class,
        VPFragmentsBindingModule::class,
        VPApplicationModule::class
    ])
    abstract fun contributeHomeActivity() : VPHomeActivity

}