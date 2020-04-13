package dog.snow.androidrecruittest.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dog.snow.androidrecruittest.base.BaseActivity
import dog.snow.androidrecruittest.common.intentfactory.HomeIntentFactory
import dog.snow.androidrecruittest.data.repository.ListItemsLocalLocalRepositoryImpl
import dog.snow.androidrecruittest.data.rest.AlbumService
import dog.snow.androidrecruittest.data.rest.PhotoService
import dog.snow.androidrecruittest.data.rest.UserService
import dog.snow.androidrecruittest.db.RoomDatabase
import dog.snow.androidrecruittest.domain.repository.ListItemsLocalRepository
import dog.snow.androidrecruittest.presentation.factory.HomeIntentFactoryImpl
import dog.snow.androidrecruittest.presentation.navigation.Navigator
import dog.snow.androidrecruittest.util.fragment.FragmentUtil
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ViewModelModule::class])
class ApplicationModule {

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
    internal fun provideRetrofitPhotoService(retrofit: Retrofit): PhotoService {
        return retrofit.create<PhotoService>(PhotoService::class.java)
    }

    @Provides
    internal fun provideRetrofitAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create<AlbumService>(AlbumService::class.java)
    }

    @Provides
    internal fun provideRetrofitUserService(retrofit: Retrofit): UserService {
        return retrofit.create<UserService>(UserService::class.java)
    }

    @Provides
    internal fun provideFragmentUtil(): FragmentUtil = FragmentUtil()

    @Provides
    internal fun provideHomeIntentFactory(
        homeIntentFactoryImpl: HomeIntentFactoryImpl
    ): HomeIntentFactory = homeIntentFactoryImpl

    @Provides
    internal fun provideListItemRepository(
        listItemsLocalLocalRepositoryImpl: ListItemsLocalLocalRepositoryImpl
    ): ListItemsLocalRepository = listItemsLocalLocalRepositoryImpl

    @Provides
    fun provideDatabase(application: Application): RoomDatabase {
        return Room.databaseBuilder(
            application,
            RoomDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    internal fun provideNavigator(
        activity: BaseActivity,
        fragmentUtil: FragmentUtil,
        homeIntentFactory: HomeIntentFactory
    ): Navigator = Navigator(activity, fragmentUtil, homeIntentFactory)
}