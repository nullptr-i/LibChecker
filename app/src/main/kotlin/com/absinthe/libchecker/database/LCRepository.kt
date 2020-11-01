package com.absinthe.libchecker.database

import androidx.lifecycle.LiveData
import com.absinthe.libchecker.database.entity.LCItem
import com.absinthe.libchecker.database.entity.SnapshotItem
import com.absinthe.libchecker.database.entity.TimeStampItem

class LCRepository(private val lcDao: LCDao) {

    val allDatabaseItems: LiveData<List<LCItem>> = lcDao.getItems()
    val allSnapshotItems: LiveData<List<SnapshotItem>> = lcDao.getSnapshots()

    fun getItem(packageName: String): LCItem? {
        return lcDao.getItem(packageName)
    }

    suspend fun getSnapshots(timestamp: Long) = lcDao.getSnapshots(timestamp)

    fun getTimeStamps(): List<TimeStampItem> = lcDao.getTimeStamps()

    suspend fun insert(item: LCItem) {
        lcDao.insert(item)
    }

    suspend fun insert(list: List<LCItem>) {
        lcDao.insert(list)
    }

    suspend fun insert(item: SnapshotItem) {
        lcDao.insert(item)
    }

    suspend fun insert(item: TimeStampItem) {
        lcDao.insert(item)
    }

    suspend fun insertSnapshots(items: List<SnapshotItem>) {
        lcDao.insertSnapshots(items)
    }

    suspend fun update(item: LCItem) {
        lcDao.update(item)
    }

    suspend fun update(item: SnapshotItem) {
        lcDao.update(item)
    }

    suspend fun update(items: List<SnapshotItem>) {
        lcDao.update(items)
    }

    suspend fun delete(item: SnapshotItem) {
        lcDao.delete(item)
    }

    suspend fun delete(item: LCItem) {
        lcDao.delete(item)
    }

    fun deleteSnapshotsAndTimeStamp(timestamp: Long) {
        lcDao.deleteSnapshots(timestamp)
        lcDao.delete(TimeStampItem(timestamp))
    }

    fun deleteAllSnapshots() {
        lcDao.deleteAllSnapshots()
    }

    fun deleteAllItems() {
        lcDao.deleteAllItems()
    }
}