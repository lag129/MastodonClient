package net.lag129.mastodon

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.lag129.mastodon.data.Status
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    val apiClient: ApiClient
) : ViewModel() {
    private val _data = mutableStateOf<List<Status>>(emptyList())
    private val _isLoading = mutableStateOf(false)
    private val _error = mutableStateOf<String?>(null)
    private var maxId: String? = null
    private var currentTimeline = Timeline.HOME

    val data = _data as State<List<Status>>

    enum class Timeline {
        HOME, LOCAL, GLOBAL
    }

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                val result = when (currentTimeline) {
                    Timeline.HOME -> apiClient.createApiService().fetchHomeData(maxId)
                    Timeline.LOCAL -> apiClient.createApiService().fetchLocalData(maxId)
                    Timeline.GLOBAL -> apiClient.createApiService().fetchAccountData(
                        "109302719268780804",
                        maxId
                    )
                }

                if (result.isNotEmpty()) {
                    maxId = result.last().id
                    _data.value = _data.value + result
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
            fetchData()
        }
    }

//    fun refresh() {
//        maxId = null
//        _data.value = emptyList()
//        fetchData()
//    }

    fun switchTimeline(timeline: Timeline) {
        if (currentTimeline != timeline) {
            currentTimeline = timeline
            maxId = null
            _data.value = emptyList()
            fetchData()
        }
    }
}
