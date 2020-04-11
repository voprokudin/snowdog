package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dog.snow.androidrecruittest.presentation.view.HomeActivity
import dog.snow.androidrecruittest.presentation.view.SplashActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        SplashActivityModule::class,
        ApplicationModule::class
    ])
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [
        HomeActivityModule::class,
        FragmentsBindingModule::class,
        ApplicationModule::class
    ])
    abstract fun contributeHomeActivity() : HomeActivity

}