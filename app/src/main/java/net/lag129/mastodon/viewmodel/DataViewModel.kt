package net.lag129.mastodon.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.lag129.mastodon.data.model.Status
import net.lag129.mastodon.data.repository.ApiClient
import net.lag129.mastodon.data.repository.StatusRepository
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: StatusRepository,
    internal val apiClient: ApiClient
) : ViewModel() {
    private val _data = mutableStateOf<List<Status>>(emptyList())
    val data = _data as State<List<Status>>

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading as State<Boolean>

    private val _statuses = MutableStateFlow<List<Status>>(emptyList())
    val statuses: StateFlow<List<Status>> = _statuses

    private val _error = mutableStateOf<String?>(null)
    private var maxId: String? = null

    private var currentTimeline = Timeline.HOME

    enum class Timeline {
        HOME, GLOBAL, USER
    }

    init {
        loadStatuses()
    }

    private fun loadStatuses() {
        viewModelScope.launch {
            try {
                val localStatuses = repository.getAllStatuses()
                _statuses.value = localStatuses


                _isLoading.value = true
                _error.value = null

                val apiService = apiClient.createApiService()

                val remoteStatuses = when (currentTimeline) {
                    Timeline.HOME -> apiService.fetchHomeData(maxId)
                    Timeline.GLOBAL -> apiService.fetchGlobalData(maxId)
                    Timeline.USER -> {
                        val myId = apiService.getMyCredential().id
                        apiService.fetchAccountData(myId, maxId)
                    }
                }

                repository.saveStatuses(remoteStatuses)
                _statuses.value = repository.getAllStatuses()

                maxId = if (remoteStatuses.isNotEmpty()) {
                    remoteStatuses.last().id
                } else {
                    null
                }
            } catch (e: Exception) {
                _error.value = e.message
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchNextPage() {
        if (!_isLoading.value) {
            loadStatuses()
        }
    }

    fun switchTimeline(timeline: Timeline) {
        if (currentTimeline != timeline) {
            viewModelScope.launch {
                try {
                    _isLoading.value = true

                    repository.clearStatuses()
                    
                    _statuses.value = emptyList()
                    _data.value = emptyList()

                    currentTimeline = timeline
                    maxId = null

                    val checkEmpty = repository.getAllStatuses()
                    if (checkEmpty.isNotEmpty()) {
                        repository.clearStatuses()
                    }

                    loadStatuses()
                } catch (e: Exception) {
                    _error.value = e.message
                    e.printStackTrace()
                }
            }
        }
    }
}
