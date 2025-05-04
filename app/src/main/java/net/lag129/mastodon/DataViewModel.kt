package net.lag129.mastodon

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.lag129.mastodon.data.Status

class DataViewModel : ViewModel() {
    private val _data = mutableStateOf<List<Status>>(emptyList())
    private val _isLoading = mutableStateOf(false)
    private val _error = mutableStateOf<String?>(null)
    private var maxId: String? = null
    private var currentTimeline = Timeline.HOME

    val data = _data as State<List<Status>>
    val isLoading = _isLoading as State<Boolean>
    val error = _error as State<String?>

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
                    Timeline.HOME -> ApiClient.apiService.fetchHomeData(maxId)
                    Timeline.LOCAL -> ApiClient.apiService.fetchLocalData(maxId)
                    Timeline.GLOBAL -> ApiClient.apiService.fetchAccountData(
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

    fun refresh() {
        maxId = null
        _data.value = emptyList()
        fetchData()
    }

    fun switchTimeline(timeline: Timeline) {
        if (currentTimeline != timeline) {
            currentTimeline = timeline
            maxId = null
            _data.value = emptyList()
            fetchData()
        }
    }
}
