package dog.snow.androidrecruittest.di.modules

import dagger.Binds
import dagger.Module
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.presentation.view.VPHomeActivity

@Module
abstract class VPHomeActivityModule {

    @Binds
    abstract fun providesHomeActivity(activity: VPHomeActivity) : VPActivity
}