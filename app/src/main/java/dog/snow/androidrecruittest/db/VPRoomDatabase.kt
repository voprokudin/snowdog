package dog.snow.androidrecruittest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.C
import dog.snow.androidrecruittest.db.dao.VPListItemDao
import dog.snow.androidrecruittest.db.model.entity.VPListItemEntity

@Database(
    entities = [VPListItemEntity::class],
    version = C.RoomDatabase.VERSION
)
abstract class VPRoomDatabase : RoomDatabase() {

    abstract fun listItemDao(): VPListItemDao
}