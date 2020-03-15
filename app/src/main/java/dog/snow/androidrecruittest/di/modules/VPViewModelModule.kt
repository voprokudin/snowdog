package dog.snow.androidrecruittest.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dog.snow.androidrecruittest.di.util.VPViewModelKey
import dog.snow.androidrecruittest.presentation.viewmodel.VPListFragmentViewModel
import dog.snow.androidrecruittest.presentation.viewmodel.VPSplashViewModel
import dog.snow.androidrecruittest.presentation.viewmodel.factory.VPViewModelFactory

@Module
abstract class VPViewModelModule {

    @Binds
    @IntoMap
    @VPViewModelKey(VPSplashViewModel::class)
    internal abstract fun bindSplashViewModel(splashViewModel: VPSplashViewModel): ViewModel

    @Binds
    @IntoMap
    @VPViewModelKey(VPListFragmentViewModel::class)
    internal abstract fun bindListFragmentViewModel(listFragmentViewModel: VPListFragmentViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: VPViewModelFactory): ViewModelProvider.Factory
}