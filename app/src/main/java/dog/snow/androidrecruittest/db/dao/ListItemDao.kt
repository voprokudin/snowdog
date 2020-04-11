package dog.snow.androidrecruittest.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.db.model.entity.ListItemEntity
import io.reactivex.Single

@Dao
interface ListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listItemEntities: List<ListItemEntity>): Single<List<Long>>

    @Query("SELECT * FROM ListItemEntity")
    fun getAll(): Single<List<ListItemEntity>>
}