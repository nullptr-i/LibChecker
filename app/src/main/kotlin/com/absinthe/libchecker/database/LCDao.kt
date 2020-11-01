package com.absinthe.libchecker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.absinthe.libchecker.database.entity.LCItem
import com.absinthe.libchecker.database.entity.SnapshotItem
import com.absinthe.libchecker.database.entity.TimeStampItem

@Dao
interface LCDao {

    //Item Table
    @Query("SELECT * from item_table ORDER BY label ASC")
    fun getItems(): LiveData<List<LCItem>>

    @Query("SELECT * from item_table WHERE packageName LIKE :packageName")
    fun getItem(packageName: String): LCItem?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: LCItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<LCItem>)

    @Update
    suspend fun update(item: LCItem)

    @Delete
    suspend fun delete(item: LCItem)

    @Query("DELETE FROM item_table")
    fun deleteAllItems()

    //Snapshot Table
    @Query("SELECT * from snapshot_table ORDER BY packageName ASC")
    fun getSnapshots(): LiveData<List<SnapshotItem>>

    @Query("SELECT * from snapshot_table WHERE timeStamp LIKE :timestamp ORDER BY packageName ASC")
    suspend fun getSnapshots(timestamp: Long): List<SnapshotItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: SnapshotItem)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSnapshots(items: List<SnapshotItem>)

    @Update
    suspend fun update(item: SnapshotItem)

    @Update
    suspend fun update(items: List<SnapshotItem>)

    @Delete
    suspend fun delete(item: SnapshotItem)

    @Query("DELETE FROM snapshot_table")
    fun deleteAllSnapshots()

    @Query("DELETE FROM snapshot_table WHERE timeStamp = :timestamp")
    fun deleteSnapshots(timestamp: Long)

    //TimeStamp Table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TimeStampItem)

    @Query("SELECT * from timestamp_table ORDER BY timestamp DESC")
    fun getTimeStamps(): List<TimeStampItem>

    @Delete
    fun delete(item: TimeStampItem)
}