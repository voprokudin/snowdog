package dog.snow.androidrecruittest.data.rest

import dog.snow.androidrecruittest.data.model.VPRawAlbum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface VPAlbumService {

    @GET("albums/{albumId}")
    fun getAlbum(
        @Path("albumId") albumId: Int
    ): Single<VPRawAlbum>
}