package dog.snow.androidrecruittest.data.rest

import dog.snow.androidrecruittest.data.model.VPRawPhoto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VPPhotoService {

    @GET("photos")
    fun getPhotos(
        @Query("_limit") limit: Int
    ): Single<ArrayList<VPRawPhoto>>
}