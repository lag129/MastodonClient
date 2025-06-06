package net.lag129.mastodon.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.lag129.mastodon.data.StatusDao
import net.lag129.mastodon.data.model.Status
import net.lag129.mastodon.di.IoDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusRepository @Inject constructor(
    private val statusDao: StatusDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllStatuses(): List<Status> = withContext(ioDispatcher) {
        statusDao.getAll()
    }

    suspend fun saveStatuses(statuses: List<Status>) = withContext(ioDispatcher) {
        statusDao.insertAll(statuses)
    }

    suspend fun deleteStatus(id: String) = withContext(ioDispatcher) {
        statusDao.delete(id)
    }

    suspend fun clearStatuses() = withContext(ioDispatcher) {
        statusDao.deleteAll()
    }
}