package dog.snow.androidrecruittest.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dog.snow.androidrecruittest.base.App
import dog.snow.androidrecruittest.di.modules.ActivityBuildersModule
import dog.snow.androidrecruittest.di.modules.ApplicationModule
import dog.snow.androidrecruittest.di.modules.ContextModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    ApplicationModule::class,
    ContextModule::class
])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}