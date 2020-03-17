package dog.snow.androidrecruittest.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.db.model.entity.VPListItemEntity
import dog.snow.androidrecruittest.presentation.view.list.model.VPListItem
import io.reactivex.Single

@Dao
interface VPListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listItemEntities: List<VPListItemEntity>): Single<List<Long>>

    @Query("SELECT * FROM VPListItemEntity")
    fun getAll(): Single<List<VPListItemEntity>>
}