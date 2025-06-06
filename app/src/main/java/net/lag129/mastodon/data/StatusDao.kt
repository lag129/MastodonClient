package net.lag129.mastodon.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.lag129.mastodon.data.model.Status

@Dao
interface StatusDao {
    @Query("SELECT * FROM status ORDER BY createdAt DESC")
    suspend fun getAll(): List<Status>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(statuses: List<Status>)

    @Query("DELETE FROM status WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM status")
    suspend fun deleteAll()
}