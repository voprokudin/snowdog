package dog.snow.androidrecruittest.presentation.view.list.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VPListItem(
    val id: Int,
    val title: String,
    val albumTitle: String,
    val thumbnailUrl: String
) : Parcelable