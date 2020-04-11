package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dog.snow.androidrecruittest.presentation.view.DetailsFragment
import dog.snow.androidrecruittest.presentation.view.ListFragment

@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideDataListFragment(): ListFragment

    @ContributesAndroidInjector
    internal abstract fun provideDetailsFragment(): DetailsFragment
}