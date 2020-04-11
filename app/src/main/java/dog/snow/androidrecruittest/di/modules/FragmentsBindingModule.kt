package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dog.snow.androidrecruittest.presentation.view.BaseDetailsFragment
import dog.snow.androidrecruittest.presentation.view.BaseListFragment

@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideDataListFragment(): BaseListFragment

    @ContributesAndroidInjector
    internal abstract fun provideDetailsFragment(): BaseDetailsFragment
}