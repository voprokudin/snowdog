package dog.snow.androidrecruittest.data.rest

import dog.snow.androidrecruittest.data.model.RawUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{userId}")
    fun getUser(
        @Path("userId") userId: Long
    ): Single<RawUser>
}