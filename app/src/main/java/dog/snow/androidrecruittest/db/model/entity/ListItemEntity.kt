package dog.snow.androidrecruittest.db.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dog.snow.androidrecruittest.Constants

@Entity
data class ListItemEntity(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.RoomDatabase.Data.ID) val id: Long,
    @ColumnInfo(name = Constants.RoomDatabase.Data.USER_ID) val userId: Long,
    @ColumnInfo(name = Constants.RoomDatabase.Data.TITLE) val title: String,
    @ColumnInfo(name = Constants.RoomDatabase.Data.ALBUM_TITLE) val albumTitle: String,
    @ColumnInfo(name = Constants.RoomDatabase.Data.USERNAME) val username: String,
    @ColumnInfo(name = Constants.RoomDatabase.Data.EMAIL) val email: String,
    @ColumnInfo(name = Constants.RoomDatabase.Data.PHONE) val phone: String,
    @ColumnInfo(name = Constants.RoomDatabase.Data.URL) val url: String,
    @ColumnInfo(name = Constants.RoomDatabase.Data.THUMBNAIL_URL) val thumbnailUrl: String
)