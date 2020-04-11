package dog.snow.androidrecruittest.di.modules

import dagger.Binds
import dagger.Module
import dog.snow.androidrecruittest.base.BaseActivity
import dog.snow.androidrecruittest.presentation.view.BaseHomeActivity

@Module
abstract class HomeActivityModule {

    @Binds
    abstract fun providesHomeActivity(activity: BaseHomeActivity) : BaseActivity
}