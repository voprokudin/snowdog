package dog.snow.androidrecruittest.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class VPContextModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context
}