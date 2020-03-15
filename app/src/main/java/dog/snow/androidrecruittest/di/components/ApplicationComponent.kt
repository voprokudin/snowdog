package dog.snow.androidrecruittest.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dog.snow.androidrecruittest.base.VPApplication
import dog.snow.androidrecruittest.di.modules.VPActivityBuildersModule
import dog.snow.androidrecruittest.di.modules.VPApplicationModule
import dog.snow.androidrecruittest.di.modules.VPContextModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    VPActivityBuildersModule::class,
    VPApplicationModule::class,
    VPContextModule::class
])
interface ApplicationComponent : AndroidInjector<VPApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}