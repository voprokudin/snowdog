package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dog.snow.androidrecruittest.presentation.view.BaseHomeActivity
import dog.snow.androidrecruittest.presentation.view.BaseSplashActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        SplashActivityModule::class,
        ApplicationModule::class
    ])
    abstract fun contributeSplashActivity(): BaseSplashActivity

    @ContributesAndroidInjector(modules = [
        HomeActivityModule::class,
        FragmentsBindingModule::class,
        ApplicationModule::class
    ])
    abstract fun contributeHomeActivity() : BaseHomeActivity

}