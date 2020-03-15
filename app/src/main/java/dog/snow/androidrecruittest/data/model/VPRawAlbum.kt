package dog.snow.androidrecruittest.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VPRawAlbum(
    val id: Int,
    val userId: Int,
    val title: String
) : Parcelable
