package dog.snow.androidrecruittest.di.modules

import dagger.Module
import dagger.Provides
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.common.intentfactory.VPHomeIntentFactory
import dog.snow.androidrecruittest.presentation.factory.VPHomeIntentFactoryImpl
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.util.fragment.VPFragmentUtil
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [VPViewModelModule::class])
class VPApplicationModule {

    private val BASE_URL = "https://recruitment-task.futuremind.dev/api/products/"

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

//    @Provides
//    internal fun provideRetrofitService(retrofit: Retrofit): VPProductsService {
//        return retrofit.create<VPProductsService>(VPProductsService::class.java)
//    }

    @Provides
    internal fun provideVPFragmentUtil(): VPFragmentUtil = VPFragmentUtil()

    @Provides
    internal fun provideVPHomeIntentFactory(
        homeIntentFactoryImpl: VPHomeIntentFactoryImpl
    ): VPHomeIntentFactory = homeIntentFactoryImpl

    @Provides
    internal fun provideVPNavigator(
        activity: VPActivity,
        fragmentUtil: VPFragmentUtil,
        homeIntentFactory: VPHomeIntentFactory
    ): VPNavigator = VPNavigator(activity, fragmentUtil, homeIntentFactory)
}