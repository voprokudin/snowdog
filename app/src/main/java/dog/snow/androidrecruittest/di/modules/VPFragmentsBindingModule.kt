package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dog.snow.androidrecruittest.ui.VPListFragment

@Module
abstract class VPFragmentsBindingModule {

    @ContributesAndroidInjector
    internal abstract fun provideDataListFragment(): VPListFragment

//    @ContributesAndroidInjector
//    internal abstract fun provideProductDetailFragment(): VPProductDetailFragment
}