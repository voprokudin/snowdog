package dog.snow.androidrecruittest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dog.snow.androidrecruittest.Constants
import dog.snow.androidrecruittest.db.dao.ListItemDao
import dog.snow.androidrecruittest.db.model.entity.ListItemEntity

@Database(
    entities = [ListItemEntity::class],
    version = Constants.RoomDatabase.VERSION
)
abstract class RoomDatabase : RoomDatabase() {

    abstract fun listItemDao(): ListItemDao
}