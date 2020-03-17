package dog.snow.androidrecruittest.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dog.snow.androidrecruittest.base.VPActivity
import dog.snow.androidrecruittest.common.intentfactory.VPHomeIntentFactory
import dog.snow.androidrecruittest.data.repository.VPListItemsLocalLocalRepositoryImpl
import dog.snow.androidrecruittest.data.rest.VPAlbumService
import dog.snow.androidrecruittest.data.rest.VPPhotoService
import dog.snow.androidrecruittest.data.rest.VPUserService
import dog.snow.androidrecruittest.db.VPRoomDatabase
import dog.snow.androidrecruittest.domain.repository.VPListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.factory.VPHomeIntentFactoryImpl
import dog.snow.androidrecruittest.presentation.navigation.VPNavigator
import dog.snow.androidrecruittest.util.fragment.VPFragmentUtil
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [VPViewModelModule::class])
class VPApplicationModule {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val DB_NAME = "candidate_room.db"

    @Provides
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    internal fun provideRetrofitPhotoService(retrofit: Retrofit): VPPhotoService {
        return retrofit.create<VPPhotoService>(VPPhotoService::class.java)
    }

    @Provides
    internal fun provideRetrofitAlbumService(retrofit: Retrofit): VPAlbumService {
        return retrofit.create<VPAlbumService>(VPAlbumService::class.java)
    }

    @Provides
    internal fun provideRetrofitUserService(retrofit: Retrofit): VPUserService {
        return retrofit.create<VPUserService>(VPUserService::class.java)
    }

    @Provides
    internal fun provideVPFragmentUtil(): VPFragmentUtil = VPFragmentUtil()

    @Provides
    internal fun provideVPHomeIntentFactory(
        homeIntentFactoryImpl: VPHomeIntentFactoryImpl
    ): VPHomeIntentFactory = homeIntentFactoryImpl

    @Provides
    internal fun provideListItemRepository(
        listItemsLocalLocalRepositoryImpl: VPListItemsLocalLocalRepositoryImpl
    ): VPListItemsLocalRepository = listItemsLocalLocalRepositoryImpl

    @Provides
    fun provideDatabase(application: Application): VPRoomDatabase {
        return Room.databaseBuilder(
            application,
            VPRoomDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    internal fun provideVPNavigator(
        activity: VPActivity,
        fragmentUtil: VPFragmentUtil,
        homeIntentFactory: VPHomeIntentFactory
    ): VPNavigator = VPNavigator(activity, fragmentUtil, homeIntentFactory)
}