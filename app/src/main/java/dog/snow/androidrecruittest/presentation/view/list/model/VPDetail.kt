package dog.snow.androidrecruittest.presentation.view.list.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VPDetail(
    val photoId: Int,
    val photoTitle: String,
    val albumTitle: String,
    val username: String,
    val email: String,
    val phone: String,
    val url: String
) : Parcelable