package dog.snow.androidrecruittest.di.modules

import dagger.Binds
import dagger.Module
import dog.snow.androidrecruittest.presentation.view.SplashActivity
import dog.snow.androidrecruittest.base.BaseActivity

@Module
abstract class SplashActivityModule {

    @Binds
    abstract fun providesSplashActivity(activity: SplashActivity) : BaseActivity
}