package dog.snow.androidrecruittest.data.rest

import dog.snow.androidrecruittest.data.model.RawAlbum
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {

    @GET("albums/{albumId}")
    fun getAlbum(
        @Path("albumId") albumId: Long
    ): Single<RawAlbum>
}