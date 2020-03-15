package dog.snow.androidrecruittest.data.rest

import dog.snow.androidrecruittest.data.model.VPRawUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface VPUserService {

    @GET("users/{userId}")
    fun getUser(
        @Path("userId") userId: Int
    ): Single<VPRawUser>
}