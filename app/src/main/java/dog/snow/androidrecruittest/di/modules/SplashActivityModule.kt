package dog.snow.androidrecruittest.di.modules

import dagger.Binds
import dagger.Module
import dog.snow.androidrecruittest.presentation.view.BaseSplashActivity
import dog.snow.androidrecruittest.base.BaseActivity

@Module
abstract class SplashActivityModule {

    @Binds
    abstract fun providesSplashActivity(activity: BaseSplashActivity) : BaseActivity
}